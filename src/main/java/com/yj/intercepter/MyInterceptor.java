package com.yj.intercepter;

import com.yj.annotation.MyIdempontent;
import com.yj.exception.MyException;
import com.yj.token.TokenService;
import jdk.nashorn.internal.ir.ReturnNode;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

//@Component // 使用拦截器 拦截自定义注解 实现 post 请求幂等性
public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断是不是hello 请求
        if(!(handler instanceof HandlerMethod)){
            // 不行就放行
            return true;
        }
        // 如果是的话 就获取该请求的方法上的注解
        Method method = ((HandlerMethod) handler).getMethod();
        MyIdempontent idempontent = method.getAnnotation(MyIdempontent.class);
        // 如果有我们自定义的注解 我们就去检查token 是否访问过
        if(idempontent!=null){
            try {
               return tokenService.checkToken(request);
            } catch (MyException e) {
               throw e;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
