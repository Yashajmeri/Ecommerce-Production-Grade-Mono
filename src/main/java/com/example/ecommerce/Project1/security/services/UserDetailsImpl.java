package com.example.ecommerce.Project1.security.services;

import com.example.ecommerce.Project1.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents the user details impl component.
 */
@Data
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;
    private Long id;



    private String username;
    private String email;
    @JsonIgnore // this ensures that password is not serialized in the JSON response
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    /**
     * Creates a new `UserDetailsImpl` instance.
     * @param id the id value.
     * @param username the username value.
     * @param email the email value.
     * @param password the password value.
     * @param authorities the authorities value.
     */
    public UserDetailsImpl(Long id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }
    /**
     * Executes build.
     * @param user the user value.
     * @return the result of build.
     */
    public  static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role->new SimpleGrantedAuthority(role.getRoleName().name())).collect(Collectors.toList());
        return new UserDetailsImpl(user.getUserId(), user.getUsername(), user.getEmail(),user.getPassword(),authorities);
    }
    /**
     * Returns the authorities.
     * @return the authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    /**
     * Returns the password.
     * @return the password.
     */
    @Override
    public String getPassword() {
        return password;
    }

    /**
     * Returns the username.
     * @return the username.
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Checks whether account non expired.
     * @return whether the check passed.
     */
    @Override
    public boolean isAccountNonExpired() {
        return  true;
    }

    /**
     * Checks whether account non locked.
     * @return whether the check passed.
     */
    @Override
    public boolean isAccountNonLocked() {
        return  true;
    }

    /**
     * Checks whether credentials non expired.
     * @return whether the check passed.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return  true;
    }

    /**
     * Checks whether enabled.
     * @return whether the check passed.
     */
    @Override
    public boolean isEnabled() {
        return  true;
    }
    /**
     * Executes equals.
     * @param o the o value.
     * @return the result of equals.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return id.equals(user.id);
    }
}
