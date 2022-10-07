package org.example.forumServer.controller;

import com.github.pagehelper.PageInfo;
import org.example.forumServer.constant.UserCst;
import org.example.forumServer.model.User;
import org.example.forumServer.service.IUserService;
import org.example.forumServer.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    RedisCache redisCache;

    @Autowired
    ImgCodeUtil imgCodeUtil;

    @RequestMapping("/login")
    public JsonResult userLogin(String username, String password, String imgcodeId, String code) {
        JsonResult result = new JsonResult();
        //判空操作
        if (null == username || "".equals(username) || null == password || "".equals(password)) {
            result.setFail(UserCst.NULL_ERR);
            return result;
        }
        // 验证码判空操作
        if (CommonUtil.strIsNull(imgcodeId) || CommonUtil.strIsNull(code)) {
            result.setFail(UserCst.IMG_CODE_NULL_ERR);
            return result;
        }
        if (!imgCodeUtil.checkImgCode(imgcodeId, code)) {
            result.setFail(UserCst.IMG_CODE_ERR);
            return result;
        }
        //根据用户名密码构建一个用户并进行数据库查询
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        User resUser = userService.loginUser(user);
        //查到的用户不为空时返回登录成功信息
        if (resUser != null) {
            //构造登录Token
            String token = tokenUtil.createToken(resUser);
            result.setSuccess(UserCst.LOGIN_SUCCESS);
            result.setToken(token);
            result.addDatas("role", resUser.getRole()); //用户身份
            return result;
        }
        result.setFail(UserCst.PWD_ERR);
        return result;
    }

    @RequestMapping("/check")
    public JsonResult userCheck(@RequestHeader(value = "token") String token) {
        JsonResult result = new JsonResult();
        result.setSuccess();
        result.addDatas("user", tokenUtil.getCacheUser(token));
        return result;
    }

    @RequestMapping("/logout")
    public boolean userLogout(@RequestHeader(value = "token") String token) {
        tokenUtil.deleteToken(token);
        return true;
    }

    @RequestMapping("/backlogin")
    public JsonResult backLogin() {
        JsonResult result = new JsonResult();
        result.setFail("未登录！");
        result.setCode(UserCst.UN_LOGIN);
        return result;
    }

    @RequestMapping("/register")
    public JsonResult userRegister(@RequestBody User user, String code) {
        JsonResult result = new JsonResult();
        //判空操作
        if (CommonUtil.strIsNull(user.getUsername()) || CommonUtil.strIsNull(user.getPassword())) {
            result.setFail(UserCst.NULL_ERR);
            return result;
        }
        if (CommonUtil.strIsNull(user.getEmail()) || CommonUtil.strIsNull(code)) {
            result.setFail(UserCst.EMAIL_NULL_ERR);
            return result;
        }
        //邮件验证码验证
        if (CommonUtil.strIsNull((String) redisCache.get(user.getEmail())) || !redisCache.get(user.getEmail()).equals(code)) {
            result.setFail(UserCst.EMAIL_CODE_ERR);
            return result;
        }
        if (userService.addUser(user)) {
            result.setSuccess();
            redisCache.del(user.getEmail());
        } else {
            result.setFail(UserCst.USERNAME_IS_EXIST);
        }
        return result;
    }

    @RequestMapping("/resetpwd")
    public JsonResult resetPassword(String email, String code, String newpwd) {
        JsonResult result = new JsonResult();
        //判空操作
        if (CommonUtil.strIsNull(email) || CommonUtil.strIsNull(code)) {
            result.setFail(UserCst.EMAIL_NULL_ERR);
            return result;
        }
        if (CommonUtil.strIsNull(newpwd)) {
            result.setFail(UserCst.NEWPWD_NULL_ERR);
            return result;
        }
        //邮件验证码验证
        if (CommonUtil.strIsNull((String) redisCache.get(email)) || !redisCache.get(email).equals(code)) {
            result.setFail(UserCst.EMAIL_CODE_ERR);
            return result;
        }
        if (userService.resetPassword(email, newpwd)) {
            result.setSuccess();
            redisCache.del(email);
        } else {
            result.setSysErr();
        }
        return result;
    }

    @RequestMapping("/findall")
    public JsonResult findAll(Integer pageNum, Integer pageSize) {
        JsonResult result = new JsonResult();
        result.setSuccess();
        PageInfo<User> users = userService.findAll(pageNum, pageSize);
        result.addDatas("userListWithPage", users);
        return result;
    }

    @RequestMapping("/ableuser")
    public JsonResult ableUser(Integer userId) {
        JsonResult result = new JsonResult();
        //service层处理确定是启用还是禁用
        if (userService.delOrDisableUser(userId, false)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }

    @RequestMapping("/deluser")
    public JsonResult delUser(Integer userId) {
        JsonResult result = new JsonResult();
        if (userService.delOrDisableUser(userId, true)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }

    @RequestMapping("/updateuser")
    public JsonResult updateUser(@RequestBody User user, @RequestHeader(value = "token") String token) {
        JsonResult result = new JsonResult();
        if (userService.updateUser(user)) {
            result.setSuccess();
            //如果是用户操作，更新用户登录的缓存信息
            User cacheUser = tokenUtil.getCacheUser(token);
            if (cacheUser.getRole() != UserCst.USER_ROLE_ADMIN) {
                tokenUtil.setToken(token, userService.findOneUserById(user.getUserId()));
            }
        } else {
            result.setSysErr();
        }
        return result;
    }

    @RequestMapping("/adduser")
    public JsonResult addUser(@RequestBody User user) {
        JsonResult result = new JsonResult();
        if (userService.addUser(user)) {
            result.setSuccess();
        } else {
            result.setSysErr();
        }
        return result;
    }

    @RequestMapping("/findone")
    public JsonResult findOneUserById(Integer userId) {
        JsonResult result = new JsonResult();
        User user = userService.findOneUserById(userId);
        if (user != null) {
            result.setSuccess();
            result.addDatas("userInfo", user);
        } else {
            result.setFail("未找到此用户！");
        }
        return result;
    }

    @RequestMapping("/findonebycondition")
    public JsonResult findUserByCondition(@RequestBody User user, Integer pageNum, Integer pageSize) {
        JsonResult result = new JsonResult();
        result.setSuccess();
        PageInfo<User> users = userService.findUserByCondition(user, pageNum, pageSize);
        result.addDatas("userListWithPage", users);
        return result;
    }
}
