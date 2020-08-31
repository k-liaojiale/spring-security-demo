package com.luoqiu.security.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * 认证相关动态配置
 * application.yml 没配置取默认值
 *
 * @author 落秋
 * @date 2020/8/26 17:08
 */
@Getter
@Setter
public class AuthenticationProperties {

    private String loginPage = "/login/page";

    private String loginProcessingUrl = "/login/form";

    private String usernameParameter = "name";

    private String passwordParameter = "pwd";

    private String[] staticPaths = {"/dist/**", "/modules/**", "/plugins/**"};

    /**
     * 登录成功后响应 JSON ,还是重定向
     * 如果 application.yml 中没有配置,则取此初始值 REDIRECT
     */
    private LoginResponseType loginType = LoginResponseType.REDIRECT;

    /**
     * 获取图形验证码地址
     */
    private String imageCodeUrl = "/code/image";

    /**
     * 发送手机短信验证码地址
     */
    private String mobileCodeUrl = "/code/mobile";

    /**
     * 前往手机登录页面
     */
    private String mobilePage = "/mobile/page";

    /**
     * 记住我功能有效时长
     */
    private int tokenValiditySeconds = 604800;

}
