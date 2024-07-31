package com.ite.authservice.payload.dto;

import com.ite.authservice.entities.EPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDTO {
    private String permissionId;
    private EPermission name;
    private String description;
}
