package com.example.cda.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.web.cors.CorsConfiguration;


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

        HttpSecurity httpSecurity = http    //.cors().and().csrf().disable()
                .cors(cors -> cors.disable())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // On place notre filter dans le middleware
                .addFilterBefore(securityFilter(), UsernamePasswordAuthenticationFilter.class)
                //.authorizeHttpRequests((authz) -> authz.anyRequest().permitAll());

               .authorizeRequests((authz) -> authz
                        .requestMatchers("/login", "/signup", "/forgotPassword").permitAll()
                        .requestMatchers("/admin/**")
                        .hasAuthority("ADMIN")
                        //.requestMatchers("/user/**")
                        //.hasAuthority("USER")
                        .anyRequest().authenticated());


        return httpSecurity.build();
    }
}
