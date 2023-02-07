package com.wyu.counselsystem.security;

import com.alibaba.fastjson.JSON;
import com.wyu.counselsystem.util.Result;
import com.wyu.counselsystem.util.ResultCodeEnum;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author afglow
 * @Date Create in 2023-02-2023/2/6-15:15
 * @Description
 */
public class EntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(401);
        response.getWriter().write(JSON.toJSONString(Result.build("", ResultCodeEnum.LOGIN_AUTH)));
    }
}
