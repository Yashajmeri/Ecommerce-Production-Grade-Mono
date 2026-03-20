package com.example.ecommerce.Project1.security.response;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Represents the user info response component.
 */
@Data
@NoArgsConstructor
public class UserInfoResponse {

    private Long id;
    private String jwtToken;
    private String username;
    private List<String> roles;
    /**
     * Creates a new `UserInfoResponse` instance.
     * @param jwtToken the jwtToken value.
     * @param username the username value.
     * @param roles the roles value.
     * @param id the id value.
     */
    public UserInfoResponse(String jwtToken, String username, List<String> roles , Long id) {
        this.jwtToken = jwtToken;
        this.username = username;
        this.roles = roles;
        this.id = id;
    }

    /**
     * Creates a new `UserInfoResponse` instance.
     * @param username the username value.
     * @param roles the roles value.
     * @param id the id value.
     */
    public UserInfoResponse(String username, List<String> roles, Long id) {
        this.username = username;
        this.roles = roles;
        this.id = id;
    }
}
