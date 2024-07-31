package com.ite.authservice.controller;

import com.ite.authservice.constants.MessageConstants;
import com.ite.authservice.payload.Request.LoginRequest;
import com.ite.authservice.service.AccountService;
import com.ite.authservice.service.AdminAuthService;
import com.ite.authservice.payload.Request.VerificationRequest;
import com.ite.authservice.utils.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminAuthController {
    @Autowired
    private AdminAuthService adminAuthService;

    @Autowired
    private AccountService accountService;

//    @PostMapping("/signup")
//    public ResponseEntity<?> signupClient(@RequestBody AdminDTO adminDTO){
//        try{
//            return adminService.signup(adminDTO);
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseUtil.getResponseEntity(MessageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try{
            return adminAuthService.authenticate(loginRequest);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.getResponseEntity(MessageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody VerificationRequest request){
        try{
            return adminAuthService.verifyCode(request);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.getResponseEntity(MessageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/checkSignatureAndAccessToken")
    public ResponseEntity<?> checkSignatureAndAccessToken(HttpServletRequestWrapper requestWrapper) {
        return adminAuthService.checkSignatureAndAccessToken(requestWrapper);
    }

    @GetMapping("/checkSignatureRequest")
    public ResponseEntity<?> checkSignatureRequest(HttpServletRequestWrapper requestWrapper){
        return adminAuthService.checkSignatureRequest(requestWrapper);
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAuthority('LOGOUT')")
    public ResponseEntity<?> adminLogout(HttpServletRequest request){
        return adminAuthService.logout(request);
    }

}
