package com.fomi.domain.profile.controller;
import com.fomi.domain.auth.dto.SignupResponse;
import com.fomi.domain.auth.service.LoginService;
import java.util.UUID;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController
public class ProfileController {
 private final LoginService service;
 public ProfileController(LoginService service) { this.service=service; }
 @GetMapping("/api/users/me") public SignupResponse me(@AuthenticationPrincipal UUID id) { return service.me(id); }
}
