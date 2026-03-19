package com.dxlab.gamepromotionweb.controller;

import com.dxlab.gamepromotionweb.entity.News;
import com.dxlab.gamepromotionweb.repository.NewsRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardPagesController {

    NewsRepository newsRepository;

    private static void addCommonModel(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("userName", principal.getName());
        }
    }

    @GetMapping("/news")
    public String news(Model model, Principal principal, Pageable pageable) {
        addCommonModel(model, principal);

        return "dashboard/news";
    }

    @GetMapping("/characters")
    public String characters(Model model, Principal principal) {
        addCommonModel(model, principal);
        return "dashboard/charactersPage";
    }

    @GetMapping("/rankings")
    public String rankings(Model model, Principal principal) {
        addCommonModel(model, principal);
        return "dashboard/rankingsPage";
    }

    @GetMapping("/media")
    public String media(Model model, Principal principal) {
        addCommonModel(model, principal);
        return "dashboard/mediaPage";
    }

    @GetMapping("/theme")
    public String theme(Model model, Principal principal) {
        addCommonModel(model, principal);
        return "dashboard/themePage";
    }
}

