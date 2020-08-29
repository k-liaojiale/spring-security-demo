package com.luoqiu.security.authentication;

import com.luoqiu.base.result.LuoqiuResult;
import com.luoqiu.security.properties.LoginResponseType;
import com.luoqiu.security.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
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
//public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        if (LoginResponseType.JSON.equals(
                securityProperties.getAuthentication().getLoginType()
        )) {
            LuoqiuResult result = LuoqiuResult.build(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
            String json = result.toJsonString();
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(json);
        } else {
            super.setDefaultFailureUrl(securityProperties.getAuthentication().getLoginPage() + "?error");
            super.onAuthenticationFailure(request, response, exception);
        }
    }
}
