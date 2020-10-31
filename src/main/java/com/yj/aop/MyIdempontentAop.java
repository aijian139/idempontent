package com.yj.aop;

import com.yj.exception.MyException;
import com.yj.token.TokenService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect  // 使用aop 实现post 请求幂等性
public class MyIdempontentAop {

    @Autowired
    private TokenService tokenService;

    @Pointcut(value = "@annotation(com.yj.annotation.MyIdempontent)")
    public void pointcut(){

    }

    @Before(value = "pointcut()")
    public void before(JoinPoint joinPoint) throws MyException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        try {
            tokenService.checkToken(request);
        } catch (MyException e) {
           throw  e;
        }

    }

}
