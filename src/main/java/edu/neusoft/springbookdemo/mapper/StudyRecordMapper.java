package edu.neusoft.springbookdemo.mapper;

import edu.neusoft.springbookdemo.entity.StudyRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudyRecordMapper {
    StudyRecord findByUserChapter(@Param("userId") Long userId, @Param("chapterId") Long chapterId);
    int upsert(StudyRecord record);
    List<StudyRecord> findByUser(@Param("userId") Long userId);
}
