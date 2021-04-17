package com.example.demo.utils;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.HashMap;
import java.util.Map;
public class HttpUtils {

    private static final Gson gson = new Gson();

    /**
     * 封装 Http Get 请求
     * @param url
     * @return
     */
    public static Map<String,Object> doGet(String url){
        Map<String,Object> map = new HashMap<>();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(5000)
                .setConnectTimeout(5000)
                .setSocketTimeout(5000)
                .build();

        HttpGet httpGet = new HttpGet(url);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();){
             HttpResponse response = httpClient.execute(httpGet);
             if(response.getStatusLine().getStatusCode() == 200){
                 String jsonResult = response.getEntity().toString();
                 map = gson.fromJson(jsonResult,HashMap.class);
             }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * Http Post 请求
     * @return
     */
    public static String doPost(String url,Object data){
        //超时设置
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type","text/html;charset = utf-8");
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
        httpPost.setConfig(requestConfig);

        if(data != null && data instanceof String){
            StringEntity stringEntity = new StringEntity(data.toString(),"utf-8");
            httpPost.setEntity(stringEntity);
        }

        try (CloseableHttpClient httpClient = HttpClients.createDefault();){
           HttpResponse response= httpClient.execute(httpPost);
            HttpEntity  httpEntity =response.getEntity();
            if(response.getStatusLine().getStatusCode() == 200){
                String result = httpEntity.toString();
                return  result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
