package com.luoqiu.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 安全控制中心
 *
 * @author 落秋
 * @date 2020/8/20 13:44
 */
@Configuration
@EnableWebSecurity // 启动 SpringSecurity 过滤器链功能
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Bean
    public PasswordEncoder passwordEncoder() {
        // 设置默认的加密方式
        return new BCryptPasswordEncoder();
        // return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 认证管理器：
     * 1、认证信息提供方式（用户名、密码、当前用户的资源权限）
     * 2、可采用内存存储方式，也可能采用数据库方式等
     *
     * @param auth
     * @throw Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // super.configure(auth);
        // auth.inMemoryAuthentication().withUser("luoqiu")
        //     .password("1234").authorities("ADMIN");
        String password = passwordEncoder().encode("1234");
        logger.info("加密之后存储的密码：" + password);
        // 用户信息存储在内存中
        auth.inMemoryAuthentication().withUser("luoqiu")
                .password(password).authorities("ADMIN");

    }

    /**
     * 资源权限配置（过滤器链）:
     * 1、被拦截的资源
     * 2、资源所对应的角色权限
     * 3、定义认证方式：httpBasic 、httpForm
     * 4、定制登录页面、登录请求地址、错误处理方式
     * 5、自定义 spring security 过滤器
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // super.configure(http);
        //http.httpBasic() // 采用 httpBasic 认证方式
        http.formLogin() // 表单认证
                .and()
                .authorizeRequests() // 授权请求
                .anyRequest().authenticated() // 所有访问该应用的http请求都要通过身份认证才可以访问
        ;
    }
}
