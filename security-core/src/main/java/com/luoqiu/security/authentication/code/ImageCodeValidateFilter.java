package com.luoqiu.security.authentication.code;

import com.luoqiu.security.authentication.CustomAuthenticationFailureHandler;
import com.luoqiu.security.authentication.exception.ValidateCodeException;
import com.luoqiu.security.controller.CustomLoginController;
import com.luoqiu.security.properties.SecurityProperties;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OncePerRequestFilter 所有请求之前被调用一次
 *
 * @author 落秋
 * @date 2020-08-29 23:23
 */
@Component("imageCodeValidateFilter")
public class ImageCodeValidateFilter extends OncePerRequestFilter {

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 如果是登录请求, 则校验输入的验证码是否正确
        String loginUrl = securityProperties.getAuthentication().getLoginProcessingUrl();
        if (loginUrl.equals(request.getRequestURI())
            && request.getMethod().equalsIgnoreCase("post")) {
            // 校验验证码合法性
            try {
                validate(request);
            } catch (AuthenticationException e) {
                // 交给失败处理器进行处理异常
                customAuthenticationFailureHandler.onAuthenticationFailure(request, response, e);
                return;
            }
        }

        // 放行请求
        filterChain.doFilter(request, response);
    }

    private void validate(HttpServletRequest request) {
        // 获取 session 中的验证码
        String sessionCode = (String) request.getSession().getAttribute(CustomLoginController.SESSION_KEY);
        // 获取用户输入的验证码
        String inputCode = request.getParameter("code");
        if (StringUtils.isBlank(inputCode)) {
            throw new ValidateCodeException("验证码不能为空");
        }

        if (!inputCode.equalsIgnoreCase(sessionCode)) {
            throw new ValidateCodeException("验证码输入有误");
        }
    }
}
