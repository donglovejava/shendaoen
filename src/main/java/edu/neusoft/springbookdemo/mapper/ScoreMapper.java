package edu.neusoft.springbookdemo.mapper;

import edu.neusoft.springbookdemo.entity.ScoreRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreMapper {
    int insert(ScoreRecord record);
    List<ScoreRecord> findByUser(@Param("userId") Long userId);
}
