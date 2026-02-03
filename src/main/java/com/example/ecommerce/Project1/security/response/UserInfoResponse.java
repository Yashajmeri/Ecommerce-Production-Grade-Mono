package com.example.ecommerce.Project1.security.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class UserInfoResponse {

    private Long id;
    private String jwtToken;
    private String username;
    private List<String> roles;
    public UserInfoResponse(String jwtToken, String username, List<String> roles , Long id) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.roles = roles;
        this.id = id;
    }

    public UserInfoResponse(String username, List<String> roles, Long id) {
        this.username = username;
        this.roles = roles;
        this.id = id;
    }
}