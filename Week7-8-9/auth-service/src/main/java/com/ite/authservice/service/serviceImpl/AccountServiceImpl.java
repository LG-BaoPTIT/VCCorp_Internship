package com.ite.authservice.service.serviceImpl;

import com.ite.authservice.config.SystemLogger;
import com.ite.authservice.entities.Admin;
import com.ite.authservice.payload.dto.AdminDTO;
import com.ite.authservice.repositories.AdminRepository;
import com.ite.authservice.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SystemLogger logger;

    @Override
    public void createAdminAccount(AdminDTO adminDTO) {
        adminRepository.save(modelMapper.map(adminDTO, Admin.class));
    }

    @Override
    public void updateAdminAccount(AdminDTO adminDTO) {
        adminRepository.save(modelMapper.map(adminDTO,Admin.class));
    }



}
