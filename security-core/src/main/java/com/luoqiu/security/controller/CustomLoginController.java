package com.luoqiu.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 落秋
 * @date 2020-08-24 23:53
 */
@Controller
public class CustomLoginController {

    @RequestMapping("/login/page")
    public String toLogin() {
        return "login"; // classpath: /templates/login.html
    }
}
