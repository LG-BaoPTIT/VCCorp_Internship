//package com.ite.authservice.controller;
//
//import com.ite.authservice.entities.Token;
//import com.ite.authservice.service.RedisService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/redis")
//public class RedisTestController {
//    @Autowired
//    private RedisService redisService;
//    @PostMapping("/add")
//    public String add(){
//        Token token = Token.builder()
//                .tokenId("LIVE2")
//                .tokenType("Bearer22")
//                .accessToken("999999999999992")
//                .userId("22222223")
//                .build();
//        redisService.setFieldWithTTL("TOKENS", token.getTokenId(),token,1 );
//        return "ok";
//    }
//
//    @GetMapping("/hashGet")
//    public ResponseEntity<?> get(){
//        redisService.set("123","value123123");
//        return ResponseEntity.ok().body(redisService.keyExists("123"));
//
//    }
//    @GetMapping("/get")
//    public ResponseEntity<?> get2(){
//        return ResponseEntity.ok().body(redisService.getValue("123"));
//
//    }
//    @GetMapping("/getFieldPrefies")
//    public ResponseEntity<?> get3(){
//        return ResponseEntity.ok().body(redisService.getFieldPrefies("TOKENS"));
//
//    }
//
//}
