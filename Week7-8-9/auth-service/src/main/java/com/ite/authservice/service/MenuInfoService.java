package com.ite.authservice.service;

import com.ite.authservice.entities.RoleGroup;
import com.ite.authservice.payload.dto.MenuInfoDTO;

import java.util.List;

public interface MenuInfoService {
    List<MenuInfoDTO> getListMenuInfoByRoleGroup(String roleGroupId);
}
