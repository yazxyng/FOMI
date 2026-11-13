package com.fomi.domain.auth.dto;
import jakarta.validation.constraints.*;
public record SignupRequest(
    @NotNull @Pattern(regexp = "[a-z0-9_]{4,20}", message = "아이디는 영문 소문자, 숫자, 밑줄 4~20자로 입력해 주세요.") String username,
    String nickname,
    @NotNull @Size(min = 10, max = 64, message = "비밀번호는 10~64자로 입력해 주세요.") String password,
    @NotNull @Size(min = 10, max = 64) String passwordConfirm
) {
    @Override public String toString() { return "SignupRequest[REDACTED]"; }
}
