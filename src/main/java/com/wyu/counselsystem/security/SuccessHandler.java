package com.wyu.counselsystem.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wyu.counselsystem.util.JwtHelper;
import com.wyu.counselsystem.util.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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

        String rememberMe = request.getHeader("rememberMe");

        for (GrantedAuthority g : authorities) {
            sb.append(g.toString());
        }

        if (sb.toString().equals("ROLE_user")) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("url", "/user/index");
            //当前端点击了记住密码才会生成token
            if (rememberMe != null && rememberMe.equals("true")) {
                String token = JwtHelper.createToken(authentication.getName(), authentication.getAuthorities());
                jsonObject.put("token", token);
            }
            response.getWriter().write(JSON.toJSONString(Result.ok(jsonObject)));
        } else if (sb.toString().equals("ROLE_admin")) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("url", "/admin/index");
            if (rememberMe != null && rememberMe.equals("true")) {
                String token = JwtHelper.createToken(authentication.getName(), authentication.getAuthorities());
                jsonObject.put("token", token);
            }
            response.getWriter().write(JSON.toJSONString(Result.ok(jsonObject)));
        }
    }

}
