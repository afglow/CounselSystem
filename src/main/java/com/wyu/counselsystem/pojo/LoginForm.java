package com.wyu.counselsystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author afglow
 * @Date Create in 2023-01-2023/1/28-19:15
 * @Description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {
    private String username;
    private String password;
    private String code;
}
