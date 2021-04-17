package com.example.demo.Controller;

import com.example.demo.utils.JsonData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user/api/v1/order")
public class OrderController {

    @PostMapping("save")
    public JsonData saveOrder(){

        return JsonData.buildSuccess("成功下单");
    }
}
