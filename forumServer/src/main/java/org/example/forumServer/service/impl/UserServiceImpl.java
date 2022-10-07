package org.example.forumServer.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.example.forumServer.constant.UserCst;
import org.example.forumServer.dao.IUserDao;
import org.example.forumServer.model.User;
import org.example.forumServer.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    @Override
    public PageInfo<User> findAll(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userDao.findAll();
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public User findOneUserById(Integer userId) {
        return userDao.findOneUserById(userId);
    }

    @Override
    public PageInfo<User> findUserByCondition(User user, Integer pageNum, Integer pageSize) {
        if (null != user.getUsername() && !"".equals(user.getUsername()))
            user.setUsername("%" + user.getUsername() + "%");
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = userDao.findUserByCondition(user);
        PageInfo<User> pageInfo = new PageInfo<>(userList);
        return pageInfo;
    }

    @Override
    public Boolean addUser(User user) {
        User condition = new User();
        condition.setUsername(user.getUsername());
        //判断用户名是否已经存在
        if (this.findUserByCondition(condition, 0, 10).getList().size() > 0) {
            return false;
        }
        return userDao.addUser(user) > 0 ? true : false;
    }

    @Override
    public Boolean updateUser(User user) {
        return userDao.updateUser(user) > 0 ? true : false;
    }

    @Override
    public User loginUser(User user) {
        User loginUser = userDao.findOneUserByUnameAndPwd(user);
        if (null != loginUser) userDao.updateUserLoginInfo(loginUser);
        return loginUser;
    }

    @Override
    public Boolean updateCount(User user) {
        return userDao.updateCount(user) > 0 ? true : false;
    }

    @Override
    public Boolean delOrDisableUser(Integer userId, Boolean isToDel) {
        Integer status;
        if (isToDel) {
            status = UserCst.USER_STATUS_DEL;
        } else {
            if (this.findOneUserById(userId).getStatus() == UserCst.USER_STATUS_ENABLE) {
                status = UserCst.USER_STATUS_DISABLE;
            } else {
                status = UserCst.USER_STATUS_ENABLE;
            }
        }
        User user = new User();
        user.setUserId(userId);
        user.setStatus(status);
        return userDao.updateUser(user) > 0 ? true : false;
    }

    @Override
    public Boolean resetPassword(String email, String newpwd) {
        User condition = new User();
        condition.setEmail(email);
        User user = this.findUserByCondition(condition, 0, 10).getList().get(0);
        user.setPassword(newpwd);
        return this.updateUser(user);
    }
}
