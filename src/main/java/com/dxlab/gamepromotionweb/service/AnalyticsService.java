package com.dxlab.gamepromotionweb.service;

import com.google.analytics.data.v1beta.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalyticsService {
    private final BetaAnalyticsDataClient analyticsDataClient;
    private static final String PROPERTY_ID = "528584350";

    public AnalyticsService(BetaAnalyticsDataClient client) {
        this.analyticsDataClient = client;
    }

    public Map<String, Object> getOverview(int days) {
        RunReportRequest request = RunReportRequest.newBuilder()
                .setProperty("properties/" + PROPERTY_ID)
                .addDateRanges(DateRange.newBuilder()
                        .setStartDate(days + "daysAgo")
                        .setEndDate("today"))
                .addMetrics(Metric.newBuilder().setName("activeUsers"))
                .addMetrics(Metric.newBuilder().setName("screenPageViews"))
                .addMetrics(Metric.newBuilder().setName("averageSessionDuration"))
                .addMetrics(Metric.newBuilder().setName("engagementRate"))
                .addMetrics(Metric.newBuilder().setName("sessions"))
                .build();

        RunReportResponse response = analyticsDataClient.runReport(request);

        Map<String, Object> data = new HashMap<>();
        if (response.getRowsCount() == 0) {
            data.put("users", 0);
            data.put("pageViews", 0);
            data.put("avgSessionDuration", 0);
            data.put("engagementRate", 0);
            data.put("sessions", 0);
            return data;
        }
        Row row = response.getRows(0);

        data.put("users", Integer.parseInt(row.getMetricValues(0).getValue()));
        data.put("pageViews", Integer.parseInt(row.getMetricValues(1).getValue()));
        data.put("avgSessionDuration", Double.parseDouble(row.getMetricValues(2).getValue()));
        data.put("engagementRate", Double.parseDouble(row.getMetricValues(3).getValue()));
        data.put("sessions", Integer.parseInt(row.getMetricValues(4).getValue()));

        return data;
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
        data.put("overview", getOverview(1));
        data.put("overview30days", getOverview(30));
        data.put("traffic", getTraffic(7));
        data.put("sources", getTrafficSource(7));

        return data;
    }
}

