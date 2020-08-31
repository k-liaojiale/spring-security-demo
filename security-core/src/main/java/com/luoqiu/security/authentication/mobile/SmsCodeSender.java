package com.luoqiu.security.authentication.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 发送短信验证码, 第三方的短信服务接口
 *
 * @author 落秋
 * @date 2020-08-30 17:14
 */
public class SmsCodeSender implements SmsSend {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean sendSm(String mobile, String content) {
        String sendContent = String.format("短信验证码%s, 请勿泄露。", content);
        logger.info("向手机号：" + mobile + "发送的短信验证码为：" + content);
        return true;
    }
}
