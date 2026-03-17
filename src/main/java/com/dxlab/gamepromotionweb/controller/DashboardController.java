package com.dxlab.gamepromotionweb.controller;

import com.dxlab.gamepromotionweb.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class DashboardController {

    private final AnalyticsService analyticsService;

    public DashboardController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/dashboard")
    public Map<String, Object> getDashboard() {
        return analyticsService.getDashboard();
    }
}
