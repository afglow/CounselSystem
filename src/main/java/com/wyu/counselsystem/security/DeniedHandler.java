package com.wyu.counselsystem.security;

import com.alibaba.fastjson.JSON;
import com.wyu.counselsystem.util.Result;
import com.wyu.counselsystem.util.ResultCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author afglow
 * @Date Create in 2023-02-2023/2/6-15:10
 * @Description
 */
public class DeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        response.getWriter().write(JSON.toJSONString(Result.build("", ResultCodeEnum.PERMISSION)));
    }
}
