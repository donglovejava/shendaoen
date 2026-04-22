package edu.neusoft.springbookdemo.service;

import edu.neusoft.springbookdemo.entity.Chapter;
import edu.neusoft.springbookdemo.entity.Course;
import edu.neusoft.springbookdemo.mapper.ChapterMapper;
import edu.neusoft.springbookdemo.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseMapper courseMapper;
    private final ChapterMapper chapterMapper;

    public List<Course> listAll() { return courseMapper.findAll(); }

    public List<Course> listByTeacher(Long teacherId) { return courseMapper.findByTeacher(teacherId); }

    public Course get(Long id) { return courseMapper.findById(id); }

    public List<Chapter> chaptersOf(Long courseId) { return chapterMapper.findByCourse(courseId); }

    public Chapter chapter(Long id) { return chapterMapper.findById(id); }

    public Course create(String title, String description, Long teacherId) {
        Course c = new Course();
        c.setTitle(title);
        c.setDescription(description);
        c.setTeacherId(teacherId);
        courseMapper.insert(c);
        return c;
    }

    public void update(Long id, String title, String description) {
        Course c = courseMapper.findById(id);
        if (c == null) throw new IllegalArgumentException("课程不存在");
        c.setTitle(title);
        c.setDescription(description);
        courseMapper.update(c);
    }

    @Transactional
    public void delete(Long id) {
        courseMapper.delete(id);
    }

    public Chapter addChapter(Long courseId, String title, Integer sortOrder,
                              String videoPath, String pptPath) {
        Chapter ch = new Chapter();
        ch.setCourseId(courseId);
        ch.setTitle(title);
        ch.setSortOrder(sortOrder == null ? 0 : sortOrder);
        ch.setVideoPath(videoPath);
        ch.setPptPath(pptPath);
        chapterMapper.insert(ch);
        return ch;
    }

    public void deleteChapter(Long id) { chapterMapper.delete(id); }
}
