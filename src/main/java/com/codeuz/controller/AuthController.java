package com.codeuz.controller;

import com.codeuz.dto.profile.ProfileDTO;
import com.codeuz.dto.auth.AuthDTO;
import com.codeuz.dto.auth.RegistrationDTO;
import com.codeuz.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;

    //private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);



    // Registration with email
    @PostMapping("/registration_email")
    public ResponseEntity<String> registrationWithEmail(@Valid @RequestBody RegistrationDTO dto) {
        String body = authService.registrationWithEmail(dto);
        //LOGGER.info("Registration name = {}  phone = {} ",dto.getName(),dto.getPhone());
        log.info("Registration name = {}  phone = {} ",dto.getName(),dto.getPhone());
        return ResponseEntity.ok().body(body);
    }

    // Registration with phone
    @PostMapping("/registration_phone")
    public ResponseEntity<String> registrationWithPhone(@Valid @RequestBody RegistrationDTO dto) {
        String body = authService.registrationWithPhone(dto);
        return ResponseEntity.ok().body(body);
    }

    // Authorize with email
    @GetMapping("/verification_email/{userId}")
    public ResponseEntity<String> verificationWithEmail(@PathVariable("userId") Integer userId) {
        String body = authService.authorizationWithEmail(userId);
        return ResponseEntity.ok().body(body);
    }

    // Authorize with phone
    @GetMapping("/verification_sms")
    public ResponseEntity<String> verificationWithPhone(@RequestParam("code") String code, @RequestParam("phone") String phone) {
        String response = authService.authorizationWithPhone(code, phone);
        return ResponseEntity.ok().body(response);
    }

    // Resend with email
    @GetMapping("/verification/resend/{email}")
    public ResponseEntity<String> verificationResendEmail(@PathVariable("email") String email) {
        String response = authService.authorizeResendEmail(email);
        return ResponseEntity.ok().body(response);
    }

    // Resend with phone
    @GetMapping("/verification/resend")
    public ResponseEntity<String> verificationResendPhone(@RequestParam("phone") String phone) {
        String response = authService.authorizeResendPhone(phone);
        return ResponseEntity.ok().body(response);
    }

    // Login with email
    @PostMapping("/login_email")
    public ResponseEntity<ProfileDTO> loginWithEmail(@RequestBody AuthDTO authDTO) {
        ProfileDTO response = authService.loginWithEmail(authDTO);
        return ResponseEntity.ok().body(response);
    }

    // Login with phone
    @GetMapping("/login_phone")
    public ResponseEntity<ProfileDTO> loginWithPhone(@RequestParam("phone") String phone, @RequestParam("password") String password) {
        ProfileDTO response = authService.loginWithPhone(phone, password);
        return ResponseEntity.ok().body(response);
    }




}
