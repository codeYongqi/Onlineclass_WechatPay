package com.example.demo.Interceptor;

import com.example.demo.utils.JwtUtils;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import org.apache.http.HttpResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginInterceptor implements HandlerInterceptor {

    private static final Gson gson = new Gson();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessToken = request.getHeader("token");
        if(accessToken == null) accessToken = request.getParameter("token");
        if(accessToken != null){
            Claims claims = JwtUtils.checkToken(accessToken);
            if (claims != null) {
                Integer userId = (Integer) claims.get("id");
                String usrName = (String) claims.get("name");

                request.setAttribute("user_id",userId);
                request.setAttribute("name",usrName);

                return true;
            }
        }
        sendErrorMessage(response,"出现异常，请重新登录");
        return false;
    }

    public static void sendErrorMessage(HttpServletResponse response,Object object) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(gson.toJson(object));
        printWriter.close();
        response.flushBuffer();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
