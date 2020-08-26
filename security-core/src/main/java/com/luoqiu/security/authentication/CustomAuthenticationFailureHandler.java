package com.luoqiu.security.authentication;

import com.luoqiu.base.result.LuoqiuResult;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理器
 *
 * @author 落秋
 * @date 2020-08-27 00:15
 */
@Component("customAuthenticationFailureHandler")
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        LuoqiuResult result = LuoqiuResult.build(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
        String json = result.toJsonString();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
}
