package com.example.ecommerce.Project1.util;

import com.example.ecommerce.Project1.model.User;
import com.example.ecommerce.Project1.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Represents the auth util component.
 */
@Component
@RequiredArgsConstructor
public class AuthUtil {
    private final UserRepository userRepository;
    /**
     * Executes logged in email.
     * @return the result of logged in email.
     */
    public String loggedInEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return user.getEmail();
    }
    /**
     * Executes logged in user id.
     * @return the result of logged in user id.
     */
    public Long loggedInUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return user.getUserId();

    }
    /**
     * Executes logged in user.
     * @return the result of logged in user.
     */
    public User loggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
}
