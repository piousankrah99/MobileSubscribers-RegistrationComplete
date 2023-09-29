package com.MobileSubscribers.MobileSubscribers.MobileSubscribers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
@Configuration
@EnableWebSecurity
public class SecurityConfig { 
    
    
    private final LogoutHandler logoutHandler;
private final SubscriberService subscriberService;
private UserDetailsService SubscriberService;

    public SecurityConfig(LogoutHandler logoutHandler, SubscriberService userDetailsService) {
        this.logoutHandler = logoutHandler;
        this.subscriberService = userDetailsService;

        }


    @Order
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/Subscribers/**",
                                "/Subscribers/index",
                                "/Subscribers/login",
                                "/Subscribers/sign_up",
                                "/Subscribers/sign_up/save",
                                "/Subscribers/sign_up/mobileSubscriber",
                                "/Subscribers/update/{id}",
                                "/Subscribers/updateSubscriberForm/**",
                                "/Subscribers/css/**",
                                "/Subscribers/css/styles",
                                "/static/**",
                                "/js/**"
                        )
                        .permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/Subscribers/login")
                        .defaultSuccessUrl("/Subscribers/sign_up/mobileSubscriber")
                        .loginProcessingUrl("/Subscribers/login")
                        .failureUrl("/login?error=true")
                        .permitAll())

                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );
              return http.build();
    }





}

