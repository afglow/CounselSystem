package com.wyu.counselsystem.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @author afglow
 * @Date Create in 2023-02-2023/2/2-17:18
 * @Description
 */
//sss
public class MyAuthenticationProvide extends DaoAuthenticationProvider {
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
//
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        String code = request.getParameter("code");
//        System.out.println(request.getSession());
//        String verifyCode = (String)request.getSession().getAttribute("VerifyCode");
//        System.out.println(verifyCode);
//        if (code == null || verifyCode == null || !code.toUpperCase(Locale.ROOT).equals(verifyCode.toUpperCase(Locale.ROOT))) {
//            throw new AuthenticationServiceException("验证码错误");
//        }

        super.additionalAuthenticationChecks(userDetails, authentication);
    }
}
