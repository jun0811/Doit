package com.ssafy.doit.service.jwt;

import com.ssafy.doit.model.user.User;
import com.ssafy.doit.service.UserDetailsServiceImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class JwtUtil {

    public final static long TOKEN_VALIDATION_SECOND = 1000L * 60 * 30;

    final static public String ACCESS_TOKEN_NAME = "accessToken";

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Map<String, Object> getUser(String token) {
        return (Map<String, Object>) extractAllClaims(token).get("User");
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername((String) getUser(token).get("email"));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public String generateToken(User user) {
        return doGenerateToken(user, TOKEN_VALIDATION_SECOND);
    }

    public String doGenerateToken(User user, long expireTime) {

        Claims claims = Jwts.claims();
        claims.put("User", user);

        String jwt = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();

        return jwt;
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(ACCESS_TOKEN_NAME);
    }
}
