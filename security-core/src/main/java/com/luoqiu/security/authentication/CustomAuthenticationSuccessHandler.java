package com.luoqiu.security.authentication;

import com.alibaba.fastjson.JSON;
import com.luoqiu.base.result.LuoqiuResult;
import com.luoqiu.security.properties.LoginResponseType;
import com.luoqiu.security.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
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
//public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if (LoginResponseType.JSON.equals(securityProperties.getAuthentication().getLoginType())) {
            // 认证成功后 响应JSON字符串
            LuoqiuResult result = LuoqiuResult.ok("认证成功");
            String json = result.toJsonString();
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(json);
        } else {
            // 重定向到上次请求的地址上, 引发跳转到认证页面的地址
            logger.info("authentication:" + JSON.toJSONString(authentication));
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}
