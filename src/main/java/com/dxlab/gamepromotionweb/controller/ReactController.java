package com.dxlab.gamepromotionweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactController {
    @RequestMapping(value = "/home/{path:[^\\.]*}")
    public String redirect() {
        return "forward:/home/index.html";
    }
}
