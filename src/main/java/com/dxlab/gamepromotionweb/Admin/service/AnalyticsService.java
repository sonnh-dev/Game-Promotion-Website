package com.dxlab.gamepromotionweb.Admin.service;

import com.google.analytics.data.v1beta.*;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AnalyticsService {
    private static final String PROPERTY_ID = "528584350";
    private final BetaAnalyticsDataClient analyticsDataClient;

    public AnalyticsService() throws Exception {

        GoogleCredentials credentials =
                GoogleCredentials.fromStream(
                        new FileInputStream("C:\\Users\\sonpk\\Desktop\\credentials.json"));

        BetaAnalyticsDataSettings settings =
                BetaAnalyticsDataSettings.newBuilder()
                        .setCredentialsProvider(() -> credentials)
                        .build();

        this.analyticsDataClient = BetaAnalyticsDataClient.create(settings);
    }

    public Map<String, Object> getOverview(int days) throws Exception {
        RunReportRequest request =
                RunReportRequest.newBuilder()
                        .setProperty("properties/" + PROPERTY_ID)

                        .addDateRanges(
                                DateRange.newBuilder()
                                        .setStartDate(days + "daysAgo")
                                        .setEndDate("today"))

                        .addMetrics(Metric.newBuilder().setName("activeUsers"))
                        .addMetrics(Metric.newBuilder().setName("screenPageViews"))
                        .addMetrics(Metric.newBuilder().setName("averageSessionDuration"))
                        .addMetrics(Metric.newBuilder().setName("engagementRate"))
                        .addMetrics(Metric.newBuilder().setName("sessions"))

                        .build();

        RunReportResponse response = analyticsDataClient.runReport(request);

        Row row = response.getRows(0);

        Map<String, Object> data = new HashMap<>();

        data.put("users", row.getMetricValues(0).getValue());
        data.put("pageViews", row.getMetricValues(1).getValue());
        data.put("avgSessionDuration", row.getMetricValues(2).getValue());
        data.put("engagementRate", row.getMetricValues(3).getValue());
        data.put("sessions", row.getMetricValues(4).getValue());

        return data;
    }

    public List<Map<String, String>> getTraffic7Days() throws Exception {

        RunReportRequest request =
                RunReportRequest.newBuilder()
                        .setProperty("properties/" + PROPERTY_ID)

                        .addDimensions(
                                Dimension.newBuilder().setName("date"))

                        .addMetrics(
                                Metric.newBuilder().setName("sessions"))

                        .addDateRanges(
                                DateRange.newBuilder()
                                        .setStartDate("7daysAgo")
                                        .setEndDate("today"))

                        .build();

        RunReportResponse response = analyticsDataClient.runReport(request);

        List<Map<String, String>> list = new ArrayList<>();

        for (Row row : response.getRowsList()) {

            Map<String, String> item = new HashMap<>();

            item.put("date", row.getDimensionValues(0).getValue());
            item.put("sessions", row.getMetricValues(0).getValue());

            list.add(item);
        }

        return list;
    }

    public List<Map<String, String>> getTrafficSource() throws Exception {

        RunReportRequest request =
                RunReportRequest.newBuilder()
                        .setProperty("properties/" + PROPERTY_ID)

                        .addDimensions(
                                Dimension.newBuilder()
                                        .setName("sessionDefaultChannelGroup"))

                        .addMetrics(
                                Metric.newBuilder()
                                        .setName("sessions"))

                        .addDateRanges(
                                DateRange.newBuilder()
                                        .setStartDate("7daysAgo")
                                        .setEndDate("today"))

                        .build();

        RunReportResponse response = analyticsDataClient.runReport(request);

        List<Map<String, String>> result = new ArrayList<>();

        for (Row row : response.getRowsList()) {

            Map<String, String> item = new HashMap<>();

            item.put("source", row.getDimensionValues(0).getValue());
            item.put("sessions", row.getMetricValues(0).getValue());

            result.add(item);
        }

        return result;
    }
}
