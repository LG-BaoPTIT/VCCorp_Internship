package com.ite.authservice.service;

import com.ite.authservice.payload.Request.LoginRequest;
import com.ite.authservice.payload.Request.VerificationRequest;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public interface AdminAuthService {
    //ResponseEntity<?> signup(AdminDTO adminDTO);
    ResponseEntity<?> authenticate(LoginRequest loginRequest);
    ResponseEntity<?> verifyCode(VerificationRequest request);
    ResponseEntity<?> checkSignatureAndAccessToken(HttpServletRequestWrapper requestWrapper);
    ResponseEntity<?> checkSignatureRequest(HttpServletRequestWrapper requestWrapper);
    ResponseEntity<?> logout(HttpServletRequest request);
}
