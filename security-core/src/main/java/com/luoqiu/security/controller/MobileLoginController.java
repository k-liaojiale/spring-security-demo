package com.luoqiu.security.controller;

import com.luoqiu.base.result.LuoqiuResult;
import com.luoqiu.security.authentication.mobile.SmsSend;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 落秋
 * @date 2020-08-30 17:26
 */
@Controller
public class MobileLoginController {

    public static final String SESSION_KEY = "SESSION_KEY_MOBILE_CODE";

    @Autowired
    private SmsSend smsSend;

    @RequestMapping("/mobile/page")
    public String toMobilePage() {
        return "login-mobile";
    }

    /**
     * 发送手机验证码
     */
    @ResponseBody // 响应 json 字符串
    @RequestMapping("/code/mobile")
    public LuoqiuResult smsCode(HttpServletRequest request) {
        // 1. 生成一个手机验证码
        String code = RandomStringUtils.randomNumeric(4);
        // 2. 将手机验证码保存到session中
        request.getSession().setAttribute(SESSION_KEY, code);
        String mobile = request.getParameter("mobile");
        // 3. 发送验证码到用户手机上
        smsSend.sendSm(mobile, code);

        return LuoqiuResult.ok();
    }
}
