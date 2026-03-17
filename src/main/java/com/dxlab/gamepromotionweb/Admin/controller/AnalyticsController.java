package com.dxlab.gamepromotionweb.Admin.controller;

import com.dxlab.gamepromotionweb.Admin.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private AnalyticsService analyticsService;

    @GetMapping("/overview-7days")
    public Map<String, Object> overview7Days() throws Exception {
        return analyticsService.getOverview(7);
    }

    @GetMapping("/overview-30days")
    public Map<String, Object> overview30Days() throws Exception {
        return analyticsService.getOverview(30);
    }

    @GetMapping("/traffic-7days")
    public List<Map<String, String>> traffic7Days() throws Exception {
        return analyticsService.getTraffic7Days();
    }

    @GetMapping("/traffic-source")
    public List<Map<String, String>> trafficSource() throws Exception {
        return analyticsService.getTrafficSource();
    }
}
