package com.fomi.domain.auth.controller;
import com.fomi.domain.auth.dto.*;
import com.fomi.domain.auth.service.SignupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class SignupController {
    private final SignupService service;
    public SignupController(SignupService service) { this.service = service; }
    @PostMapping("/signup") @ResponseStatus(HttpStatus.CREATED)
    public SignupResponse signup(@Valid @RequestBody SignupRequest request) { return service.signup(request); }
}
