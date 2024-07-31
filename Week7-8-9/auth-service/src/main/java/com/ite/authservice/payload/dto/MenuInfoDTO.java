package com.ite.authservice.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuInfoDTO {
    private int menu_id;
    private String url;
    private String menu_icon;
    private String mapped_url;
    private int menu_level;
    private String menu_title;
    private List<MenuInfoDTO> children;
}
