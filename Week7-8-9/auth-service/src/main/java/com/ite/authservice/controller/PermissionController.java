//package com.ite.authservice.controller;
//
//import com.ite.authservice.constants.MessageConstants;
//import com.ite.authservice.payload.dto.PermissionDTO;
//import com.ite.authservice.service.PermissionService;
//import com.ite.authservice.utils.ResponseUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin
//@RestController
//@RequestMapping("/permission")
//public class PermissionController {
//    @Autowired
//    private PermissionService permissionService;
//    @PostMapping("/add")
//    public ResponseEntity<?> addPermission(@RequestBody PermissionDTO permissionDTO){
//        try{
//            return permissionService.addPermission(permissionDTO);
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseUtil.getResponseEntity(MessageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
