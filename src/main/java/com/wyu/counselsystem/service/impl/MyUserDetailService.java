package com.wyu.counselsystem.service.impl;

import com.wyu.counselsystem.pojo.Users;
import com.wyu.counselsystem.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users users = usersService.selectUser(username);
        if (users == null){//认证失败
            throw new UsernameNotFoundException("用户名错误！");
        }

        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(users.getRole());//配置类的权限名字是用这个来判断的
//        List<GrantedAuthority> auth = new ArrayList<>();
//        auth.add(new SimpleGrantedAuthority("ROLE_user"));
        return new User(users.getUsername(),new BCryptPasswordEncoder().encode(users.getPassword()),auths);
    }
}
