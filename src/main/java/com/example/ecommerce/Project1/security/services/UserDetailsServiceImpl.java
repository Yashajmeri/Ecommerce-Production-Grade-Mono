package com.example.ecommerce.Project1.security.services;

import com.example.ecommerce.Project1.model.User;
import com.example.ecommerce.Project1.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Represents the user details service impl component.
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Loads user by username.
     * @param username the username value.
     * @return the result of load user by username.
     * @throws UsernameNotFoundException if the operation cannot be completed.
     */
    @Override
    @Transactional  // success ? complete task : roll back
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User Not Found with the user name: "+ username));


        return UserDetailsImpl.build(user);
    }
}
