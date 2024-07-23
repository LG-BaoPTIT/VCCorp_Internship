package com.example.demo.Controller;

import com.example.demo.Service.UserService;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/get-info/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Integer id){
        try {
           // User user= userService.getUserById(id);
            return userService.getUserById(id);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Internal server", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
