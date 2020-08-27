package com.luoqiu.security.properties;

/**
 * 认证相关动态配置
 * application.yml 没配置取默认值
 *
 * @author 落秋
 * @date 2020/8/26 17:08
 */
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

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }

    public String getLoginProcessingUrl() {
        return loginProcessingUrl;
    }

    public void setLoginProcessingUrl(String loginProcessingUrl) {
        this.loginProcessingUrl = loginProcessingUrl;
    }

    public String getUsernameParameter() {
        return usernameParameter;
    }

    public void setUsernameParameter(String usernameParameter) {
        this.usernameParameter = usernameParameter;
    }

    public String getPasswordParameter() {
        return passwordParameter;
    }

    public void setPasswordParameter(String passwordParameter) {
        this.passwordParameter = passwordParameter;
    }

    public String[] getStaticPaths() {
        return staticPaths;
    }

    public void setStaticPaths(String[] staticPaths) {
        this.staticPaths = staticPaths;
    }

    public LoginResponseType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginResponseType loginType) {
        this.loginType = loginType;
    }
}
