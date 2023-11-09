package com.example.demo;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class JWTProveedorToken {

   
    private String jwtSecret;
    private long jwtExpirationDate;


    public JWTProveedorToken(){
        jwtSecret = "daf66e01593f61a15b857cf433aae03a005812b31234e149036bcc8dee755dbb";
        jwtExpirationDate = 604800000;
    }

    // generate JWT token
    public String generateToken(String username, String password){
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);


        Map<String, Object>authoritiesClaim = new HashMap<>();
        authoritiesClaim.put("authorities", new SimpleGrantedAuthority("Prueba"));
        
        Map<String, Object> additionalClaims = new HashMap<>();
        additionalClaims.put("username", username);
        additionalClaims.put("password", password);

        return Jwts.builder()
                                .addClaims(authoritiesClaim)
                                .addClaims(additionalClaims)
                                .setSubject(username)
                                .setIssuedAt(new Date())
                                .setExpiration(expireDate)
                                .signWith(key())
                                .compact();
    }

    private Key key(){
        return Keys.hmacShaKeyFor( Decoders.BASE64.decode(jwtSecret));
    }

    // get username from Jwt token
    public String getUsername(String token){
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }
    public Claims getClaims(String token){
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token);
        return claimsJws.getBody();
    }

 
    

}
