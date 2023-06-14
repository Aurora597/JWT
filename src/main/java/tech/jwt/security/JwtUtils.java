package tech.jwt.security;

import java.util.Date;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    private Key key() {
        byte[] keyBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean verifyJwtToken(String authToken){
        try{
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (ExpiredJwtException e) {
        // 处理 JWT 过期的情况...
        } catch (UnsupportedJwtException e) {
            // 处理不支持的 JWT 类型...
        } catch (MalformedJwtException e) {
            // 处理 JWT 格式错误...
        } catch (SignatureException e) {
            // 处理 JWT 签名验证失败...
        } catch (IllegalArgumentException e) {
            // 处理参数错误...
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }
}
