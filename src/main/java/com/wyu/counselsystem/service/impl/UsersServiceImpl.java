package com.wyu.counselsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyu.counselsystem.mapper.UsersMapper;
import com.wyu.counselsystem.pojo.Users;
import com.wyu.counselsystem.service.UsersService;
import org.springframework.stereotype.Service;

/**
 * @author afglow
 * @Date Create in 2022-12-2022/12/22-16:10
 * @Description
 */

@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {



    @Override
    public Users selectUser(String username) {
        QueryWrapper<Users> qw = new QueryWrapper<>();
        qw.eq("username",username);
        return baseMapper.selectOne(qw);
    }
}
