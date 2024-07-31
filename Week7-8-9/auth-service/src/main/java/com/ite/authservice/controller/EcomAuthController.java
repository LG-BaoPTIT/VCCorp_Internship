package com.ite.authservice.controller;

import com.ite.authservice.constants.MessageConstants;
import com.ite.authservice.payload.Request.LoginRequest;
import com.ite.authservice.service.AccountService;
import com.ite.authservice.service.EcomAuthService;
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
@RequestMapping("/ecommerce")
public class EcomAuthController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private EcomAuthService ecomAuthService;

//    @PostMapping("/signup")
//    public ResponseEntity<?> signupClient(@RequestBody UserDTO userDTO){
//        try{
//            return userService.signup(userDTO);
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseUtil.getResponseEntity(MessageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping("/test")
//    @PreAuthorize("hasAuthority('ROLE_USER')")
//    public String test2(){
//        return "user test";
//    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,HttpServletRequest request){
        try{
            return ecomAuthService.login(loginRequest,request);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.getResponseEntity(MessageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/logout")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<?> userLogout(HttpServletRequest request){
        return ecomAuthService.logout(request);
    }

    @GetMapping("/checkSignatureAndAccessToken")
    public ResponseEntity<?> checkSignatureAndAccessToken(HttpServletRequestWrapper requestWrapper) {
        return ecomAuthService.checkSignatureAndAccessToken(requestWrapper);
    }

    @GetMapping("/checkSignatureRequest")
    public ResponseEntity<?> checkSignatureRequest(HttpServletRequestWrapper requestWrapper){
        return ecomAuthService.checkSignatureRequest(requestWrapper);
    }
}
