package com.luoqiu.security.authentication.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 验证码相关异常类
 *
 * @author 落秋
 * @date 2020-08-29 23:31
 */
public class ValidateCodeException extends AuthenticationException {
    public ValidateCodeException(String msg, Throwable t) {
        super(msg, t);
    }

    public ValidateCodeException(String msg) {
        super(msg);
    }
}
