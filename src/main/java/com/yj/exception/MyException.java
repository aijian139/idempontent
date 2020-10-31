package com.yj.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;


public class MyException extends Exception {

    public MyException(String message) {
        super(message);
    }
}
