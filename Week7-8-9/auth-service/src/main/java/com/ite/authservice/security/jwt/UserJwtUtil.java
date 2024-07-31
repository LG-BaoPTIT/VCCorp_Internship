package com.ite.authservice.security.jwt;

import com.ite.authservice.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserJwtUtil extends JwtUtil{


    private String JWT_SECRET;

    @Value("${jwt.ecommerce.expiration}")
    private long JWT_EXPIRATION;

    private long EXPIRATION_REFRESH_TOKEN;
    @Override
    protected String getJwtSecret() {
        return this.JWT_SECRET;
    }

    @Override
    protected long getJwtExpiration() {
        return this.JWT_EXPIRATION;
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("Name", user.getName());
        claims.put("Email",user.getEmail());
//        claims.put("id", user.getUserId());
        return createToken(claims, user.getEmail());
    }

    public String getJWT_SECRET() {
        return JWT_SECRET;
    }

    public void setJWT_SECRET(String JWT_SECRET) {
        this.JWT_SECRET = JWT_SECRET;
    }
}
