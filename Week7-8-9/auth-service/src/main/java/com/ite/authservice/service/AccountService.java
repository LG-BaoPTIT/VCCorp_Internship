package com.ite.authservice.service;

import com.ite.authservice.payload.dto.AdminDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

public interface AccountService {
    void createAdminAccount(AdminDTO adminDTO);
    void updateAdminAccount(AdminDTO adminDTO);



}
