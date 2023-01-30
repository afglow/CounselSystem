package com.wyu.counselsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wyu.counselsystem.pojo.Users;
import org.springframework.stereotype.Service;

/**
 * @author afglow
 * @Date Create in 2022-12-2022/12/22-16:10
 * @Description
 */

public interface UsersService extends IService<Users> {

    Users selectUser(String username);
}
