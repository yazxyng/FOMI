package com.fomi.domain.auth.controller;
import com.fomi.domain.auth.dto.*;
import com.fomi.domain.auth.service.LoginService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
@RestController @RequestMapping("/api/auth")
public class LoginController {
 private final LoginService service;
 public LoginController(LoginService service) { this.service=service; }
 @PostMapping("/login") public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest body) {
  return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(service.login(body));
 }
 @PostMapping("/refresh") public ResponseEntity<LoginResponse> refresh(@Valid @RequestBody RefreshRequest body) {
  return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(service.refresh(body.refreshToken()));
 }
 @PostMapping("/logout") @ResponseStatus(HttpStatus.NO_CONTENT)
 public void logout(@Valid @RequestBody RefreshRequest body) { service.logout(body.refreshToken()); }
}
