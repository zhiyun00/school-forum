package org.example.forumServer.service;

import com.github.pagehelper.PageInfo;
import org.example.forumServer.model.User;

public interface IUserService {
    PageInfo<User> findAll(Integer pageNum, Integer pageSize);

    User findOneUserById(Integer userId);

    PageInfo<User> findUserByCondition(User user, Integer pageNum, Integer pageSize);

    Boolean addUser(User user);

    Boolean updateUser(User user);

    User loginUser(User user);

    Boolean updateCount(User user);

    Boolean delOrDisableUser(Integer userId, Boolean isToDel);

    Boolean resetPassword(String email, String newpwd);
}
