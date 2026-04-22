package edu.neusoft.springbookdemo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ScoreRecord {
    private Long id;
    private Long userId;
    private Integer score;
    private String source;
    private String remark;
    private LocalDateTime createdAt;
}
