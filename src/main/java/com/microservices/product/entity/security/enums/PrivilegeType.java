package com.microservices.product.entity.security.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum PrivilegeType {
    READ,
    WRITE,
    DELETE
}