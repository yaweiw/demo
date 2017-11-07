package com.example.demo;

import com.microsoft.azure.spring.autoconfigure.aad.AADAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoRestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@EnableOAuth2Sso
@EnableGlobalMethodSecurity(securedEnabled = true,
        prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private AADAuthenticationFilter aadAuthFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/home").authenticated();
        http.authorizeRequests().antMatchers("/api/**").authenticated();

        http.logout().logoutSuccessUrl("/").permitAll();

        http.authorizeRequests().anyRequest().permitAll();

        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        http.addFilterBefore(aadAuthFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
