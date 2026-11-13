package com.fomi.domain.auth.dto;
public record LoginResponse(String tokenType, String accessToken, long expiresIn, String refreshToken, SignupResponse user) {
 @Override public String toString() { return "LoginResponse[REDACTED]"; }
}
