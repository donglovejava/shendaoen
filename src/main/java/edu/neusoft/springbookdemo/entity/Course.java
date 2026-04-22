package edu.neusoft.springbookdemo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Course {
    private Long id;
    private String title;
    private String description;
    private String cover;
    private Long teacherId;
    private LocalDateTime createdAt;
}
