package com.luoqiu.security.authentication;

import com.luoqiu.base.result.LuoqiuResult;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证成功处理器
 * 决定 响应json还是跳转页面 或者 认证成功后进行其它处理
 *
 * @author 落秋
 * @date 2020-08-27 00:00
 */
@Component("customAuthenticationSuccessHandler")
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 认证成功后 响应JSON字符串
        LuoqiuResult result = LuoqiuResult.ok("认证成功");
        String json = result.toJsonString();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(json);
    }
}
