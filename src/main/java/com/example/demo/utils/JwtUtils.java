package com.example.demo.utils;

import com.example.demo.model.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtils {

    public static final String SUBJECT = "zhuyongqi";

    public static final long EXPIRATION = 1000 * 60 * 1;

    public static final String APPSECRET = "wechat pay";

    public static String geneJsonWebToken(User user){
        if(user == null || user.getId() == null || user.getName() == null)
            return null;

        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("id",user.getId())
                .claim("name",user.getName())
                .claim("head_img",user.getHeadImg())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256,APPSECRET).compact();
        return  token;
    }

    public static Claims checkToken(String token){
        try {
            final Claims claims = Jwts.parser().setSigningKey(APPSECRET).parseClaimsJws(token).getBody();
            return claims;
        }catch (Exception e){

        }
        return null;
    }
}
