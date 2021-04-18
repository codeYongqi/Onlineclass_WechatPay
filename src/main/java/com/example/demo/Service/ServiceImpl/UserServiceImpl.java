package com.example.demo.Service.ServiceImpl;

import com.example.demo.Config.GithubConfig;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Service.UserService;
import com.example.demo.model.Entity.User;
import com.example.demo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private GithubConfig githubConfig;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User saveGithubUser(String code) {
        String accessTokenUrl = String.format(githubConfig.getGITHUB_ACCESS_TOKEN_URL(),
                githubConfig.getClientId(),
                githubConfig.getClientSecret(),
                code);
        Map<String,Object> baseMap = HttpUtils.doGet(accessTokenUrl);
        if(baseMap == null || baseMap.isEmpty()) return null;
        String authToken = (String) baseMap.get("access_token");

        Map<String,String> headers = new HashMap<>();
        headers.put("Authorization"," token " + authToken);

        Map<String,Object> map = HttpUtils.doGetWithHeaders(githubConfig.getGITHUB_USERINFO_URL(),headers);

        Double githubId = (Double) map.get("id");

        //如果用户已经登陆过
        User user = userMapper.findByGithubId(githubId);
        if(user != null) return user;

        String name = (String) map.get("name");
        String email = (String) map.get("email");
        String head_img = (String) map.get("avatar_url");

        user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setCreateTime(new Date());
        user.setHeadImg(head_img);
        user.setGithubId(githubId);

        userMapper.saveUser(user);

        return user;
    }
}
