package edu.neusoft.springbookdemo.mapper;

import edu.neusoft.springbookdemo.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterMapper {
    List<Chapter> findByCourse(@Param("courseId") Long courseId);
    Chapter findById(@Param("id") Long id);
    int insert(Chapter chapter);
    int update(Chapter chapter);
    int delete(@Param("id") Long id);
}
