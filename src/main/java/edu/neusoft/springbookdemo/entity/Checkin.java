package edu.neusoft.springbookdemo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Checkin {
    private Long id;
    private Long courseId;
    private Long teacherId;
    private String code;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime createdAt;
}
