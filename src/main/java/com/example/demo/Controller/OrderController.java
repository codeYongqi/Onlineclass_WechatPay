package com.example.demo.Controller;

import com.example.demo.utils.JsonData;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user/api/v1/order")
public class OrderController {

    @PostMapping("save")
    public JsonData saveOrder(HttpServletRequest request){
        System.out.println(request.getAttribute("name"));
        return JsonData.buildSuccess("成功下单");
    }
}
