package com.microservices.product.entity.security;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.microservices.product.entity.security.enums.PrivilegeType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Collection;

@Data
@NoArgsConstructor
public class Privilege {

    public Privilege(PrivilegeType pt){
        this.privilegeType = pt;
    }

    private Long id;

    @Enumerated(EnumType.STRING)
    private PrivilegeType privilegeType;

    @JsonIgnoreProperties("privileges")
    private Collection<Role> roles;
}