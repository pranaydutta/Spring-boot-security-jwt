package com.sc.auth.util;

import com.sc.auth.model.AuthToken;
import com.sc.auth.model.JwtUserDetails;
import com.sc.auth.model.User;
import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    @Value("${jwt.private.key}")
    private String signeKey;

    public AuthToken generateToken(User user)
    {
        Claims claims= Jwts.claims().setSubject(user.getUserId());
        claims.setIssuedAt(new Date(System.currentTimeMillis()));
        claims.setExpiration(new Date(System.currentTimeMillis() * (300 * 1000)));
        claims.put("userName",user.getUserName());
        claims.put("role","customer");
        return AuthToken.builder().token(
                Jwts.builder()
                        .setClaims(claims)
                       .signWith( SignatureAlgorithm.HS512,signeKey)
                        .compact()).build();
    }

    public JwtUserDetails validateToken(String authToken)
    {
        JwtUserDetails jwtUserDetails=null;
        try {
            Claims claims = Jwts.parser().setSigningKey(signeKey).parseClaimsJws(authToken).getBody();
            Date expiryTime = claims.getExpiration();
            if (expiryTime.before(new Date((System.currentTimeMillis())))) {
                throw new RuntimeException("token is expired");
            }
            return JwtUserDetails.builder()
                    .username(claims.get("userName",String.class))
                    .roles(claims.get("role",String.class))
                    .userId(claims.getSubject())
                    .build();
        }catch (Exception e)
        {
            throw new RuntimeException("token is invalid");
        }

    }
}
