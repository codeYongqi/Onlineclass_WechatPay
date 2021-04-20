package com.example.demo.Controller;

import com.example.demo.Config.WechatConifg;
import com.example.demo.Service.UserService;
import com.example.demo.Service.VideoOrderService;
import com.example.demo.model.Entity.VideoOrder;
import com.example.demo.utils.JsonData;
import com.example.demo.utils.WechatPayUtils;
import org.apache.ibatis.cache.decorators.WeakCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

@Controller
@RequestMapping("/api/v1/wechat")
public class WeChatController {

    @Autowired
    private WechatConifg wechatConifg;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoOrderService videoOrderService;

    @GetMapping("login_url")
    @ResponseBody
    public JsonData loginUrl(@RequestParam(value = "access_page")String accessPage) throws UnsupportedEncodingException {
        String url = wechatConifg.getOpenRedirectUrl();
        String callbakc = URLEncoder.encode(url,"GBK");
        String qrCodeUrl = String.format(wechatConifg.getOPEN_QRCODE_URL(),wechatConifg.getOpenAppId(),callbakc,accessPage);
        return JsonData.buildSuccess(qrCodeUrl);
    }

    /**
     * 微信支付回调
     * @param request
     * @param response
     */
    @PostMapping("/order/callback")
    public void orderCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception{
        InputStream inputStream = request.getInputStream();
        BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
        StringBuffer stringBuffer = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine())  != null){
            stringBuffer.append(line);
        }
        bufferedReader.close();
        inputStream.close();
        Map<String,String> callbackMap = WechatPayUtils.xmlToMap(stringBuffer.toString());
        SortedMap<String,String> sortedMap = WechatPayUtils.getSortedMap(callbackMap);

        //判断签名是否正确
        if(WechatPayUtils.isCorrectedSign(sortedMap,wechatConifg.getKey())){
            if("SUCCESS".equals(sortedMap.get("result_code"))){
                String outTradeNo = sortedMap.get("out_trade_no");
                VideoOrder dbVideoOrder = videoOrderService.findByOutTradeNo(outTradeNo);
                if(dbVideoOrder.getState() == 0){
                    dbVideoOrder.setOpenid(sortedMap.get("openid"));
                    dbVideoOrder.setOutTradeNo(outTradeNo);
                    dbVideoOrder.setNotifyTime(new Date());
                    dbVideoOrder.setState(1);
                    int rows = videoOrderService.updateOrderByOutTradeNo(dbVideoOrder);
                    if(rows == 1){
                      response.setContentType("text/xml");
                      response.getWriter().println("SUCCESS");
                    }
                }
            }
        }else {
            response.setContentType("text/xml");
            response.getWriter().println("FAIL");
        }
    }
}
