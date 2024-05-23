package com.codeuz.controller;

import com.codeuz.dto.ProfileDTO;
import com.codeuz.dto.auth.RegistrationDTO;
import com.codeuz.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody RegistrationDTO dto) {
        String body = authService.registration(dto);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/verification/{userId}")
    public ResponseEntity<String> verification(@PathVariable("userId") Integer userId) {
        String body = authService.authorizationVerification(userId);
        return ResponseEntity.ok().body(body);
    }

    @GetMapping("/login")
    public ResponseEntity<ProfileDTO> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        ProfileDTO response = authService.login(email, password);
        return ResponseEntity.ok().body(response);
    }




}
