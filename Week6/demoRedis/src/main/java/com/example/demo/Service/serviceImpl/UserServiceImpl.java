package com.example.demo.Service.serviceImpl;

import com.example.demo.Service.UserService;
import com.example.demo.entity.User;
import com.example.demo.redis.repo.RedisUserRepo;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RedisUserRepo redisUserRepo;
    @Override
    public ResponseEntity<?> getUserById(Integer id) {

        User user = redisUserRepo.get(id);

        if(user != null){
            System.out.println("get from redis");
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent()){
            return new ResponseEntity<>("User is not present",HttpStatus.NOT_FOUND);
        }
        redisUserRepo.put(userOptional.get());
        System.out.println("get from db");

        return new ResponseEntity<>(userOptional.get(),HttpStatus.OK);
    }
}
