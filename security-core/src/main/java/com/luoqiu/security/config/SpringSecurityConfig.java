package com.luoqiu.security.config;

import com.luoqiu.security.authentication.code.ImageCodeValidateFilter;
import com.luoqiu.security.authentication.mobile.MobileAuthenticationConfig;
import com.luoqiu.security.authentication.mobile.MobileValidateFilter;
import com.luoqiu.security.properties.SecurityProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

/**
 * 安全控制中心
 *
 * @author 落秋
 * @date 2020/8/20 13:44
 */
@Configuration
@EnableWebSecurity // 启动 SpringSecurity 过滤器链功能
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private UserDetailsService customUserDetailsService;

    @Autowired
    private AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private ImageCodeValidateFilter imageCodeValidateFilter;

    @Autowired
    private DataSource dataSource;

    // 校验手机验证码
    @Autowired
    private MobileValidateFilter mobileValidateFilter;

    // 校验手机号是否存在 -> 手机号认证
    @Autowired
    private MobileAuthenticationConfig mobileAuthenticationConfig;


    @Bean
    public PasswordEncoder passwordEncoder() {
        // 设置默认的加密方式
        // 明文 + 随机盐值 》加密存储
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

//        String password = passwordEncoder().encode("1234");
//        logger.info("加密之后存储的密码：" + password);
//        // 用户信息存储在内存中
//        // 存储的密码必须是加密后的 否则报错：There is no PasswordEncoder
//        auth.inMemoryAuthentication().withUser("luoqiu")
//                .password(password).authorities("ADMIN");

        auth.userDetailsService(customUserDetailsService);
    }

    /**
     * 记住我功能
     *
     * @return
     */
    @Bean
    public JdbcTokenRepositoryImpl jdbcTokenRepository () {
        JdbcTokenRepositoryImpl jdbcTokenRepository= new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 项目启动时, 自动创建表
        // true: 自动创建
        // jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

    /**
     * 当你认证成功后，springsecurity它会重定向到你上一次请求上
     * 那页面中的请求呢？？
     *
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
        // http.httpBasic() // 采用 httpBasic 认证方式

// 固定配置
//        http.formLogin() // 表单认证
//                .loginPage("/login/page")
//                .loginProcessingUrl("/login/form") // 登录表单提交处理url 默认：/login
//                .usernameParameter("name") // 默认：username
//                .passwordParameter("pwd") // 默认：password
//                .and()
//                .authorizeRequests() // 授权请求
//                .antMatchers("/login/page").permitAll() // 放行/login/page不需要认证可访问
//                .anyRequest().authenticated() // 所有访问该应用的http请求都要通过身份认证才可以访问
//        ;

          // 动态配置
          http.addFilterBefore(mobileValidateFilter, UsernamePasswordAuthenticationFilter.class)
              .addFilterBefore(imageCodeValidateFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin() // 表单认证
                .loginPage(securityProperties.getAuthentication().getLoginPage())
                .loginProcessingUrl(securityProperties.getAuthentication().getLoginProcessingUrl()) // 登录表单提交处理url 默认：/login
                .usernameParameter(securityProperties.getAuthentication().getUsernameParameter()) // 默认：username
                .passwordParameter(securityProperties.getAuthentication().getPasswordParameter()) // 默认：password
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .and()
                .authorizeRequests() // 授权请求
                .antMatchers(securityProperties.getAuthentication().getLoginPage(),
                             securityProperties.getAuthentication().getImageCodeUrl(),
                             securityProperties.getAuthentication().getMobileCodeUrl(),
                             securityProperties.getAuthentication().getMobilePage()).permitAll() // 放行/login/page不需要认证可访问
                .anyRequest().authenticated() // 所有访问该应用的http请求都要通过身份认证才可以访问
                .and()
                .rememberMe() // 记住功能
                .tokenRepository(jdbcTokenRepository()) // 保存登录信息
                .tokenValiditySeconds(securityProperties.getAuthentication().getTokenValiditySeconds()) // 记住我有效时长
          ;

          // 将手机认证添加到过滤器链上
          http.apply(mobileAuthenticationConfig);
    }

    /**
     * 一般是针对静态资源放行
     * @param web
     */
    @Override
    public void configure(WebSecurity web)  {
        // web.ignoring().antMatchers("/dist/**", "/modules/**", "/plugins/**");
        web.ignoring().antMatchers(securityProperties.getAuthentication().getStaticPaths());
    }
}
