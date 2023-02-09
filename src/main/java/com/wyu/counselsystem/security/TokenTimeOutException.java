package com.wyu.counselsystem.security;

import org.springframework.security.core.AuthenticationException;

/**
 * @author afglow
 * @Date Create in 2023-02-2023/2/8-20:03
 * @Description
 */
public class TokenTimeOutException extends AuthenticationException {
    public TokenTimeOutException(String msg, Throwable t) {
        super(msg, t);
    }

    public TokenTimeOutException(String msg) {
        super(msg);
    }
}
