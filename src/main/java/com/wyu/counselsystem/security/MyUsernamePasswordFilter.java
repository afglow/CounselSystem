package com.wyu.counselsystem.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyu.counselsystem.pojo.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
        //attempt Authentication when Content-Type is json
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {

            //use jackson to deserialize json
            UsernamePasswordAuthenticationToken authRequest = null;
            String verifyCode = null;
            try (InputStream is = request.getInputStream()) {
                ObjectMapper mapper = new ObjectMapper();
                LoginForm userDetail = null;
                userDetail = mapper.readValue(is, LoginForm.class);
                verifyCode = (String)request.getSession().getAttribute("VerifyCode");
                //验证验证码
                if (userDetail.getCode().equals("") || verifyCode == null ||
                        !userDetail.getCode().toUpperCase(Locale.ROOT).equals(verifyCode.toUpperCase(Locale.ROOT))) {
                    throw new AuthenticationServiceException("验证码错误");
                }
                System.out.println(userDetail);
                authRequest = new UsernamePasswordAuthenticationToken(
                        userDetail.getUsername(), userDetail.getPassword());

            } catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
            }



            setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);


        }

//        transmit it to UsernamePasswordAuthenticationFilter
        else {
            return super.attemptAuthentication(request, response);
        }

    }
}
