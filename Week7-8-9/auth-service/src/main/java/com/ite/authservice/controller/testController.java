//package com.ite.authservice.controller;
//
//
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@CrossOrigin
//@RestController
//@RequestMapping("/admin/test")
//public class testController {
//
//
//    @GetMapping("/LOGOUT")
//    @PreAuthorize("hasAuthority('LOGOUT')")
//    public String test1(){
//        return "LOGOUT";
//    }
//
//    @GetMapping("/UPDATE_ACCOUNT_STATUS")
//    @PreAuthorize("hasAuthority('UPDATE_ACCOUNT_STATUS')")
//    public String test2(){
//        return "UPDATE_ACCOUNT_STATUS";
//    }
//
//    @GetMapping("/UPDATE_ACCOUNT_INFO")
//    @PreAuthorize("hasAuthority('UPDATE_ACCOUNT_INFO')")
//    public String test3(){
//        return "UPDATE_ACCOUNT_INFO";
//    }
//
//
//
//}
