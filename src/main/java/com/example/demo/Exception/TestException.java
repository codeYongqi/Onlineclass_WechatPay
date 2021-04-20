package com.example.demo.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestException extends RuntimeException {
    private Integer code;
    private String msg;
}
