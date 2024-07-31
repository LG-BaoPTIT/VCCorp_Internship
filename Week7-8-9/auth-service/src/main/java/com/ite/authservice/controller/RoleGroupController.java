//package com.ite.authservice.controller;
//
//import com.ite.authservice.constants.MessageConstants;
//import com.ite.authservice.payload.dto.RoleGroupDTO;
//import com.ite.authservice.service.RoleGroupService;
//import com.ite.authservice.utils.ResponseUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin
//@RestController
//@RequestMapping("/roleGroup")
//public class RoleGroupController {
//    @Autowired
//    private RoleGroupService roleGroupService;
//    @PostMapping("/add")
//    //@PreAuthorize("hasPermission('USER_PROFILE')")
//    public ResponseEntity<?> addRole(@RequestBody RoleGroupDTO roleGroupDTO){
//        try{
//            return roleGroupService.AddRoleGroup(roleGroupDTO);
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResponseUtil.getResponseEntity(MessageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
