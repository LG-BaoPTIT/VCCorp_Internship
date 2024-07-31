package com.ite.authservice.service;

import com.ite.authservice.entities.Permission;
import com.ite.authservice.payload.dto.PermissionDTO;
import org.springframework.http.ResponseEntity;

public interface PermissionService {
    ResponseEntity<?> addPermission(PermissionDTO permissionDTO);
    Permission getPermissionById(String permissionId);
}
