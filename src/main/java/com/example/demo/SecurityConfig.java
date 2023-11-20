package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.web.DefaultSecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> securityConfigurerAdapter() {
        return new SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {
            @Override
            public void configure(HttpSecurity http) throws Exception {
                http
                        .authorizeRequests(authorizeRequests ->
                                authorizeRequests
                                        .antMatchers("/cliente/**").hasAuthority("Prueba")
                                        .antMatchers("/admin/**").hasAuthority("Admin")
                                        .anyRequest().authenticated()
                        )
                        .formLogin(withDefaults())
                        .httpBasic(withDefault())
                        .cors().and().csrf().disable();
            }
        };
    }


    private Customizer<HttpBasicConfigurer<HttpSecurity>> withDefault() {
    return httpBasic ->
        httpBasic
            .realmName("My Realm")
            .and();
}

    

    private Customizer<FormLoginConfigurer<HttpSecurity>> withDefaults() {
        return formLogin ->
            formLogin
                .loginPage("user-page")
                .permitAll();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        
        auth
            .inMemoryAuthentication()
                .withUser("username").password("password").roles("Prueba")
                .and()
                .withUser("us").password("passw").roles("Admin");
    } }




