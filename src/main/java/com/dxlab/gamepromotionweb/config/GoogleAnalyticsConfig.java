package com.dxlab.gamepromotionweb.config;

import com.google.analytics.data.v1beta.BetaAnalyticsDataClient;
import com.google.analytics.data.v1beta.BetaAnalyticsDataSettings;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class GoogleAnalyticsConfig {
    @Bean
    public BetaAnalyticsDataClient analyticsDataClient() throws IOException {
        GoogleCredentials credentials = GoogleCredentials
                .fromStream(new FileInputStream("C:\\Users\\doant\\IdeaProjects\\Game-Promotion-Website\\src\\main\\resources\\credentials.json"))
                .createScoped("https://www.googleapis.com/auth/analytics.readonly");

        BetaAnalyticsDataSettings settings = BetaAnalyticsDataSettings.newBuilder()
                .setCredentialsProvider(() -> credentials)
                .build();

        return BetaAnalyticsDataClient.create(settings);
    }
}

