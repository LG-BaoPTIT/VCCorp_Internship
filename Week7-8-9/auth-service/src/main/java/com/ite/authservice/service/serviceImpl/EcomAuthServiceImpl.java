package com.ite.authservice.service.serviceImpl;

import com.ite.authservice.config.SystemLogger;
import com.ite.authservice.constants.LogStepConstant;
import com.ite.authservice.constants.MessageConstants;
import com.ite.authservice.entities.EStatus;
import com.ite.authservice.entities.User;
import com.ite.authservice.event.eventProducer.NotificationEventPublisher;
import com.ite.authservice.event.messages.LockAccountNotification;
import com.ite.authservice.payload.Request.LoginRequest;
import com.ite.authservice.payload.Response.AuthResponse;
import com.ite.authservice.payload.Response.CheckTokenResponse;
import com.ite.authservice.payload.Response.UserInfo;
import com.ite.authservice.repositories.*;
import com.ite.authservice.security.CustomUserDetailsService;
import com.ite.authservice.security.encrypt.AES;
import com.ite.authservice.security.jwt.UserJwtUtil;
import com.ite.authservice.service.EcomAuthService;
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

@Slf4j
@Service
public class EcomAuthServiceImpl implements EcomAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserJwtUtil userJwtUtil;

    @Autowired
    private RedisService redisService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationEventPublisher notificationEventPublisher;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AES aes;

    @Autowired
    private SystemLogger logger;
    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest,HttpServletRequest request) {
        String requestId = request.getHeader("requestId");
        log.info("Inside login {}",loginRequest);
        try {
            logger.log(Thread.currentThread().getName(), "Authenticate user account", LogStepConstant.BEGIN_PROCESS,loginRequest.getEmail());
            if(!userRepository.existsByEmail(loginRequest.getEmail())){
                logger.log(Thread.currentThread().getName(), "Authenticate user account", LogStepConstant.END_PROCESS, MessageConstants.ERROR_01 + loginRequest.getEmail());
                return ResponseUtil.getResponseEntity("03",MessageConstants.ERROR_03, HttpStatus.BAD_REQUEST);
            }
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), aes.ecomDecrypt(loginRequest.getPassword())));
            User user = userRepository.findUserByEmail(loginRequest.getEmail());
            userJwtUtil.setJWT_SECRET(user.getSecret());
            String jwt = userJwtUtil.generateToken(customUserDetailsService.getUserDetail());
            AuthResponse response = AuthResponse.builder()
                    .user_info(UserInfo.builder().user_id(user.getUserId())
                            .full_name(user.getName())
                            .email(user.getEmail())
                            .phone(user.getPhone())
                            .build())
                    .message("Login successful")
                    .accessToken(jwt)
                    .refreshToken("null")
                    .mfaEnabled(false)
                    .expired(1800)
                    .build();
            user.setFailedLogin(0);
            redisService.set(user.getUserId(),jwt);
            redisService.setTimeToLive(user.getUserId(), 30);
            userRepository.save(user);
            logger.log(Thread.currentThread().getName(), "Authenticate user account", LogStepConstant.END_PROCESS,"User account authentication successful: "+ loginRequest.getEmail());

            return new ResponseEntity<>(response,HttpStatus.OK);
        }catch (AuthenticationException exception) {
            logger.log(Thread.currentThread().getName(), "Authenticate user account", LogStepConstant.END_PROCESS,"User account authentication failed: " + loginRequest.getEmail());
            if (exception instanceof BadCredentialsException) {
                if(userRepository.existsByEmail(loginRequest.getEmail())){
                    User user = userRepository.findUserByEmail(loginRequest.getEmail());
                    user.setFailedLogin(user.getFailedLogin() + 1);
                    if(user.getFailedLogin() == 5){
                        LockAccountNotification notification = LockAccountNotification.builder()
                                .email(user.getEmail())
                                .name(user.getName())
                                .build();
                        notificationEventPublisher.publishLockAccountNotificationEvent(notification);
                        user.setStatus(EStatus.LOCK);
                    }
                    userRepository.save(user);
                }

                return ResponseUtil.getResponseEntity("01" , MessageConstants.ERROR_01, HttpStatus.BAD_REQUEST);
            } else if (exception instanceof LockedException) {
                return ResponseUtil.getResponseEntity("02", MessageConstants.ERROR_02, HttpStatus.BAD_REQUEST);
            } else if (exception instanceof DisabledException) {
                return ResponseUtil.getResponseEntity("Account has not been approved.", HttpStatus.BAD_REQUEST);
            }  else {
                return ResponseUtil.getResponseEntity("Failed to authenticate.", HttpStatus.BAD_REQUEST);
            }
        }
    }

    @Override
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization");
            token = token.substring(7);
            Claims claims = userJwtUtil.extractAllClaims(token);
            String email = (String) claims.get("Email");
            User user = userRepository.findUserByEmail(email);
            logger.log(Thread.currentThread().getName(), "user logout",LogStepConstant.BEGIN_PROCESS,user.toString());
            redisService.delete(user.getUserId());
            logger.log(Thread.currentThread().getName(), "user logout",LogStepConstant.END_PROCESS,user.toString());
            return ResponseUtil.getResponseEntity(MessageConstants.LOGOUT_SUCCESSFULLY,HttpStatus.OK);
        } catch (Exception e) {
            logger.logError(Thread.currentThread().getName(), "user logout",LogStepConstant.END_PROCESS,e.getMessage());
            return ResponseUtil.getResponseEntity(MessageConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @Override
    public ResponseEntity<?> checkAccessToken(String token) {
        logger.log(Thread.currentThread().getName(), "Check access token of user",LogStepConstant.BEGIN_PROCESS,token);
        Claims claims = userJwtUtil.extractAllClaims(token);
        String email =(String) claims.get("Email");
        User user = userRepository.findUserByEmail(email);
        if (userJwtUtil.validateToken(token)&& email.equals(user.getEmail()) && redisService.keyExists(user.getUserId()) && redisService.getValue(user.getUserId()).equals(token)) {
            logger.log(Thread.currentThread().getName(), "Check access token of user",LogStepConstant.END_PROCESS,token);
            return ResponseEntity.ok( new CheckTokenResponse(email,MessageConstants.VALID_ACCESS_TOKEN));
        } else {
            logger.log(Thread.currentThread().getName(), "Check access token of user",LogStepConstant.END_PROCESS,MessageConstants.INVALID_ACCESS_TOKEN);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( new CheckTokenResponse(null,MessageConstants.INVALID_ACCESS_TOKEN));
        }
    }

    @Override
    public ResponseEntity<?> checkSignatureRequest(HttpServletRequestWrapper requestWrapper) {
        return ResponseEntity.ok("Valid signature request");
    }

    @Override
    public ResponseEntity<?> checkSignatureAndAccessToken(HttpServletRequestWrapper requestWrapper){
        logger.log(Thread.currentThread().getName(), "Check access token of user",LogStepConstant.BEGIN_PROCESS,requestWrapper.getHeader("email"));

        String authorizationHeader = requestWrapper.getHeader("Authorization");
        String token = authorizationHeader.substring(7);
        Claims claims = userJwtUtil.extractAllClaims(token);
        String email =(String) claims.get("Email");
        if(!email.equals(requestWrapper.getHeader("email"))){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( new CheckTokenResponse(null,MessageConstants.INVALID_ACCESS_TOKEN));
        }
        User user = userRepository.findUserByEmail(email);

        if (userJwtUtil.validateToken(token) && redisService.keyExists(user.getUserId()) && redisService.getValue(user.getUserId()).equals(token)) {
            logger.log(Thread.currentThread().getName(), "Check access token of user",LogStepConstant.END_PROCESS,token);
            return ResponseEntity.ok( new CheckTokenResponse(email,MessageConstants.VALID_ACCESS_TOKEN));
        } else {
            logger.log(Thread.currentThread().getName(), "Check access token of user",LogStepConstant.END_PROCESS,MessageConstants.INVALID_ACCESS_TOKEN);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( new CheckTokenResponse(null,MessageConstants.INVALID_ACCESS_TOKEN));
        }
    }

}
