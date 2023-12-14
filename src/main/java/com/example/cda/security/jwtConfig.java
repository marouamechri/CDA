package com.example.cda.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class jwtConfig {

    @Bean
    public JwtFilter securityFilter() {
        return new JwtFilter();
    }

    //
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
                .csrf(csrf->csrf.disable())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(securityFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests((authz) -> authz
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // Autoriser les requêtes préliminaires
                        .requestMatchers(new AntPathRequestMatcher("/login", "POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/signup", "POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/forgotPassword", "POST")).permitAll()

                        .requestMatchers("/user/**").hasAnyAuthority("USER", "ADMIN")
                        .requestMatchers("/admin/**").access("hasAuthority('ADMIN') and hasAuthority('USER')")
                        .anyRequest().authenticated());
        return http.build();
    }
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Autoriser les requêtes depuis http://localhost:4200
        config.addAllowedOrigin("*");

        // Autoriser tous les en-têtes
        config.addAllowedHeader("*");

        // Autoriser toutes les méthodes (GET, POST, PUT, DELETE, etc.)
        config.addAllowedMethod("*");

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

  /*  @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.applyPermitDefaultValues();
        // Configurer mes options
        corsConfig.setAllowCredentials(true);

        corsConfig.addAllowedOrigin("*");
        corsConfig.addAllowedMethod("*");
        /*corsConfig.addAllowedMethod("POST");
        corsConfig.addAllowedMethod("PUT");
        corsConfig.addAllowedMethod("DELETE");
        corsConfig.addAllowedMethod("OPTIONS");*/

       /* corsConfig.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);


        return source;
    }*/


}
