package com.example.demo.Controller;

import com.example.demo.Dto.VideoOrderDto;
import com.example.demo.Service.VideoOrderService;
import com.example.demo.utils.IpUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Resource;
import sun.security.util.Resources;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user/api/v1/order")
public class OrderController {

    @Autowired
    private VideoOrderService videoOrderService;

    @GetMapping("add")
    public void saveOrder(@RequestParam(value = "video_id") int videoId,
                              HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {
        String ip = IpUtils.getIpAddr(servletRequest);
       // String ip = "120.25.1.43";
       // int userId = 2;
        int userId = (int) servletRequest.getAttribute("user_id");
        VideoOrderDto videoOrderDto = new VideoOrderDto();
        videoOrderDto.setUserId(userId);
        videoOrderDto.setVideoId(videoId);
        videoOrderDto.setIp(ip);

        String QRCodeUrl = videoOrderService.save(videoOrderDto);
        if(QRCodeUrl == null) throw new NullPointerException();

        //生成二维码
        Map<EncodeHintType,Object> hints = new HashMap<>();
        //设置纠错等级
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hints.put(EncodeHintType.CHARACTER_SET,"utf-8");

        BitMatrix bitMatrix = new MultiFormatWriter().encode(QRCodeUrl, BarcodeFormat.QR_CODE
                ,400,400,hints);

        OutputStream outputStream = servletResponse.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix,"png",outputStream);

    }
}
