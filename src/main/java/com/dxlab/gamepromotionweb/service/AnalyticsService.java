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
    private static final DateTimeFormatter GA_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd");

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
                .addOrderBys(OrderBy.newBuilder()
                        .setDimension(OrderBy.DimensionOrderBy.newBuilder()
                                .setDimensionName("date")))
                .build();

        RunReportResponse response = analyticsDataClient.runReport(request);
        List<Map<String, Object>> overviewDaily = new ArrayList<>();

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

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        String todayKey = today.format(GA_DATE_FORMAT);
        String yesterdayKey = yesterday.format(GA_DATE_FORMAT);

        Map<String, Object> latestDay = overviewDaily.isEmpty() ? null : overviewDaily.get(overviewDaily.size() - 1);
        Map<String, Object> previousDay = overviewDaily.size() > 1 ? overviewDaily.get(overviewDaily.size() - 2) : null;
        Map<String, Object> todayDay = null;
        Map<String, Object> yesterdayDay = null;

        for (Map<String, Object> day : overviewDaily) {
            String date = (String) day.get("date");
            if (todayKey.equals(date)) {
                todayDay = day;
            } else if (yesterdayKey.equals(date)) {
                yesterdayDay = day;
            }
        }

        Map<String, Object> effectiveToday = todayDay != null ? todayDay : latestDay;
        Map<String, Object> effectiveYesterday = yesterdayDay != null ? yesterdayDay : previousDay;

        Map<String, Object> overview = mapOverviewMetrics(effectiveToday);

        String[] keys = {"sessions", "users", "pageViews", "avgSessionDuration", "engagementRate"};
        Map<String, Double> growth = new HashMap<>();
        for (String k : keys) {
            double tVal = getNumericMetric(effectiveToday, k);
            double yVal = getNumericMetric(effectiveYesterday, k);
            double g = (yVal != 0.0) ? ((tVal - yVal) / yVal * 100.0) : 0.0;
            growth.put(k, g);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("overview", overview);
        result.put("overviewDaily", overviewDaily);
        result.put("growth", growth);
        result.put("latestProcessedDate", latestDay == null ? null : latestDay.get("date"));
        result.put("requestedDate", todayKey);

        return result;
    }

    private Map<String, Object> mapOverviewMetrics(Map<String, Object> day) {
        Map<String, Object> overview = new HashMap<>();
        overview.put("users", day == null ? 0 : ((Number) day.getOrDefault("users", 0)).intValue());
        overview.put("pageViews", day == null ? 0 : ((Number) day.getOrDefault("pageViews", 0)).intValue());
        overview.put("sessions", day == null ? 0 : ((Number) day.getOrDefault("sessions", 0)).intValue());
        overview.put("avgSessionDuration", day == null ? 0.0 : ((Number) day.getOrDefault("avgSessionDuration", 0.0)).doubleValue());
        overview.put("engagementRate", day == null ? 0.0 : ((Number) day.getOrDefault("engagementRate", 0.0)).doubleValue());
        return overview;
    }

    private double getNumericMetric(Map<String, Object> day, String key) {
        if (day == null) {
            return 0.0;
        }
        Object value = day.get(key);
        return value instanceof Number ? ((Number) value).doubleValue() : 0.0;
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

    public Map<String, Object> getRealtime() {
        RunRealtimeReportRequest request = RunRealtimeReportRequest.newBuilder()
                .setProperty("properties/" + PROPERTY_ID)
                .addMetrics(Metric.newBuilder().setName("activeUsers"))
                .addMetrics(Metric.newBuilder().setName("screenPageViews"))
                .build();

        RunRealtimeReportResponse response = analyticsDataClient.runRealtimeReport(request);

        Map<String, Object> result = new HashMap<>();
        result.put("activeUsers", 0);
        result.put("screenPageViews", 0);
        result.put("updatedAt", java.time.Instant.now().toString());

        if (response.getRowsCount() == 0) {
            return result;
        }

        Row row = response.getRows(0);
        result.put("activeUsers", Integer.parseInt(row.getMetricValues(0).getValue()));
        result.put("screenPageViews", Integer.parseInt(row.getMetricValues(1).getValue()));
        return result;
    }

    // ================= COMBINE ALL  =================
    public Map<String, Object> getDashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("overview", getOverview());
        data.put("traffic", getTraffic(7));
        data.put("sources", getTrafficSource(7));
        data.put("realtime", getRealtime());

        return data;
    }
}
