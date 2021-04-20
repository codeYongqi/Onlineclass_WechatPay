package com.example.demo.Service.ServiceImpl;

import com.example.demo.Config.WechatConifg;
import com.example.demo.Dto.VideoOrderDto;
import com.example.demo.Mapper.UserMapper;
import com.example.demo.Mapper.VideoMapper;
import com.example.demo.Mapper.VideoOrderMapper;
import com.example.demo.Service.VideoOrderService;
import com.example.demo.model.Entity.User;
import com.example.demo.model.Entity.Video;
import com.example.demo.model.Entity.VideoOrder;
import com.example.demo.utils.CommonUtils;
import com.example.demo.utils.HttpUtils;
import com.example.demo.utils.WechatPayUtils;
import org.apache.ibatis.annotations.Insert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class VideoOrderServiceImpl implements VideoOrderService {

    private Logger logger= LoggerFactory.getLogger(this.getClass());
    private Logger dataLogger = LoggerFactory.getLogger("dataLogger");

    @Autowired
    private VideoOrderMapper videoOrderMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WechatConifg wechatConifg;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public  String save(VideoOrderDto videoOrderDto) {

        dataLogger.info("module = video_order ` api = save ` user_id = {}",videoOrderDto.getUserId());

        Video video = videoMapper.findById(videoOrderDto.getVideoId());

        User user = userMapper.findById(videoOrderDto.getUserId());

        //生成订单
        VideoOrder videoOrder = new VideoOrder();
        videoOrder.setTotalFee(video.getPrice());
        videoOrder.setVideoTitle(video.getTitle());
        videoOrder.setUserId(user.getId());
        videoOrder.setVideoId(video.getId());
        videoOrder.setCreateTime(new Date());
        videoOrder.setHeadImg(user.getHeadImg());
        videoOrder.setState(0);
        videoOrder.setNickname(user.getName());

        videoOrder.setDel(0);
        videoOrder.setIp(videoOrderDto.getIp());
        videoOrder.setOutTradeNo(CommonUtils.geneUUID());

        videoOrderMapper.insert(videoOrder);

        String QRCodeUrl = unifiedOrder(videoOrder);

        return QRCodeUrl;
    }

    @Override
    public VideoOrder findByOutTradeNo(String outTradeNo) {
        return videoOrderMapper.findByOutTradeNo(outTradeNo);
    }

    @Override
    public int updateOrderByOutTradeNo(VideoOrder videoOrder) {
        return videoOrderMapper.updateVideoOrderByOutTradeNo(videoOrder);
    }

    private String unifiedOrder(VideoOrder videoOrder) {
        int i = 1/0;
        //生成签名
        SortedMap<String,String> parms = new TreeMap<>();

        parms.put("appid",wechatConifg.getAppId());
        parms.put("mch_id",wechatConifg.getMchId());
        parms.put("nonce_str",CommonUtils.geneUUID());
        parms.put("body",videoOrder.getVideoTitle());
        parms.put("out_trade_no",videoOrder.getOutTradeNo());
        parms.put("total_fee",videoOrder.getTotalFee().toString());
        parms.put("spbill_create_ip",videoOrder.getIp());
        parms.put("notify_url",wechatConifg.getCallbackUrl());
        parms.put("trade_type","NATIVE");

        String key = wechatConifg.getKey();
        String sign = WechatPayUtils.createSign(parms,key);

        parms.put("sign",sign);

        String payXml = null;
        try {
            payXml = WechatPayUtils.mapToXml(parms);
        } catch (Exception e) {
            e.printStackTrace();
        }

       //下单
        String orderStr = HttpUtils.doPost(wechatConifg.getUNIFIED_ORDER_URL(),payXml);
        if(orderStr == null) return null;

        Map<String,String> map = new HashMap<>();

        try {
             map = WechatPayUtils.xmlToMap(orderStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return orderStr != null ? orderStr : null;

        String QRCodeUrl = map.get("code_url");
        return QRCodeUrl;
    }

}
