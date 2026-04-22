package edu.neusoft.springbookdemo.mapper;

import edu.neusoft.springbookdemo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    User findByUsername(@Param("username") String username);

    User findById(@Param("id") Long id);

    int insert(User user);

    int addScore(@Param("userId") Long userId, @Param("delta") int delta);

    List<User> topByScore(@Param("limit") int limit);
}
