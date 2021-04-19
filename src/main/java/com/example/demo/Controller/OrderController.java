package com.example.demo.Controller;

import com.example.demo.Dto.VideoOrderDto;
import com.example.demo.Service.VideoOrderService;
import com.example.demo.model.Entity.VideoOrder;
import com.example.demo.utils.IpUtils;
import com.example.demo.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user/api/v1/order")
public class OrderController {

    @Autowired
    private VideoOrderService videoOrderService;

    @GetMapping("add")
    public JsonData saveOrder(@RequestParam(value = "video_id") int videoId,
                              HttpServletRequest servletRequest){
        String ip = IpUtils.getIpAddr(servletRequest);
        int userId = 1;
        VideoOrderDto videoOrderDto = new VideoOrderDto();
        videoOrderDto.setUserId(userId);
        videoOrderDto.setVideoId(videoId);
        videoOrderDto.setIp(ip);

        VideoOrder videoOrder = videoOrderService.save(videoOrderDto);
        return JsonData.buildSuccess(videoOrder);
    }
}
