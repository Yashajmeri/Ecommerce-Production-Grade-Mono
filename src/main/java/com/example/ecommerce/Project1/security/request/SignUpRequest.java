package com.example.ecommerce.Project1.security.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpRequest {
    @NotBlank
    @Size(min = 3,max = 20)
    private String username;
    @NotBlank
    @Email
    @Size(max = 50)
    private String email;
    private Set<String> roles;
    @NotBlank
    @Size(min = 6,max = 20)
    private String password;


}
