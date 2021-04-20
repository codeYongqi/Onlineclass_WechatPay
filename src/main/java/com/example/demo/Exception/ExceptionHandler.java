package com.example.demo.Exception;

import com.example.demo.utils.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData Handler(Exception e){
        if(e instanceof TestException){
            TestException test = (TestException) e;
            return JsonData.buildError(test.getCode(),test.getMsg());
        }else{
            return JsonData.buildError(-1,"发生未知错误");
        }
    }
}
