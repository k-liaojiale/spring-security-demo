package com.luoqiu.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author 落秋
 * @date 2020-08-26 22:46
 */
@Component("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("请求认证的用户名：" + username);

        // 1. 通过请求的用户名去数据库中查询用户信息
        if (!"luoqiu".equalsIgnoreCase(username)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        // 2. 查询该用户有哪一些权限

        // 假设用户在数据库中存储的密码
        String password = passwordEncoder.encode("1234");

        // 3. 封装用户信息和权限信息
        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("ADMIN"));
    }
}
