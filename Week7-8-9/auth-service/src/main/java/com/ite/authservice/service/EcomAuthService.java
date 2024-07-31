package com.ite.authservice.service;

import com.ite.authservice.payload.Request.LoginRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public interface EcomAuthService {
    ResponseEntity<?> login(LoginRequest loginRequest,HttpServletRequest request);
    ResponseEntity<?> logout(HttpServletRequest request);
    ResponseEntity<?> checkAccessToken(String token);
    ResponseEntity<?> checkSignatureRequest(HttpServletRequestWrapper requestWrapper);

    ResponseEntity<?> checkSignatureAndAccessToken(HttpServletRequestWrapper requestWrapper);
}
