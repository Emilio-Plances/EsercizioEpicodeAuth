package com.example.EsercizioEpicode.security;

import com.example.EsercizioEpicode.enums.Ruolo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityChain {
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(AbstractHttpConfigurer::disable);

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.authorizeHttpRequests(request->request.requestMatchers("/api/dipendenti/**").hasAnyAuthority(Ruolo.ADMIN.name(),Ruolo.USER.name()));
        httpSecurity.authorizeHttpRequests(request->request.requestMatchers(("/api/auth/**")).permitAll());
        httpSecurity.authorizeHttpRequests(request->request.requestMatchers(("/**")).denyAll());

        return httpSecurity.build();
    }
}
