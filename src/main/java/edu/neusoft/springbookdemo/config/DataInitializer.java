package edu.neusoft.springbookdemo.config;

import edu.neusoft.springbookdemo.entity.User;
import edu.neusoft.springbookdemo.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 启动时把测试账号的密码用当前 BCrypt 重新写入，确保哈希与 Security 使用的一致。
 * 这样任何环境第一次启动后，teacher / student / admin 的密码都是 123456。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private static final String DEFAULT_PASSWORD = "123456";
    private static final List<String> SEED_USERS = List.of("teacher", "student", "admin");

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        String hash = passwordEncoder.encode(DEFAULT_PASSWORD);
        for (String username : SEED_USERS) {
            User u = userMapper.findByUsername(username);
            if (u == null) continue;
            if (u.getPassword() == null || u.getPassword().isBlank()
                    || !passwordEncoder.matches(DEFAULT_PASSWORD, u.getPassword())) {
                jdbcTemplate.update("UPDATE user SET password = ? WHERE id = ?", hash, u.getId());
                log.info("已重置测试账号密码: {} -> 123456", username);
            }
        }
    }
}
