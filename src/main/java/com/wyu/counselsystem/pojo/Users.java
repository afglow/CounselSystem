package com.wyu.counselsystem.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author afglow
 * @Date Create in 2023-01-2023/1/29-15:37
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("users")
public class Users {
    @TableId(value = "id")
    private Integer id;
    private String username;
    private String password;
    private String role;
}
