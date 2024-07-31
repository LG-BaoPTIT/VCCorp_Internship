package com.ite.authservice.service.serviceImpl;

import com.ite.authservice.constants.MessageConstants;
import com.ite.authservice.entities.RoleGroup;
import com.ite.authservice.payload.dto.RoleGroupDTO;
import com.ite.authservice.repositories.RoleGroupRepository;
import com.ite.authservice.service.RoleGroupService;
import com.ite.authservice.utils.ResponseUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class RoleGroupServiceImpl implements RoleGroupService {

    @Autowired
    private RoleGroupRepository roleGroupRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RoleGroup create(RoleGroupDTO roleGroupDTO) {
        return roleGroupRepository.save(modelMapper.map(roleGroupDTO,RoleGroup.class));
    }


    @Override
    public RoleGroup getRoleGroupById(String roleGroupId) {
        return roleGroupRepository.findRoleGroupByRoleGroupId(roleGroupId);
    }
    @Override
    public void updateRoleGroup(RoleGroupDTO roleGroupDTO) {
        System.out.println(roleGroupDTO);
        roleGroupRepository.save(modelMapper.map(roleGroupDTO, RoleGroup.class));
    }

    @Override
    public void deleteRoleGroup(String roleGroupId) {
        roleGroupRepository.deleteById(roleGroupId);
    }

    private boolean isValidRoleGroup(RoleGroupDTO roleGroupDTO) {
        return isNullOrEmpty(roleGroupDTO.getRoleGroupId().trim()) &&
                isNullOrEmpty(roleGroupDTO.getName().trim()) &&
                roleGroupDTO.getPermissionIds() != null && !roleGroupDTO.getPermissionIds().isEmpty() &&
                isNullOrEmpty(roleGroupDTO.getDescription().trim());
    }

    private boolean isNullOrEmpty(String str) {
        return str != null && !str.isEmpty();
    }
}
