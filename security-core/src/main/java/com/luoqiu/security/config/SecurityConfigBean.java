package com.luoqiu.security.config;

import com.luoqiu.security.authentication.mobile.SmsCodeSender;
import com.luoqiu.security.authentication.mobile.SmsSend;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 主要为容器中添加 Bean 实例
 *
 * @author 落秋
 * @date 2020-08-30 17:17
 */
@Configuration
public class SecurityConfigBean {

    /**
     * @ConditionalOnMissingBean(SmsSend.class)
     * 默认情况下, 采用的是 SmsCodeSender 实例
     * 但是如果容器中有其它的 SmsSend 类型的实例
     * 那当前的这个 SmsCodeSender 就失效了
     */
    @Bean
    @ConditionalOnMissingBean(SmsSend.class)
    public SmsSend smsSend() {
        return new SmsCodeSender();
    }
}
