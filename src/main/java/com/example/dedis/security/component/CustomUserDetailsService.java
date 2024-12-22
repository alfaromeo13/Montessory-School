package com.example.dedis.security.component;

import com.example.dedis.entities.Admin;
import com.example.dedis.repositories.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private  final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Admin admin = adminRepository.findByUsername(username);

        if(admin == null) throw new UsernameNotFoundException("User not exists by that Username");

        // We fetch admin by username
        return new org.springframework.security.core.userdetails.User(
                admin.getUsername(),
                admin.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
        );
    }
}