package com.yj.token;

import com.yj.exception.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Component
public class TokenService {

    @Autowired
    private RedisService redisService;

    // 返回UUID
    public String createToken() {

        String token = UUID.randomUUID().toString();

        redisService.set(token, token, 36000l);

        return token;
    }

    // 检查规则 接口中携带 token , token 只能使用一次
    public boolean checkToken(HttpServletRequest request) throws MyException {
        // 获取请求头中携带的token 或者 参数中携带的token
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            token = request.getParameter("token");
            if (StringUtils.isEmpty(token)) {
                throw new MyException("该token不存在！");
            }
        }

        boolean flag = redisService.exists(token);
        if (!flag) {
            throw new MyException("重复操作");
        }
        // 然后删除redis 中的key
        boolean remove = redisService.remove(token);
        if (!remove) {
            throw new MyException("重复操作");
        }


        return true;
    }

}
