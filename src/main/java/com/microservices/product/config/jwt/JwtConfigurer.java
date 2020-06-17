package com.microservices.product.config.jwt;

import com.microservices.product.feignInterface.LoginInterface;
import com.microservices.product.filters.JwtRequestFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    JwtProperties jwt;
    LoginInterface loginInterface;

    public JwtConfigurer(JwtProperties jwt, LoginInterface loginInterface) {
        this.jwt = jwt;
        this.loginInterface = loginInterface;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        JwtRequestFilter customFilter = new JwtRequestFilter(jwt,loginInterface);
        http.exceptionHandling()
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .and()
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

