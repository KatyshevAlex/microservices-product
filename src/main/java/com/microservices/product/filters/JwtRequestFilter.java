package com.microservices.product.filters;

import com.microservices.product.config.jwt.JwtProperties;
import com.microservices.product.entity.security.AuthResponse;
import com.microservices.product.feignInterface.LoginInterface;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtRequestFilter extends OncePerRequestFilter {

    JwtProperties jwt;
    LoginInterface loginInterface;

    public JwtRequestFilter(JwtProperties jwt, LoginInterface loginInterface) {
        this.jwt = jwt;
        this.loginInterface = loginInterface;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization-jwt");
        String token = (!Objects.isNull(authorizationHeader) && authorizationHeader.startsWith(jwt.getBearer())) ?
                authorizationHeader.substring(jwt.getBearer().length()+1) : null;

        UserDetails user = loginInterface.getUserDetailsByToken(new AuthResponse(token))
                .orElseThrow(() -> new UsernameNotFoundException("User with token <" + token + "> not found"));

        Authentication auth = null;
        if (null != user) {
            auth = new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
        }

        if (null != auth) {
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
