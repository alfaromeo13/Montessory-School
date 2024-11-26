package com.example.dedis.security;

import com.example.dedis.entities.Admin;
import com.example.dedis.repositories.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        // We fetch admin by username
        Admin admin = adminRepository.findByUsername(username);

        if (admin == null) {
            throw new UsernameNotFoundException("Admin not found with username: " + username);
        }

        // Return UserDetails with username and password
        return User.builder()
                .username(admin.getUsername())
                .password(admin.getPassword()) // Password should be encoded in the database
                .build();
    }
}