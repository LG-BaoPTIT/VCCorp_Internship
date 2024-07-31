package com.ite.authservice.payload.dto;

import com.ite.authservice.entities.EStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private Timestamp registrationDate;
    private EStatus status;
    private String roleGroupId;
}
