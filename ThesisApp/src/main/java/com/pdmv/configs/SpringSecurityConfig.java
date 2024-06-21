/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pdmv.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author phamdominhvuong
 */
@Configuration
@EnableWebSecurity
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.pdmv.controllers",
    "com.pdmv.repositories",
    "com.pdmv.services"
})
@Order(2)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
            .usernameParameter("username")
            .passwordParameter("password")
            .defaultSuccessUrl("/")
            .failureUrl("/login?error")
            .permitAll();
        
        http.logout()
            .logoutSuccessUrl("/login")
            .permitAll();
        
        http.exceptionHandling()
            .accessDeniedPage("/login?accessDenied");
        
        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/**/admins").access("hasAuthority('ADMIN')") // Dung 'ADMIN' thay cho 'ROLE_ADMIN' -> khong dung hasRole(...)
            .antMatchers("/**/admins/**").access("hasAuthority('ADMIN')")
            .anyRequest().authenticated();
        
        http.csrf().disable();
    }
    
    @Bean
    public Cloudinary cloudinary() {
        Cloudinary cloudinary
                = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "dyuafq1hx",
                        "api_key", "929398868625195",
                        "api_secret", "d8Wfxo3MLa9HX5ueGU0GXaYx1Nc",
                        "secure", true));
        return cloudinary;
    }
}
