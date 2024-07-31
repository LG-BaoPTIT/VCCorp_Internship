package com.ite.authservice.security.jwt;

import com.ite.authservice.entities.User;
import com.ite.authservice.repositories.AdminRepository;
import com.ite.authservice.repositories.TokenRepository;
import com.ite.authservice.repositories.UserRepository;
import com.ite.authservice.security.CustomUserDetailsService;
import com.ite.authservice.service.RedisService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private RedisService redisService;

    @Autowired
    private AdminJwtUtil adminJwtUtil;

    @Autowired
    private UserJwtUtil userJwtUtil;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private Claims claims;
    private String userName;
    private String token;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            this.token = authorizationHeader.substring(7);
            String url = request.getRequestURI();

            if (url.startsWith("/ecommerce")) {

                handleEcomRequest(token,request.getHeader("email"));
            } else if (url.startsWith("/admin")) {
                handleAdminRequest(token,request.getHeader("id"));
            }
        }

        filterChain.doFilter(request, response);
    }

    private void handleEcomRequest(String token,String email) {
        String secret = userRepository.findUserByEmail(email).getSecret();
        userJwtUtil.setJWT_SECRET(secret);
        this.userName = userJwtUtil.extractUsername(token);
        this.claims = userJwtUtil.extractAllClaims(token);

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //UserDetails  userDetails = customUserDetailsService.loadUserByUsername(userName);
            User user = userRepository.findUserByEmail(email);
            if (claims != null && claims.containsKey("Email") && redisService.keyExists(user.getUserId()) && redisService.getValue(user.getUserId()).equals(this.token) && userJwtUtil.validateToken(token)) {
                UserDetails  userDetails = customUserDetailsService.loadUserByUsername(userName);
                authenticate(userDetails);
            }

        }
    }

    private void handleAdminRequest(String token,String adminId) {
        String secret = adminRepository.findAdminByAdminId(adminId).getSecret();
        adminJwtUtil.setJWT_SECRET(secret);
        this.userName = adminJwtUtil.extractUsername(token);
        this.claims = adminJwtUtil.extractAllClaims(token);

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
            String id = (String) claims.get("id");
            if (claims != null && claims.containsKey("id") && redisService.keyExists(id) && redisService.getValue(id).equals(this.token) && adminJwtUtil.validateToken(token)) {
                authenticate(userDetails);
            }
        }
    }

    private void authenticate(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest()));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    public String getCurrentUser() {
        return this.userName;
    }
}

















//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if(request.getServletPath().matches("/user/login|/user/signup|/user/forgotPassword")){
//            filterChain.doFilter(request,response);
//        }else{
//
//            String authorizationHeader = request.getHeader("Authorization");
//            String token = null;
//
//            if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
//                token = authorizationHeader.substring(7);
//                userName = jwtUtil.extractUsername(token);
//                claims = jwtUtil.extractAllClaims(token);
//            }
//            if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
//                UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
//                if(jwtUtil.validateToken(token)){
//                    System.out.println("Roles from token: " + claims.get("role"));
//                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
//
//                    usernamePasswordAuthenticationToken.setDetails(
//                            new WebAuthenticationDetailsSource().buildDetails(request)
//                    );
//                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                }
//            }
//            filterChain.doFilter(request,response);
//        }
//
//
//    }
//    public boolean isSystemRole() {
//        String roleName = (String) claims.get("role");
//        return ERole.ROLE_SYSTEM.name().equalsIgnoreCase(roleName);
//    }
//
//    public boolean isManagerRole() {
//        String roleName = (String) claims.get("role");
//        return ERole.ROLE_MANAGER.name().equalsIgnoreCase(roleName);
//    }
//
//    public boolean isStaffRole() {
//        String roleName = (String) claims.get("role");
//        return ERole.ROLE_STAFF.name().equalsIgnoreCase(roleName);
//    }
//
//    public boolean isClientRole() {
//        String roleName = (String) claims.get("role");
//        return ERole.ROLE_CLIENT.name().equalsIgnoreCase(roleName);
//    }
//
//    public boolean isGuestRole() {
//        String roleName = (String) claims.get("role");
//        return ERole.ROLE_GUEST.name().equalsIgnoreCase(roleName);
//    }
//