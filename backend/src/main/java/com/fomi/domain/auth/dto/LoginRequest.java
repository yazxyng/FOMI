package com.fomi.domain.auth.dto;
import jakarta.validation.constraints.*;
public record LoginRequest(@NotBlank @Size(max=20) String username, @NotNull @Size(min=1,max=64) String password) {
 @Override public String toString() { return "LoginRequest[REDACTED]"; }
}
