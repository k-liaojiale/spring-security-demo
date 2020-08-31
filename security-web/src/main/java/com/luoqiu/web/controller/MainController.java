package com.luoqiu.web.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author 落秋
 * @date 2020-08-19 00:22
 */
@Controller
public class MainController {

    @RequestMapping({"/index", "/", ""})
    public String index(Map<String, Object> map) {
        // 获取登录用户信息
        // 方法1
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            String username = userDetails.getUsername();
            map.put("username", username);
        }


        return "index"; // resources/templates/index.html
    }

    // 方法2
    @ResponseBody
    @RequestMapping("/user/info")
    public Object userInfo(Authentication authentication) {
        return authentication.getPrincipal();
    }

    // 方法3
    @ResponseBody
    @RequestMapping("/user/info2")
    public Object userInfo2(@AuthenticationPrincipal UserDetails userDetails) {
        return userDetails;
    }

}
