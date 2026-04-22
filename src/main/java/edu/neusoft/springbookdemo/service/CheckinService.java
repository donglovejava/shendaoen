package edu.neusoft.springbookdemo.service;

import edu.neusoft.springbookdemo.entity.Checkin;
import edu.neusoft.springbookdemo.entity.CheckinRecord;
import edu.neusoft.springbookdemo.entity.Course;
import edu.neusoft.springbookdemo.mapper.CheckinMapper;
import edu.neusoft.springbookdemo.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class CheckinService {

    /** 签到奖励分 */
    public static final int CHECKIN_SCORE = 10;
    /** 签到码默认有效分钟数 */
    public static final int DEFAULT_MINUTES = 5;

    private final CheckinMapper checkinMapper;
    private final CourseMapper courseMapper;
    private final ScoreService scoreService;

    /**
     * 教师发起签到：校验课程归属 -> 生成 6 位码 -> 入库 -> 返回 Checkin。
     */
    public Checkin start(Long courseId, Long teacherId, Integer minutes) {
        Course course = courseMapper.findById(courseId);
        if (course == null) throw new IllegalArgumentException("课程不存在");
        if (!course.getTeacherId().equals(teacherId)) {
            throw new IllegalArgumentException("你不是该课程的授课教师");
        }
        int ttl = (minutes == null || minutes <= 0) ? DEFAULT_MINUTES : minutes;
        LocalDateTime now = LocalDateTime.now();

        Checkin c = new Checkin();
        c.setCourseId(courseId);
        c.setTeacherId(teacherId);
        c.setCode(randomCode());
        c.setStartTime(now);
        c.setEndTime(now.plusMinutes(ttl));
        checkinMapper.insertCheckin(c);
        return c;
    }

    /**
     * 学生签到：校验签到码有效 -> 写记录（唯一键防重） -> 加 10 分。
     * 返回是否是新签到（true=首次成功，false=重复签到）。
     */
    @Transactional
    public boolean doCheckin(String code, Long userId) {
        Checkin checkin = checkinMapper.findActiveByCode(code, LocalDateTime.now());
        if (checkin == null) {
            throw new IllegalArgumentException("签到码无效或已过期");
        }
        CheckinRecord r = new CheckinRecord();
        r.setCheckinId(checkin.getId());
        r.setUserId(userId);
        try {
            checkinMapper.insertRecord(r);
        } catch (DataIntegrityViolationException e) {
            return false;
        }
        scoreService.award(userId, CHECKIN_SCORE, "CHECKIN",
                "课程 #" + checkin.getCourseId() + " 签到");
        return true;
    }

    public List<Checkin> listByCourse(Long courseId) {
        return checkinMapper.findByCourse(courseId);
    }

    public int countRecord(Long checkinId) {
        return checkinMapper.countRecord(checkinId);
    }

    /** 今日签到人数（去重） */
    public int todayDistinctUsers() {
        LocalDateTime start = LocalDateTime.now().toLocalDate().atStartOfDay();
        return checkinMapper.countTodayDistinctUsers(start, start.plusDays(1));
    }

    public List<CheckinRecord> recordsOfUser(Long userId) {
        return checkinMapper.findRecordsByUser(userId);
    }

    private String randomCode() {
        return String.valueOf(ThreadLocalRandom.current().nextInt(100000, 1000000));
    }
}
