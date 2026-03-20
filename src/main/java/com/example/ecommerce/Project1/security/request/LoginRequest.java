package com.example.ecommerce.Project1.security.request;


import jakarta.validation.constraints.NotBlank;

/**
 * Represents the login request component.
 */
public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    /**
     * Returns the password.
     * @return the password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * @param password the password value.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the username.
     * @return the username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username.
     * @param username the username value.
     */
    public void setUsername(String username) {
        this.username = username;
    }


}
