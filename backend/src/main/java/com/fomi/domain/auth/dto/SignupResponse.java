package com.fomi.domain.auth.dto;
import java.util.UUID;
public record SignupResponse(UUID id, String username, String nickname) {}
