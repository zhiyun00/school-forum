package org.example.forumServer.dao;

import org.example.forumServer.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserDao {
    List<User> findAll();

    User findOneUserById(Integer userId);

    User findOneUserByUnameAndPwd(User user);

    List<User> findUserByCondition(User user);

    Integer addUser(User user);

    Integer updateUser(User user);

    Integer updateUserLoginInfo(User user);

    Integer updateCount(User user);
}
