package edu.neusoft.springbookdemo.service;

import edu.neusoft.springbookdemo.entity.User;
import edu.neusoft.springbookdemo.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public void register(String username, String rawPassword, String nickname) {
        if (userMapper.findByUsername(username) != null) {
            throw new IllegalArgumentException("用户名已存在");
        }
        User u = new User();
        u.setUsername(username);
        u.setPassword(passwordEncoder.encode(rawPassword));
        u.setNickname(nickname == null || nickname.isBlank() ? username : nickname);
        u.setRole("STUDENT");
        userMapper.insert(u);
    }

    public User getByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public List<User> topStudentsByScore(int limit) {
        return userMapper.topByScore(limit);
    }
}
