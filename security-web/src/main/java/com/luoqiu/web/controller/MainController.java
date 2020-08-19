package com.luoqiu.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 落秋
 * @date 2020-08-19 00:22
 */
@Controller
public class MainController {

    @RequestMapping({"/index", "/", ""})
    public String index() {
        return "index"; // resources/templates/index.html
    }
}
