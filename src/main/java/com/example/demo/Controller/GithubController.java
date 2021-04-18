package com.example.demo.Controller;

import com.example.demo.Config.GithubConfig;
import com.example.demo.Service.UserService;
import com.example.demo.model.Entity.User;
import com.example.demo.utils.JsonData;
import com.example.demo.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Controller
@RequestMapping("/api/v1/github")
public class GithubController {

    @Autowired
    private GithubConfig githubConfig;

    @Autowired
    private UserService userService;

    @GetMapping("login")
    @ResponseBody
    public Object githubLogin(@RequestParam("state")String state){
        String url = githubConfig.getRedirectUrl();
        String githubLoginUrl = String.format(githubConfig.getGITHUB_LOGIN(),githubConfig.getClientId(),url,state);
        return JsonData.buildSuccess(githubLoginUrl);
    }

    @GetMapping("/user/callback")
    public void githubCallback(@RequestParam("code") String code,
                               String state, HttpServletResponse response) throws IOException {
        User user = userService.saveGithubUser(code,state);
        if(user != null){
            String token = JwtUtils.geneJsonWebToken(user);
            String RedirectUrl = new StringBuilder("/api/v1/github/user/callback/home")
                    .append("?token="+token)
                    .append("&head_img="+ user.getHeadImg())
                    .append("&name="+ URLEncoder.encode(user.getName(),"utf-8"))
                    .toString();

            response.sendRedirect(RedirectUrl);
            //response.sendRedirect("http://www.baidu.com");
        }
    }

    @GetMapping("/user/callback/home")
    @ResponseBody
    public Object homepage(){
       return JsonData.buildSuccess("登录成功，正在跳转");
    }
}
