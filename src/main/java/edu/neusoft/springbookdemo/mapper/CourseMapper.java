package edu.neusoft.springbookdemo.mapper;

import edu.neusoft.springbookdemo.entity.Course;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CourseMapper {
    List<Course> findAll();
    Course findById(@Param("id") Long id);
    List<Course> findByTeacher(@Param("teacherId") Long teacherId);
    int insert(Course course);
    int update(Course course);
    int delete(@Param("id") Long id);
}
