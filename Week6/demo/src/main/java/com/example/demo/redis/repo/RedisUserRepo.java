package com.example.demo.redis.repo;

import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisUserRepo {
    private static final String KEY = "USER_INFO";
    private static final long EXPIRATION_TIME = 6000;
    private final RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    public RedisUserRepo(RedisTemplate<Object, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public User get(Integer id){
        try {
            return (User) redisTemplate.opsForHash().get(KEY, id);
        } catch (Exception e) {
            return null;
        }
    }

    public void put(User user){
        Random random = new Random();
        redisTemplate.opsForHash().put(KEY, user.getId(), user);
        redisTemplate.expire(KEY, EXPIRATION_TIME, TimeUnit.SECONDS);
    }

    public void delete(int hashKey){
        try {
            redisTemplate.opsForHash().delete(KEY, hashKey);
        } catch (RedisConnectionFailureException e) {
            // handle exception
        }
    }
}
