package com.microservices.product.config;

import com.microservices.product.config.jwt.JwtConfigurer;
import com.microservices.product.config.jwt.JwtProperties;
import com.microservices.product.entity.security.enums.RoleType;
import com.microservices.product.feignInterface.LoginInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@PropertySource("classpath:security.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtProperties jwt;
    @Autowired
    LoginInterface loginInterface;


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/product/**").hasAnyRole(RoleType.ROLE_APPLICATION.getThisAndHigherPriorities())
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwt,loginInterface));
    }
}
