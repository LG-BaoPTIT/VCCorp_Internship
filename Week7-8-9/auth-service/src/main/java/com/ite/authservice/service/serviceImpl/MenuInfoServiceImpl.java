package com.ite.authservice.service.serviceImpl;

import com.ite.authservice.entities.MenuInfo;
import com.ite.authservice.entities.RoleGroup;
import com.ite.authservice.payload.dto.MenuInfoDTO;
import com.ite.authservice.repositories.MenuInfoRepository;
import com.ite.authservice.repositories.RoleGroupRepository;
import com.ite.authservice.service.MenuInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MenuInfoServiceImpl implements MenuInfoService {

    @Autowired
    private MenuInfoRepository menuInfoRepository;

    @Autowired
    private RoleGroupRepository roleGroupRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public List<MenuInfoDTO> getListMenuInfoByRoleGroup(String roleGroupId) {
        RoleGroup roleGroup = roleGroupRepository.findRoleGroupByRoleGroupId(roleGroupId);
        List<String> permissionIds = roleGroup.getPermissionIds();
        Collections.sort(permissionIds);
        List<MenuInfoDTO> menuInfoDTOList = new ArrayList<>();
        permissionIds.forEach(permissionId -> {
            MenuInfo menuInfo = menuInfoRepository.findMenuInfosByPermissionId(permissionId);
            if(!Objects.isNull(menuInfo)){
                if (menuInfo.getChildren() != null) {
                    List<MenuInfo> filteredChildren = menuInfo.getChildren().stream()
                            .filter(child -> permissionIds.contains(child.getPermissionId()))
                            .collect(Collectors.toList());
                    menuInfo.setChildren(filteredChildren);
                }
                menuInfoDTOList.add(modelMapper.map(menuInfo, MenuInfoDTO.class));
            }

        });
        return menuInfoDTOList;
    }
}
