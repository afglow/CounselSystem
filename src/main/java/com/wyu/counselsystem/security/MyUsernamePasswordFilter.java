package com.wyu.counselsystem.security;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyu.counselsystem.pojo.LoginForm;
import com.wyu.counselsystem.util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

/**
 * @author afglow
 * @Date Create in 2023-02-2023/2/2-17:32
 * @Description
 */
public class MyUsernamePasswordFilter extends UsernamePasswordAuthenticationFilter {


    @Autowired
    @Override
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
        //1.ContentType不为空 2.当前请求，系统无登录用户 3.此处if只处理Json数据
        if (request.getContentType() != null && authentication == null &&
                (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE))) {//常规的Json登录

            UsernamePasswordAuthenticationToken authRequest = null;
            String verifyCode = null;
            try (InputStream is = request.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                LoginForm loginForm = null;
                //JSon取值
                loginForm = mapper.readValue(is, LoginForm.class);

                //验证码操作
                verifyCode = (String) request.getSession().getAttribute("VerifyCode");
                //验证验证码
                if (loginForm.getCode().equals("") || verifyCode == null ||
                        !loginForm.getCode().toUpperCase(Locale.ROOT).equals(verifyCode.toUpperCase(Locale.ROOT))) {
                    throw new CodeCheckException("验证码错误");
                }
                System.out.println(loginForm);


                authRequest = new UsernamePasswordAuthenticationToken(
                        loginForm.getUsername(), loginForm.getPassword());

            } catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
            }


            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);

        } else
            //未登录而且带了token 不需要指定ContentType 有Header即可 当前无登录用户
            if (request.getHeader("token") != null && authentication == null) {

                String token = request.getHeader("token");

                if (!JwtHelper.isExpiration(token)) {//没过期和没被修改返回false 所以要加！
                    String userName = JwtHelper.getUserName(token);
                    return this.getAuthenticationManager().authenticate(
                            new UsernamePasswordAuthenticationToken(userName, "tokenPassword"));
                } else {
                    throw new TokenTimeOutException("Token过期，请重新登录");
                }

            }

//        transmit it to UsernamePasswordAuthenticationFilter
            else {
                return super.attemptAuthentication(request, response);
            }

    }

}
