package com.example.demo_register_user_api.security.jwt;

import com.example.demo_register_user_api.security.principal.CustomUserDetails;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

@Configuration
public class JWTProvider {
    @Value("${expire_time}")
    private Long expireTime;
    @Value("${secret_string}")
    private String secretString;

    public String generateJWTToken(CustomUserDetails userDetails) {
        Date today = new Date();
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(today)
                .setExpiration(new Date(today.getTime() + expireTime))
                .signWith(SignatureAlgorithm.HS512, secretString)
                .compact();

    }

    public boolean validateJWTToken(String token){
//        try{
//            Jwts.parser().setSigningKey(secretString).parseClaimsJwt(token);
//            return true;
//        }catch (UnsupportedJwtException ex){
//            System.out.println("Server khong ho tro JWT");
//        }catch(MalformedJwtException ex){
//            System.out.println("Chuoi jwt khong dung");
//        }catch (ExpiredJwtException ex){
//            System.out.println("Jwt da het han");
//        }catch (Exception ex){
//            System.out.println("Co loi xay ra: "+ex.getMessage());
//        }
//        return false;
        try{
            Jwts.parser().setSigningKey(secretString).parseClaimsJws(token);
            return true;
        }catch (UnsupportedJwtException ex){
            System.out.println("Server API not supported JWT: ");
            ex.printStackTrace();
        }catch (MalformedJwtException ex){
            System.out.println("Chuoi JWT khong dung");
            ex.printStackTrace();
        }catch (ExpiredJwtException ex){
            System.out.println("Thoi gian dang nhap het han");
            ex.printStackTrace();
        }catch (Exception ex){
            System.out.println("Loi: "+ex.getMessage());
        }
        return false;
    }

    public String getUsernameFromToken(String token){
        return Jwts.parser().setSigningKey(secretString).parseClaimsJws(token).getBody().getSubject();
    }
}
