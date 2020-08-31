package com.luoqiu.security.authentication.mobile;

/**
 * 短信发送统一接口
 *
 * @author 落秋
 * @date 2020-08-30 17:13
 */
public interface SmsSend {

    /**
     * 发送短信
     *
     * @param mobile  手机号
     * @param content 短信内容
     * @return 是否发送成功 true | false
     */
    boolean sendSm(String mobile, String content);

}
