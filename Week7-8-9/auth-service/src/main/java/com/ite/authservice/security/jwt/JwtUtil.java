package com.ite.authservice.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Slf4j
public abstract class JwtUtil {
    protected abstract String getJwtSecret();

    protected abstract long getJwtExpiration();

    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getJwtSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public String createToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + getJwtExpiration()))
                .signWith(SignatureAlgorithm.HS256, getJwtSecret()).compact();
    }
    // Validate jwt token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getJwtSecret())
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.error("Invalid or expired JWT token: {}", e.getMessage());
        }
        return false;
    }
}

























//@Slf4j
//public class JwtUtil {
//    private String JWT_SECRET;
//
//    private long JWT_EXPIRATION;
//
//    private long EXPIRATION_REFRESH_TOKEN;
//
//
//    public String extractUsername(String token){
//        return extractClamis(token, Claims::getSubject);
//    }
//
//    public Date extractExpiration(String token){
//        return extractClamis(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClamis(String token, Function<Claims,T> claimsResolver){
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    public Claims extractAllClaims(String token){
//        return Jwts.parser()
//                .setSigningKey(JWT_SECRET)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    private Boolean isTokenExpired(String token){
//        return extractExpiration(token).before(new Date());
//    }
//
//    public String generateToken(User user){
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("Name",user.getName());
//        claims.put("id",user.getUserId());
//        claims.put("role", user.getRole());
//        return createToken(claims, user.getEmail());
//    }
//
//    public String generateToken(Admin admin){
//        Map<String, Object> claims = new HashMap<>();
//        claims.put("Name",admin.getName());
//        claims.put("roleGroupId", admin.getRoleGroupId());
//        return createToken(claims, admin.getEmail());
//    }
//
//    private String createToken(Map<String, Object> claims, String subject){
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
//                .signWith(SignatureAlgorithm.HS256,JWT_SECRET).compact();
//    }
//
//    // validate jwt token
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser()
//                    .setSigningKey(JWT_SECRET)
//                    .parseClaimsJws(token);
//            return true;
//        } catch (MalformedJwtException mje) {
//            log.error("Invalid JWT Token");
//        } catch (ExpiredJwtException eje) {
//            log.error("Expired JWT Token");
//        } catch (UnsupportedJwtException uje) {
//            log.error("Unsupported JWT Token");
//        } catch (IllegalArgumentException iae) {
//            log.error("JWT claims String is empty");
//        }
//        return false;
//    }
//
//}
