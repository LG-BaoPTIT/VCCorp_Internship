package com.ite.authservice.payload.Response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ite.authservice.payload.dto.MenuInfoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthResponse {

    private UserInfo user_info;
    private String message;
    private String accessToken;
    private String refreshToken;
    private int expired;
    private List<MenuInfoDTO> menu_info;
    private boolean mfaEnabled;
}

