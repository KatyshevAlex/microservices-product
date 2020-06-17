package com.microservices.product.feignInterface;

import com.microservices.product.entity.security.AuthResponse;
import com.microservices.product.entity.security.User;
import org.springframework.cloud.openfeign.FeignClient;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(name = "login-service")
@Service
@RequestMapping("/login")
public interface LoginInterface {

    @RequestMapping(method = RequestMethod.POST, value = "/get-user-by-token")
    Optional<User> getUserDetailsByToken(@RequestBody AuthResponse accessToken);

}
