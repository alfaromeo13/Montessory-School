package com.example.dedis.services;

import com.example.dedis.dto.PasswordResetDTO;
import com.example.dedis.entities.Admin;
import com.example.dedis.repositories.AdminRepository;
import com.example.dedis.security.dto.LoginDTO;
import com.example.dedis.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    private final MailService mailService;
    private final CacheManager cacheManager;
    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public String login(LoginDTO loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenProvider.generateToken(authentication);
    }

    public void sendResetMail() {
        String resetCode = RandomString.make(8);
        Cache cache = cacheManager.getCache("resetCode");
        cache.put(1L, resetCode);
        mailService.sendResetPasswordMail(resetCode);
    }

    @Transactional
    public boolean changePassword(PasswordResetDTO passwordResetDTO) {
        Cache cache = cacheManager.getCache("resetCode");

        Cache.ValueWrapper cacheWrapper = cache.get(1L);

        if(cacheWrapper == null) return false;

        String cachedCode = (String) cacheWrapper.get();

        if(!passwordResetDTO.getCode().equals(cachedCode)) return false;

        cache.evict(1L);

        Admin admin = adminRepository.findById(1L).orElse(null);
        admin.setPassword(passwordEncoder.encode(passwordResetDTO.getPassword()));

        return true;
    }
}