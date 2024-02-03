package com.example.platform.jwt.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class SignupRequest {

    private String username;
    private String password;
    private String email;
    private Set<String> roles;
}
