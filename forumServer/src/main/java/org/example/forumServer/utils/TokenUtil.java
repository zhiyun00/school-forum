package org.example.forumServer.utils;

import org.example.forumServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenUtil {
    @Autowired
    RedisCache redisCache;

    public String createToken(User user) {
        String token = UUID.randomUUID().toString().replace("-", "");
        redisCache.set(token, user, 60 * 30);
        return token;
    }

    public boolean checkToken(String token) {
        return redisCache.hasKey(token) ? true : false;
    }

    public boolean setTokenExpire(String token) {
        redisCache.expire(token, 60 * 30);
        return true;
    }

    public User getCacheUser(String token) {
        return (User) redisCache.get(token);
    }

    public void setToken(String token, User user) {
        redisCache.set(token, user, 60 * 30);
    }

    public void deleteToken(String token) {
        redisCache.del(token);
    }
}
