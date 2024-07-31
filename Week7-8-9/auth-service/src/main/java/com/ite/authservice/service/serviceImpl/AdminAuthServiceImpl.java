package com.ite.authservice.service.serviceImpl;

import com.ite.authservice.config.SystemLogger;
import com.ite.authservice.constants.LogStepConstant;
import com.ite.authservice.constants.MessageConstants;
import com.ite.authservice.entities.Admin;
import com.ite.authservice.entities.EStatus;
import com.ite.authservice.security.encrypt.AES;
import com.ite.authservice.event.eventProducer.NotificationEventPublisher;
import com.ite.authservice.event.messages.LockAccountNotification;
import com.ite.authservice.payload.Request.LoginRequest;
import com.ite.authservice.payload.dto.MenuInfoDTO;
import com.ite.authservice.payload.Response.UserInfo;
import com.ite.authservice.repositories.*;
import com.ite.authservice.security.CustomUserDetailsService;
import com.ite.authservice.security.jwt.AdminJwtUtil;
import com.ite.authservice.security.jwt.UserJwtUtil;
import com.ite.authservice.service.AdminAuthService;
import com.ite.authservice.payload.Request.VerificationRequest;
import com.ite.authservice.payload.Response.AuthResponse;
import com.ite.authservice.security.tfa.TfaService;
import com.ite.authservice.service.MenuInfoService;
import com.ite.authservice.service.RedisService;
import com.ite.authservice.utils.ResponseUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminAuthServiceImpl implements AdminAuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AdminJwtUtil adminJwtUtil;

    @Autowired
    private UserJwtUtil userJwtUtil;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AES aes;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleGroupRepository roleGroupRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private NotificationEventPublisher notificationEventPublisher;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MenuInfoService menuInfoService;

    @Autowired
    private TfaService tfaService;

    @Autowired
    private SystemLogger logger;

    @Override
    public ResponseEntity<?> authenticate(LoginRequest loginRequest) {
        log.info("Inside login {}",loginRequest);

        try {
            logger.log(Thread.currentThread().getName(), "Authenticate admin account", LogStepConstant.BEGIN_PROCESS,loginRequest.getEmail());
            if(!adminRepository.existsByEmail(loginRequest.getEmail())){
                logger.log(Thread.currentThread().getName(), "Authenticate admin account", LogStepConstant.END_PROCESS,MessageConstants.ERROR_01 + loginRequest.getEmail());
                return ResponseUtil.getResponseEntity("03",MessageConstants.ERROR_03, HttpStatus.BAD_REQUEST);
            }
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), aes.adminDecrypt(loginRequest.getPassword())));
            Admin admin = adminRepository.findAdminByEmail(loginRequest.getEmail());

            admin.setVerified(true);
            admin.setFailedLogin(0);
            logger.log(Thread.currentThread().getName(), "Authenticate admin account", LogStepConstant.BEGIN_CALL_DATABASE,loginRequest.getEmail());
            adminRepository.save(admin);
            logger.log(Thread.currentThread().getName(), "Authenticate admin account", LogStepConstant.END_CALL_DATABASE,loginRequest.getEmail());
            logger.log(Thread.currentThread().getName(), "Authenticate admin account", LogStepConstant.END_PROCESS,"Admin account authentication successful: "+ loginRequest.getEmail());

            return ResponseUtil.getResponseEntity("Account authentication successful.",HttpStatus.OK);
        }catch (AuthenticationException exception) {
            exception.printStackTrace();
            logger.log(Thread.currentThread().getName(), "Authenticate admin account", LogStepConstant.END_PROCESS,MessageConstants.ERROR_01 + loginRequest.getEmail());
            if (exception instanceof BadCredentialsException) {
                if(adminRepository.existsByEmail(loginRequest.getEmail())){
                    Admin admin = adminRepository.findAdminByEmail(loginRequest.getEmail());
                    admin.setFailedLogin(admin.getFailedLogin() + 1);
                    if(admin.getFailedLogin() == 5){
                        LockAccountNotification notification = LockAccountNotification.builder()
                                .email(admin.getEmail())
                                .name(admin.getName())
                                .build();
                        notificationEventPublisher.publishLockAccountNotificationEvent(notification);
                        admin.setStatus(EStatus.LOCK);
                    }
                    adminRepository.save(admin);
                }

                return ResponseUtil.getResponseEntity("01",MessageConstants.ERROR_01, HttpStatus.BAD_REQUEST);
            } else if (exception instanceof LockedException) {
                return ResponseUtil.getResponseEntity("02",MessageConstants.ERROR_02, HttpStatus.BAD_REQUEST);
            } else if (exception instanceof DisabledException) {
                return ResponseUtil.getResponseEntity("Account has not been approved.", HttpStatus.BAD_REQUEST);
            }  else {
                exception.printStackTrace();
                return ResponseUtil.getResponseEntity("Failed to authenticate.", HttpStatus.BAD_REQUEST);
            }
        }
    }


    @Override
    public ResponseEntity<?> verifyCode(VerificationRequest request) {
        try{
            logger.log(Thread.currentThread().getName(), "Verify 2fa code", LogStepConstant.BEGIN_PROCESS,request.toString());

            Admin admin = adminRepository.findAdminByEmail(request.getEmail());
            if(!admin.isVerified() && admin.getStatus().equals(EStatus.ACTIVE)){
                return ResponseUtil.getResponseEntity(MessageConstants.SOMETHING_WENT_WRONG, HttpStatus.BAD_REQUEST);
            }
            if ( !tfaService.isOtpNotValid(admin.getSecret(), request.getCode()) ) {
                adminJwtUtil.setJWT_SECRET(admin.getSecret());
                String jwt = adminJwtUtil.generateToken(admin);
                List<String> listPermission = roleGroupRepository.findRoleGroupByRoleGroupId(admin.getRoleGroupId()).getPermissionIds().stream()
                        .map(permissionId -> permissionRepository.findPermissionByPermissionId(permissionId).getName().name()).collect(Collectors.toList());
//
//                MenuInfoDTO info1 = MenuInfoDTO.builder().menu_id(1)
//                        .url("admin/dashboard")
//                        .menu_icon("admin/trang-chu")
//                        .mapped_url("/admin/dashboard")
//                        .menu_level(1)
//                        .menu_title("Dashboard").build();
//
//                MenuInfoDTO info2 = MenuInfoDTO.builder().menu_id(2)
//                        .url("admin/dashboard")
//                        .menu_icon("admin/trang-chu")
//                        .mapped_url("/admin/user-manage")
//                        .menu_level(1)
//                        .menu_title("user").build();
//
////                MenuInfo info3 = MenuInfo.builder().menu_id(3)
////                        .url("admin/manage-order")
////                        .menu_icon("admin/manage-order")
////                        .mapped_url("/admin/manage-order")
////                        .menu_level(1)
////                        .menu_title("order").build();
//
//
//
//                MenuInfoDTO info401 = MenuInfoDTO.builder().menu_id(401)
//                        .url("/admin/giao-dich-thanh-toan")
//                        .menu_icon("payment")
//                        .mapped_url("/admin/giao-dich-thanh-toan")
//                        .menu_level(2)
//                        .menu_title("Thanh toán")
//                        .build();
//                MenuInfoDTO info402 = MenuInfoDTO.builder().menu_id(402)
//                        .url("/admin/giao-dich-hoan-tra")
//                        .menu_icon("keyboard_return")
//                        .mapped_url("/admin/giao-dich-hoan-tra")
//                        .menu_level(2)
//                        .menu_title("Hoàn trả")
//                        .build();
//                List<MenuInfoDTO> menuInfos4ChildDTOS = new ArrayList<>();
//                menuInfos4ChildDTOS.add(info401);
//                menuInfos4ChildDTOS.add(info402);
//                MenuInfoDTO info4 = MenuInfoDTO.builder().menu_id(4)
//                        .url("")
//                        .menu_icon("attach_money")
//                        .mapped_url("")
//                        .menu_level(1)
//                        .children(menuInfos4ChildDTOS)
//                        .menu_title("Quản lý giao dịch").build();
//                List<MenuInfoDTO> menuInfoDTOS = new ArrayList<>();
//                menuInfoDTOS.add(info1);
//                menuInfoDTOS.add(info2);
//                //menuInfos.add(info3);
//                menuInfoDTOS.add(info4);
                List<MenuInfoDTO> menuInfoDTOS = menuInfoService.getListMenuInfoByRoleGroup(admin.getRoleGroupId());
                AuthResponse response = AuthResponse.builder()
                                                    .user_info(UserInfo.builder().user_id(admin.getAdminId())
                                                                .full_name(admin.getName())
                                                                .email(admin.getEmail())
                                                                .phone(admin.getPhone())
                                                                .build())
                                                    .message("Verify 2fa code successful")
                                                    .accessToken( jwt)
                                                    .expired(1800)
                                                    .refreshToken("null")
                                                    .menu_info(menuInfoDTOS)
                                                    .mfaEnabled(true)
                                                    .build();

                admin.setVerified(false);
                redisService.set(admin.getAdminId(),jwt);
                redisService.setTimeToLive(admin.getAdminId(), 30);
                adminRepository.save(admin);
                logger.log(Thread.currentThread().getName(), "Verify 2fa code", LogStepConstant.END_PROCESS,request.toString());

                return new ResponseEntity<>(response,HttpStatus.OK);
                //return ResponseUtil.getResponseEntity("Code verify is incorrect", HttpStatus.BAD_REQUEST);

            }else {
                logger.log(Thread.currentThread().getName(), "Verify 2fa code", LogStepConstant.END_PROCESS, MessageConstants.ERROR_06);

                return ResponseUtil.getResponseEntity("06",MessageConstants.ERROR_06, HttpStatus.BAD_REQUEST);

            }

        }catch (Exception e){
            logger.log(Thread.currentThread().getName(), "Verify 2fa code", LogStepConstant.END_PROCESS, String.valueOf(e));
            e.printStackTrace();
            return ResponseUtil.getResponseEntity(MessageConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> checkSignatureAndAccessToken(HttpServletRequestWrapper requestWrapper) {
        String authorizationHeader = requestWrapper.getHeader("Authorization");
        String token = authorizationHeader.substring(7);
        Claims claims = adminJwtUtil.extractAllClaims(token);
        String adminId = (String) claims.get("id");
        if (adminJwtUtil.validateToken(token) && redisService.keyExists(adminId) && redisService.getValue(adminId).equals(token)) {
            return ResponseEntity.ok(MessageConstants.VALID_ACCESS_TOKEN);
        }
        return new ResponseEntity<>("",HttpStatus.UNAUTHORIZED);

    }

    @Override
    public ResponseEntity<?> checkSignatureRequest(HttpServletRequestWrapper requestWrapper) {
        return ResponseEntity.ok("Valid signature request");
    }

    @Override
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            token = token.substring(7);
            Claims claims = adminJwtUtil.extractAllClaims(token);
            String adminId = (String) claims.get("id");
            logger.log(Thread.currentThread().getName(), "admin logout", LogStepConstant.BEGIN_PROCESS,adminId);
            redisService.delete(adminId);
            logger.log(Thread.currentThread().getName(), "admin logout",LogStepConstant.END_PROCESS,adminId);
            return ResponseUtil.getResponseEntity(MessageConstants.LOGOUT_SUCCESSFULLY, HttpStatus.OK);
        } catch (Exception e) {
            logger.logError(Thread.currentThread().getName(), "admin logout",LogStepConstant.END_PROCESS,e.getMessage());
            return ResponseUtil.getResponseEntity(MessageConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
