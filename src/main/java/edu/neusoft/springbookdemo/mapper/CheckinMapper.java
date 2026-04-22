package edu.neusoft.springbookdemo.mapper;

import edu.neusoft.springbookdemo.entity.Checkin;
import edu.neusoft.springbookdemo.entity.CheckinRecord;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CheckinMapper {
    int insertCheckin(Checkin checkin);
    Checkin findActiveByCode(@Param("code") String code, @Param("now") LocalDateTime now);
    Checkin findById(@Param("id") Long id);
    List<Checkin> findByCourse(@Param("courseId") Long courseId);

    int insertRecord(CheckinRecord record);
    int countRecord(@Param("checkinId") Long checkinId);
    int countTodayDistinctUsers(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
    List<CheckinRecord> findRecordsByUser(@Param("userId") Long userId);
}
