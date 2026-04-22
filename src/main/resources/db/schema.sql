-- 智慧教育平台数据库表结构
-- 启动时自动执行，CREATE TABLE IF NOT EXISTS 保证幂等

CREATE TABLE IF NOT EXISTS `user` (
    `id`         BIGINT       NOT NULL AUTO_INCREMENT,
    `username`   VARCHAR(50)  NOT NULL UNIQUE COMMENT '登录名',
    `password`   VARCHAR(100) NOT NULL COMMENT 'BCrypt 加密密码',
    `nickname`   VARCHAR(50)           DEFAULT NULL COMMENT '昵称',
    `role`       VARCHAR(20)  NOT NULL DEFAULT 'STUDENT' COMMENT 'STUDENT / TEACHER / ADMIN',
    `avatar`     VARCHAR(255)          DEFAULT NULL,
    `total_score` INT         NOT NULL DEFAULT 0 COMMENT '累计得分',
    `enabled`    TINYINT(1)   NOT NULL DEFAULT 1,
    `created_at` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_role` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

CREATE TABLE IF NOT EXISTS `course` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `title`       VARCHAR(100) NOT NULL COMMENT '课程名',
    `description` TEXT                  DEFAULT NULL,
    `cover`       VARCHAR(255)          DEFAULT NULL COMMENT '封面图路径',
    `teacher_id`  BIGINT       NOT NULL COMMENT '授课教师',
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_teacher` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程表';

CREATE TABLE IF NOT EXISTS `chapter` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `course_id`   BIGINT       NOT NULL,
    `title`       VARCHAR(100) NOT NULL,
    `sort_order`  INT          NOT NULL DEFAULT 0,
    `video_path`  VARCHAR(255)          DEFAULT NULL COMMENT '视频文件相对路径',
    `ppt_path`    VARCHAR(255)          DEFAULT NULL COMMENT 'PPT/PDF 文件相对路径',
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_course` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='章节表';

CREATE TABLE IF NOT EXISTS `checkin` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `course_id`   BIGINT       NOT NULL,
    `teacher_id`  BIGINT       NOT NULL,
    `code`        VARCHAR(10)  NOT NULL COMMENT '签到码',
    `start_time`  DATETIME     NOT NULL,
    `end_time`    DATETIME     NOT NULL COMMENT '签到码过期时间',
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_code` (`code`),
    KEY `idx_course_time` (`course_id`, `start_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='签到场次表';

CREATE TABLE IF NOT EXISTS `checkin_record` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `checkin_id`  BIGINT       NOT NULL,
    `user_id`     BIGINT       NOT NULL,
    `checkin_time` DATETIME    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_checkin_user` (`checkin_id`, `user_id`),
    KEY `idx_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学生签到记录';

CREATE TABLE IF NOT EXISTS `score_record` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `user_id`     BIGINT       NOT NULL,
    `score`       INT          NOT NULL COMMENT '本次得分（正可负）',
    `source`      VARCHAR(30)  NOT NULL COMMENT 'CHECKIN / STUDY / MANUAL',
    `remark`      VARCHAR(255)          DEFAULT NULL,
    `created_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_user_time` (`user_id`, `created_at`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='得分流水表';

CREATE TABLE IF NOT EXISTS `study_record` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `user_id`     BIGINT       NOT NULL,
    `chapter_id`  BIGINT       NOT NULL,
    `progress`    INT          NOT NULL DEFAULT 0 COMMENT '百分比 0-100',
    `updated_at`  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_chapter` (`user_id`, `chapter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学习进度';
