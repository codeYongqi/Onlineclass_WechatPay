package com.example.demo.Controller;

import com.example.demo.Config.GithubConfig;
import com.example.demo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/api/v1/github")
public class GithubController {

    @Autowired
    private GithubConfig githubConfig;

    @GetMapping("login")
    @ResponseBody
    public Object githubLogin(@RequestParam("state")String state){
        String url = githubConfig.getRedirectUrl();
        String githubLoginUrl = String.format(githubConfig.getGITHUB_LOGIN(),githubConfig.getClientId(),url,state);
        return JsonData.buildSuccess(githubLoginUrl);
    }

    @GetMapping("/user/callback")
    public void githubCallback(@RequestParam("code") String code,
                               String state, HttpServletResponse response){


    }
}
