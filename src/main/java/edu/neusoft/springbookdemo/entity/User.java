package edu.neusoft.springbookdemo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String role;
    private String avatar;
    private Integer totalScore;
    private Boolean enabled;
    private LocalDateTime createdAt;
}
