package com.luoqiu.security;

import com.luoqiu.security.authentication.mobile.SmsSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 落秋
 * @date 2020-08-30 17:22
 */
@Component
public class MobileSmsCodeSender implements SmsSend {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean sendSm(String mobile, String content) {
        logger.info("Web应用向手机号：" + mobile + "发送的短信验证码为：" + content);
        return true;
    }
}
