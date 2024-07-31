package com.ite.authservice.service.serviceImpl;

import com.ite.authservice.config.SystemLogger;
import com.ite.authservice.constants.LogStepConstant;
import com.ite.authservice.constants.RegexConstants;
import com.ite.authservice.entities.EStatus;
import com.ite.authservice.payload.Request.LoginRequest;
import com.ite.authservice.payload.Response.AuthResponse;
import com.ite.authservice.payload.dto.UserDTO;
import com.ite.authservice.repositories.UserRepository;
import com.ite.authservice.security.CustomUserDetailsService;
import com.ite.authservice.security.jwt.UserJwtUtil;
import com.ite.authservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserJwtUtil userJwtUtil;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SystemLogger logger;


}

//        if (StringUtils.isEmpty(userDTO.getEmail())) {
//            return ResponseUtil.getResponseEntity("Email is required", HttpStatus.BAD_REQUEST);
//        }
//
//        if (StringUtils.isEmpty(userDTO.getPassword())) {
//            return ResponseUtil.getResponseEntity("Password is required", HttpStatus.BAD_REQUEST);
//        }
//
//        if (StringUtils.isEmpty(userDTO.getName())) {
//            return ResponseUtil.getResponseEntity("Name is required", HttpStatus.BAD_REQUEST);
//        }
//
//        if (StringUtils.isEmpty(userDTO.getPhone())) {
//            return ResponseUtil.getResponseEntity("Phone number is required", HttpStatus.BAD_REQUEST);
//        }
//        if (!isValidEmail(userDTO.getEmail())) {
//            return ResponseUtil.getResponseEntity("Email is not valid", HttpStatus.BAD_REQUEST);
//        }
//
//        if (!isValidPassword(userDTO.getPassword())) {
//            return ResponseUtil.getResponseEntity("Password is not strong enough", HttpStatus.BAD_REQUEST);
//        }