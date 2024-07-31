package com.ite.authservice.service;

import com.ite.authservice.entities.RoleGroup;
import com.ite.authservice.payload.dto.RoleGroupDTO;
import org.springframework.http.ResponseEntity;

public interface RoleGroupService {
    RoleGroup getRoleGroupById(String roleGroupId);
    void deleteRoleGroup(String roleGroupId);
    void updateRoleGroup(RoleGroupDTO roleGroupDTO);
    RoleGroup create(RoleGroupDTO roleGroupDTO);
}
