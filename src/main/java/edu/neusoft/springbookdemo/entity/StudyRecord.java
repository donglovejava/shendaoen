package edu.neusoft.springbookdemo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class StudyRecord {
    private Long id;
    private Long userId;
    private Long chapterId;
    private Integer progress;
    private LocalDateTime updatedAt;
}
