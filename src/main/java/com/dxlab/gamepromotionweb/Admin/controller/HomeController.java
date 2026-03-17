package com.dxlab.gamepromotionweb.Admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login(){
        return "Login";
    }

    @GetMapping("/dashboard")
    private String dashBoard(Model model, Principal principal){
        model.addAttribute("userName", principal.getName());
        return "Dashboard";
    }
}
