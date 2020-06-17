package com.microservices.product.entity.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microservices.product.entity.security.enums.RoleType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class User implements UserDetails {
    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private boolean enabled;
    private int loginAttempt;
    private boolean tokenExpired;
    private LocalDateTime lastSeen;

    @JsonIgnoreProperties("users")
    private Collection<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(Role::getRoleType)
                .map(RoleType::getRole)
                .map((s)->"ROLE_"+s)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !tokenExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !tokenExpired;
    }
}