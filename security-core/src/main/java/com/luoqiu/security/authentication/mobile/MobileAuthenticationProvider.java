package com.luoqiu.security.authentication.mobile;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 手机认证处理提供者
 *
 * @author 落秋
 * @date 2020-08-30 20:37
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    /**
     * 认证处理：
     * 1. 通过手机号码, 查询用户信息 ( UserDetailsService 实现)
     * 2. 当查询到用户信息, 则认为认证通过 封装 Authentication 对象
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取手机号码
        String principal = authentication.getName();
        // 通过手机号, 查询用户信息
        UserDetails userDetails = userDetailsService.loadUserByUsername(principal);

        if (userDetails == null) {
            throw new AuthenticationServiceException("该手机号未注册");
        }

        // 认证通过
        // 封装到 MobileAuthenticationToken
        MobileAuthenticationToken authenticationToken = new MobileAuthenticationToken(userDetails, userDetails.getAuthorities());
        authenticationToken.setDetails(authentication.getDetails());
        return authenticationToken;
    }

    /**
     * 通过这个方法来选择对应的 provider, 即选择 MobileAuthenticationProvider
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return (MobileAuthenticationToken.class
                .isAssignableFrom(authentication));
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    protected UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

}
