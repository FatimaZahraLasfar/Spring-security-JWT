package org.fzl.tpjwtspring.controller;

import org.fzl.tpjwtspring.dto.AuthResponse;
import org.fzl.tpjwtspring.dto.LoginRequest;
import org.fzl.tpjwtspring.dto.RegisterRequest;
import org.fzl.tpjwtspring.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService service;

    public AuthController(AuthenticationService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticate(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @GetMapping("/profile")
    public String getProfile() {
        return "Accès autorisé ! Bienvenue dans votre espace sécurisé.";
    }
}