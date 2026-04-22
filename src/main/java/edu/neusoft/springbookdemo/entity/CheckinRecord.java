package edu.neusoft.springbookdemo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CheckinRecord {
    private Long id;
    private Long checkinId;
    private Long userId;
    private LocalDateTime checkinTime;
}
