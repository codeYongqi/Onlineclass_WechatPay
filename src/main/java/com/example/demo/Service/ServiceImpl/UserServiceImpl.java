package com.example.demo.Service.ServiceImpl;

import com.example.demo.Config.GithubConfig;
import com.example.demo.Service.UserService;
import com.example.demo.model.Entity.User;
import com.example.demo.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private GithubConfig githubConfig;

    @Override
    public User saveGithubUser(String code) {
        String accessTokenUrl = String.format(githubConfig.getGITHUB_ACCESS_TOKEN_URL(),
                githubConfig.getClientId(),
                githubConfig.getClientSecret(),
                code);
        Map<String,Object> baseMap = HttpUtils.doGet(accessTokenUrl);
        if(baseMap != null && !baseMap.isEmpty()){
            String authToken = (String) baseMap.get("access_token");

            Map<String,String> headers = new HashMap<>();
            headers.put("Authorization"," token " + authToken);
            Map<String,Object> map = HttpUtils.doGetWithHeaders(githubConfig.getGITHUB_USERINFO_URL(),headers);
            System.out.println(map);
        }
        return null;
    }
}
