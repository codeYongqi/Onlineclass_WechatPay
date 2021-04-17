package com.example.demo.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonData {
    private int code;
    private Object data;
    private String message;


    public JsonData(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public static JsonData buildSuccess(Object data){
        return new JsonData(1,data);
    }

    public static JsonData buildSuccess(Object data,String message){
        return new JsonData(1,data,message);
    }

    public static JsonData buildError(Object data){
        return new JsonData(-1,data);
    }

    public static JsonData buildError(Object data,String message){
        return new JsonData(-1,data,message);
    }


}
