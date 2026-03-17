package com.dxlab.gamepromotionweb.Admin.config;

import com.dxlab.gamepromotionweb.Admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private AdminService adminDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsPasswordService(adminDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/login","/", "/css/**","/js/**").permitAll()
                        .anyRequest().authenticated()
        )
                .formLogin(form -> form.loginPage("/login").permitAll()
                        .defaultSuccessUrl("/admin", true))
                .logout(logout -> logout.logoutUrl("/logout").permitAll()
                        .logoutSuccessUrl("/login?logout"))
                .sessionManagement(session -> session.maximumSessions(1));
    return http.build();
    }
}
