package com.luoqiu.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 通过手机号获取用户信息和权限资源
 *
 * @author 落秋
 * @date 2020-08-30 21:51
 */
@Component("mobileUserDetailsService")
public class MobileUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        logger.info("请求的手机号是：" + mobile);
        return new User("luoqiu", "", true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
