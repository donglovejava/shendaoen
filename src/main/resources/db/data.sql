-- 测试用户的占位记录，密码字段先留空，由 DataInitializer 启动时用 BCrypt 重新写入
-- 明文密码统一 123456

INSERT IGNORE INTO `user` (id, username, password, nickname, role) VALUES
(1, 'teacher', '', '王老师', 'TEACHER'),
(2, 'student', '', '小明',   'STUDENT'),
(3, 'admin',   '', '管理员', 'ADMIN');

INSERT IGNORE INTO `course` (id, title, description, teacher_id) VALUES
(1, 'Java 程序设计基础', '从零开始学 Java，覆盖语法、OOP、集合框架', 1),
(2, 'Spring Boot 实战',  '企业级后端开发入门',                         1);

INSERT IGNORE INTO `chapter` (id, course_id, title, sort_order) VALUES
(1, 1, '第 1 章 Java 简介与环境搭建', 1),
(2, 1, '第 2 章 变量与数据类型',      2),
(3, 2, '第 1 章 Spring Boot 快速入门', 1);
