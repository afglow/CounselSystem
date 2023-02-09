package com.wyu.counselsystem.service.impl;

import com.wyu.counselsystem.pojo.Users;
import com.wyu.counselsystem.security.TokenTimeOutException;
import com.wyu.counselsystem.service.UsersService;
import com.wyu.counselsystem.util.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContext;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author afglow
 * @Date Create in 2022-12-2022/12/22-16:11
 * @Description
 */
//UserDetailService
@Service("userDetailService")
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UsersService usersService;

    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {

//        if (SecurityContextHolder.getContext())
        //返回token的用户
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        if (request.getHeader("token") != null) {
            String token = request.getHeader("token");
            String userName = null;
            try {
                userName = JwtHelper.getUserName(token);
            } catch (Exception e) {
                e.printStackTrace();
                throw new TokenTimeOutException("token timeout");
            }
            Collection<? extends GrantedAuthority> authorities = JwtHelper.getUserGrant(token);
            return new User(userName,new BCryptPasswordEncoder().encode("tokenPassword"),authorities);
        }

        Users users = usersService.selectUser(username);
        if (users == null){//认证失败
            throw new UsernameNotFoundException("找不到此用户！");
        }

        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(users.getRole());//配置类的权限名字是用这个来判断的
        return new User(users.getUsername(),new BCryptPasswordEncoder().encode(users.getPassword()),auths);
    }


}
