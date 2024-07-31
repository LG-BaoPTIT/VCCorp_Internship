package com.ite.authservice.service.serviceImpl;

import com.ite.authservice.constants.MessageConstants;
import com.ite.authservice.entities.Permission;
import com.ite.authservice.repositories.PermissionRepository;
import com.ite.authservice.payload.dto.PermissionDTO;
import com.ite.authservice.service.PermissionService;
import com.ite.authservice.utils.ResponseUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ResponseEntity<?> addPermission(PermissionDTO permissionDTO) {
        try{
            permissionRepository.save(modelMapper.map(permissionDTO, Permission.class));
            return ResponseUtil.getResponseEntity("Add permission successfully", HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseUtil.getResponseEntity(MessageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Permission getPermissionById(String permissionId) {
        return permissionRepository.findPermissionByPermissionId(permissionId);
    }
}
