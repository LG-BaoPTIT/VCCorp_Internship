package com.ite.authservice.security;

import com.ite.authservice.entities.User;
import com.ite.authservice.service.PermissionService;
import com.ite.authservice.entities.Admin;
import com.ite.authservice.repositories.AdminRepository;
import com.ite.authservice.repositories.UserRepository;
import com.ite.authservice.service.RoleGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private RoleGroupService roleGroupService;

    @Autowired
    private PermissionService permissionService;

    private User userDetail;

    private Admin adminDetail;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String url = request.getRequestURI();

        if(url.startsWith("/ecommerce")){
            this.userDetail = userRepository.findUserByEmail(email);
            if(Objects.isNull(userDetail)) {
                throw new UsernameNotFoundException("user not found");
            }
            return CustomUserDetails.mapUserToUserDetail(userDetail);
        }
        else if(url.startsWith("/admin")){
            this.adminDetail = adminRepository.findAdminByEmail(email);
            if(Objects.isNull(adminDetail)) {
                throw new UsernameNotFoundException("admin not found");
            }
            List<GrantedAuthority> authorities = getListAuthorities(adminDetail.getRoleGroupId());
            return AdminCustomUserDetails.mapAdminToUserDetail(adminDetail,authorities);
        }
        throw new UsernameNotFoundException("Invalid URL");
    }

    private List<GrantedAuthority> getListAuthorities(String roleGroupId){

        return roleGroupService.getRoleGroupById(roleGroupId).getPermissionIds().stream()
                .map(permissionId -> new SimpleGrantedAuthority(permissionService.getPermissionById(permissionId).getName().name()))
                .collect(Collectors.toList());
    }

    public User getUserDetail(){
        // userDetail.setPassword(null);
        return userDetail;
    }

    public Admin getAdminDetail(){
        // userDetail.setPassword(null);
        return adminDetail;
    }
}
