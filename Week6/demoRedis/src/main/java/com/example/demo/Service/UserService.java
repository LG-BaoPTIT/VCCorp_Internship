package com.example.demo.Service;

import com.example.demo.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface UserService {
    ResponseEntity<?> getUserById(Integer id);
}
