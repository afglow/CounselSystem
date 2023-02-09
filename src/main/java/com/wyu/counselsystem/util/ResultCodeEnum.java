package com.wyu.counselsystem.util;

/**
 * @author afglow
 * @Date Create in 2022-10-2022/10/31-10:55
 * @Description
 */
import lombok.Getter;

/**
 * 统一返回结果状态信息类
 *
 */
@Getter
public enum ResultCodeEnum {

    SUCCESS(200,"成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(202, "服务异常"),
    HAD_REGISTER(203,"用户名已经注册过了！"),
    ILLEGAL_REQUEST( 204, "非法请求"),
    ARGUMENT_VALID_ERROR(206, "参数校验错误"),

    LOGIN_ERROR(207, "用户名或密码错误"),
    LOGIN_AUTH(401, "未登陆"),
    PERMISSION(403, "没有权限"),

    //2022-02-22
    LOGIN_CODE(222,"长时间未操作,会话已失效,请刷新页面后重试!"),
    CODE_ERROR(223,"验证码错误!"),
    TOKEN_ERROR(224,"Token失效，请重新登录!");

    private Integer code;

    private String message;

    private ResultCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
