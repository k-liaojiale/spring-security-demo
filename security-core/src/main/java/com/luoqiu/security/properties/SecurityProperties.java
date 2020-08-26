package com.luoqiu.security.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 落秋
 * @date 2020/8/26 17:04
 */
@Component
@ConfigurationProperties(prefix = "luoqiu.security")
public class SecurityProperties {

    private AuthenticationProperties authentication;

    public AuthenticationProperties getAuthentication() {
        return authentication;
    }

    public void setAuthentication(AuthenticationProperties authentication) {
        this.authentication = authentication;
    }
}
