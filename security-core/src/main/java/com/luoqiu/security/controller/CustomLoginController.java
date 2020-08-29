package com.luoqiu.security.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author 落秋
 * @date 2020-08-24 23:53
 */
@Controller
public class CustomLoginController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";

    @RequestMapping("/login/page")
    public String toLogin() {
        return "login"; // classpath: /templates/login.html
    }

    /**
     * 获取图像验证码
     */
    @RequestMapping("/code/image")
    public void imageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 1. 获取验证码字符串
        String code = defaultKaptcha.createText();
        // 2. 把验证码字符串放到session中
        request.getSession().setAttribute(SESSION_KEY, code);
        // 3. 获取验证码图片
        BufferedImage image = defaultKaptcha.createImage(code);
        // 4. 将验证码图片写出去
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }
}
