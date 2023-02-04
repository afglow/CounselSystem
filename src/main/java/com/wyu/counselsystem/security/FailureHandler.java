package com.wyu.counselsystem.security;

import com.alibaba.fastjson.JSON;
import com.wyu.counselsystem.util.Result;
import com.wyu.counselsystem.util.ResultCodeEnum;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author afglow
 * @Date Create in 2023-02-2023/2/4-0:55
 * @Description
 */
public class FailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        System.out.println(exception.getMessage());
        response.setContentType("application/json;charset=UTF-8");
        if(exception instanceof UsernameNotFoundException || exception instanceof BadCredentialsException){
            response.getWriter().write(JSON.toJSONString(Result.build("", ResultCodeEnum.LOGIN_ERROR)));
        }else if (exception instanceof CodeCheckException){
            response.getWriter().write(JSON.toJSONString(Result.build("", ResultCodeEnum.CODE_ERROR)));
        }else {
            response.getWriter().write(JSON.toJSONString(Result.build("", ResultCodeEnum.ARGUMENT_VALID_ERROR)));
        }
    }
}
