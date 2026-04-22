package edu.neusoft.springbookdemo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Chapter {
    private Long id;
    private Long courseId;
    private String title;
    private Integer sortOrder;
    private String videoPath;
    private String pptPath;
    private LocalDateTime createdAt;
}
