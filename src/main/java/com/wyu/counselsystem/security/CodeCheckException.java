package com.wyu.counselsystem.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author afglow
 * @Date Create in 2023-02-2023/2/4-20:54
 * @Description
 */
public class CodeCheckException extends AuthenticationException {
    public CodeCheckException(String msg, Throwable t) {
        super(msg, t);
    }

    public CodeCheckException(String msg) {
        super(msg);
    }
}
