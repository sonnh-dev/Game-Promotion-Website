package com.dxlab.gamepromotionweb.service;

import com.google.analytics.data.v1beta.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class AnalyticsService {
    private final BetaAnalyticsDataClient analyticsDataClient;
    private static final String PROPERTY_ID = "528584350";

    public AnalyticsService(BetaAnalyticsDataClient client) {
        this.analyticsDataClient = client;
    }

    public Map<String, Object> getOverview() {
        RunReportRequest request = RunReportRequest.newBuilder()
                .setProperty("properties/" + PROPERTY_ID)
                .addDateRanges(DateRange.newBuilder()
                        .setStartDate("30daysAgo")
                        .setEndDate("today"))
                .addDimensions(Dimension.newBuilder().setName("date"))
                .addMetrics(Metric.newBuilder().setName("activeUsers"))
                .addMetrics(Metric.newBuilder().setName("screenPageViews"))
                .addMetrics(Metric.newBuilder().setName("averageSessionDuration"))
                .addMetrics(Metric.newBuilder().setName("engagementRate"))
                .addMetrics(Metric.newBuilder().setName("sessions"))
                .build();

        RunReportResponse response = analyticsDataClient.runReport(request);
        List<Map<String, Object>> overviewDaily = new LinkedList<>();

        for (Row row : response.getRowsList()) {
            Map<String, Object> day = new HashMap<>();
            String date = row.getDimensionValues(0).getValue(); // yyyyMMdd
            day.put("date", date);
            day.put("users", Integer.parseInt(row.getMetricValues(0).getValue()));
            day.put("pageViews", Integer.parseInt(row.getMetricValues(1).getValue()));
            day.put("avgSessionDuration", Double.parseDouble(row.getMetricValues(2).getValue()));
            day.put("engagementRate", Double.parseDouble(row.getMetricValues(3).getValue()));
            day.put("sessions", Integer.parseInt(row.getMetricValues(4).getValue()));
            overviewDaily.add(day);
        }

        // --- Tổng hợp overview cho 30 ngày ---
        int totalUsers = 0, totalPageViews = 0, totalSessions = 0;
        double totalDuration = 0, totalEngagement = 0;

        for (Map<String, Object> day : overviewDaily) {
            totalUsers += (Integer) day.get("users");
            totalPageViews += (Integer) day.get("pageViews");
            totalSessions += (Integer) day.get("sessions");
            totalDuration += (Double) day.get("avgSessionDuration");
            totalEngagement += (Double) day.get("engagementRate");
        }
        Map<String, Object> overview = new HashMap<>();
        overview.put("users", totalUsers);
        overview.put("pageViews", totalPageViews);
        overview.put("sessions", totalSessions);
        overview.put("avgSessionDuration", totalDuration / overviewDaily.size());
        overview.put("engagementRate", totalEngagement / overviewDaily.size());

        // --- growth hôm nay/ hôm qua ---
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        Map<String, Double> todayMetrics = new HashMap<>();
        Map<String, Double> yesterdayMetrics = new HashMap<>();

        String[] keys = {"sessions", "users", "pageViews", "avgSessionDuration", "engagementRate"};

        for (String k : keys) {
            todayMetrics.put(k, 0.0);
            yesterdayMetrics.put(k, 0.0);
        }

        // lấy dữ liệu hôm nay và hôm qua
        for (Map<String, Object> d : overviewDaily) {
            LocalDate dDate = LocalDate.parse((String) d.get("date"), DateTimeFormatter.ofPattern("yyyyMMdd"));

            for (String k : keys) {
                double value = 0.0;
                Object obj = d.get(k);
                if (obj instanceof Number) {
                    value = ((Number) obj).doubleValue();
                }

                if (dDate.equals(today)) todayMetrics.put(k, value);
                else if (dDate.equals(yesterday)) yesterdayMetrics.put(k, value);
            }
        }

        Map<String, Double> growth = new HashMap<>();
        for (String k : keys) {
            double yVal = yesterdayMetrics.get(k);
            double tVal = todayMetrics.get(k);
            double g = (yVal != 0.0) ? ((tVal - yVal) / yVal * 100.0) : 0.0;
            growth.put(k, g);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("overview", overview);
        result.put("overviewDaily", overviewDaily);
        result.put("growth", growth);

        return result;
    }

    public List<Map<String, Object>> getTraffic(int days) {

        RunReportRequest request = RunReportRequest.newBuilder()
                .setProperty("properties/" + PROPERTY_ID)
                .addDimensions(Dimension.newBuilder().setName("date"))
                .addMetrics(Metric.newBuilder().setName("sessions"))
                .addDateRanges(DateRange.newBuilder()
                        .setStartDate(days + "daysAgo")
                        .setEndDate("today"))
                .addOrderBys(OrderBy.newBuilder()
                        .setDimension(OrderBy.DimensionOrderBy.newBuilder()
                                .setDimensionName("date")))
                .build();

        RunReportResponse response = analyticsDataClient.runReport(request);
        if (response.getRowsCount() == 0) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> result = new ArrayList<>();

        for (Row row : response.getRowsList()) {

            String rawDate = row.getDimensionValues(0).getValue();
            String formattedDate = rawDate.substring(6, 8) + "/" + rawDate.substring(4, 6);

            Map<String, Object> item = new HashMap<>();
            item.put("date", formattedDate);
            item.put("sessions", row.getMetricValues(0).getValue());

            result.add(item);
        }

        return result;
    }

    public List<Map<String, Object>> getTrafficSource(int days) {

        RunReportRequest request = RunReportRequest.newBuilder()
                .setProperty("properties/" + PROPERTY_ID)
                .addDimensions(Dimension.newBuilder()
                        .setName("sessionDefaultChannelGroup"))
                .addMetrics(Metric.newBuilder()
                        .setName("sessions"))

                .addDateRanges(DateRange.newBuilder()
                        .setStartDate(days + "daysAgo")
                        .setEndDate("today"))
                .addOrderBys(OrderBy.newBuilder()
                        .setMetric(OrderBy.MetricOrderBy.newBuilder()
                                .setMetricName("sessions"))
                        .setDesc(true))
                .build();

        RunReportResponse response = analyticsDataClient.runReport(request);
        if (response.getRowsCount() == 0) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> result = new ArrayList<>();

        for (Row row : response.getRowsList()) {

            Map<String, Object> item = new HashMap<>();

            item.put("source", row.getDimensionValues(0).getValue());
            item.put("sessions", row.getMetricValues(0).getValue());

            result.add(item);
        }

        return result;
    }

    // ================= COMBINE ALL  =================
    public Map<String, Object> getDashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("overview", getOverview());
        data.put("traffic", getTraffic(7));
        data.put("sources", getTrafficSource(7));

        return data;
    }
}
