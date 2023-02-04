package com.wyu.counselsystem.security;

import com.alibaba.fastjson.JSON;
import com.wyu.counselsystem.util.Result;
import com.wyu.counselsystem.util.ResultCodeEnum;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @author afglow
 * @Date Create in 2023-02-2023/2/4-0:53
 * @Description
 */
public class SuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        StringBuilder sb = new StringBuilder();
        for (GrantedAuthority g:authorities){
            sb.append(g.toString());
        }
        if (sb.toString().equals("ROLE_user")){
            response.getWriter().write(JSON.toJSONString(Result.ok("/user/index")));
        }else if (sb.toString().equals("ROLE_admin")){
            response.getWriter().write(JSON.toJSONString(Result.ok("/admin/index")));
        }
    }
}
