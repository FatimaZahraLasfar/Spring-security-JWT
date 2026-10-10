package org.fzl.tpjwtspring.service;

import org.fzl.tpjwtspring.dto.AuthResponse;
import org.fzl.tpjwtspring.dto.LoginRequest;
import org.fzl.tpjwtspring.dto.RegisterRequest;
import org.fzl.tpjwtspring.entity.AppUser;
import org.fzl.tpjwtspring.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CustomerUserDetailsService userDetailsService;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager, CustomerUserDetailsService userDetailsService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    public AuthResponse register(RegisterRequest request) {
        var user = new AppUser();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER"); // Rôle par défaut

        repository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        var jwtToken = jwtService.generateToken(userDetails);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.email());
        var jwtToken = jwtService.generateToken(userDetails);
        return new AuthResponse(jwtToken);
    }
}