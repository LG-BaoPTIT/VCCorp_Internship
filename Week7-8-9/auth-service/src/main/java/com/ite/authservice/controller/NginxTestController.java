//package com.ite.authservice.controller;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RestController
//@RequestMapping("/testauth")
//public class NginxTestController {
//
//    @GetMapping("/hihi")
//    public ResponseEntity<?> hihi (){
//        String id = "12345";
//
//        // Tạo một đối tượng HttpHeaders để chứa các header
//        HttpHeaders headers = new HttpHeaders();
//
//        // Gắn giá trị ID vào header "X-User-ID"
//        headers.set("X-User-Id", id);
//
//        // Tạo một đối tượng ResponseEntity với body là "ok auth" và headers đã được chỉ định
//        return ResponseEntity.ok().headers(headers).body("ok auth");
//
//    }
//}
