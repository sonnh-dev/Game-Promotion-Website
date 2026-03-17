package com.dxlab.gamepromotionweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/dashboard")
    private String dashBoard(Model model, Principal principal){
        model.addAttribute("userName", principal.getName());
        return "Dashboard";
    }
}
