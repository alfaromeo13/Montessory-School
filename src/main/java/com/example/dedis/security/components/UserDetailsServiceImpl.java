package com.example.dedis.security.components;

import com.example.dedis.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private  AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        // We fetch admin by username
        var admin = adminRepository.findByUsername(username);

        // Return UserDetails with username and password
        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword()) // Password is encoded and stored such in database
                .roles("ADMIN")
                .build();
    }
}