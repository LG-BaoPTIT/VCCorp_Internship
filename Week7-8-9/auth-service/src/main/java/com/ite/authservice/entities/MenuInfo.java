package com.ite.authservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "menu_info")
public class MenuInfo {
    @Id
    private String menu_id;
    private String permissionId;
    private String url;
    private String menu_icon;
    private String mapped_url;
    private int menu_level;
    private String menu_title;
    private List<MenuInfo> children;
}
