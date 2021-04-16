package com.example.demo;

import com.example.demo.model.Entity.User;
import com.example.demo.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class CommonTest {
    @Test
    public String  testGeneJwt(){
        User user = new User();
        user.setId(10);
        user.setName("张三");
        user.setHeadImg("www.baidu.com");
        String token = JwtUtils.geneJsonWebToken(user);
        System.out.println(token);
        return token;
    }

    @Test
    public void checkJwt(){
        Claims claims = JwtUtils.checkToken(testGeneJwt());
        Assertions.assertEquals(claims.getSubject(),"zhuyongqi");
    }

}
