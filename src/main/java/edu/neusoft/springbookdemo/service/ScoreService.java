package edu.neusoft.springbookdemo.service;

import edu.neusoft.springbookdemo.entity.ScoreRecord;
import edu.neusoft.springbookdemo.mapper.ScoreMapper;
import edu.neusoft.springbookdemo.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final ScoreMapper scoreMapper;
    private final UserMapper userMapper;

    /**
     * 给用户加分：写流水 + 更新 user.total_score，同一事务保证一致。
     */
    @Transactional
    public void award(Long userId, int score, String source, String remark) {
        ScoreRecord r = new ScoreRecord();
        r.setUserId(userId);
        r.setScore(score);
        r.setSource(source);
        r.setRemark(remark);
        scoreMapper.insert(r);
        userMapper.addScore(userId, score);
    }

    public List<ScoreRecord> listByUser(Long userId) {
        return scoreMapper.findByUser(userId);
    }
}
