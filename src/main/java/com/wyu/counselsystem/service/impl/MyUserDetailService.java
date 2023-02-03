package com.wyu.counselsystem.service.impl;

import com.wyu.counselsystem.pojo.Users;
import com.wyu.counselsystem.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
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

//        System.out.println(username);
//        Users users = usersService.selectUser(username);
//        if (users == null){//认证失败
//            throw new UsernameNotFoundException("找不到这个用户！");
//        }

//        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(users.getRole());//配置类的权限名字是用这个来判断的
        return new User("1203",new BCryptPasswordEncoder().encode("456"),new ArrayList<>());
    }
}
