package com.fomi.global.exception;
import com.fomi.domain.auth.service.SignupService.DuplicateUsernameException;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(com.fomi.domain.auth.service.LoginService.InvalidCredentials.class)
    ResponseEntity<?> credentials() { return ResponseEntity.status(401).body(Map.of("code", "INVALID_CREDENTIALS", "message", "아이디 또는 인증 정보를 확인해 주세요.")); }

    @ExceptionHandler(DuplicateUsernameException.class)
    ResponseEntity<?> duplicate() { return ResponseEntity.status(409).body(Map.of("code", "DUPLICATE_USERNAME", "message", "이미 사용 중인 아이디예요.")); }
    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<?> integrity(DataIntegrityViolationException error) {
        Throwable cause = error;
        while (cause != null) {
            if (cause instanceof org.hibernate.exception.ConstraintViolationException constraint
                    && "uk_users_username".equalsIgnoreCase(constraint.getConstraintName())) return duplicate();
            cause = cause.getCause();
        }
        return ResponseEntity.internalServerError().body(Map.of("code", "INTERNAL_ERROR", "message", "요청을 처리하지 못했어요."));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> invalid(IllegalArgumentException error) { return ResponseEntity.badRequest().body(Map.of("code", "INVALID_INPUT", "message", error.getMessage())); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> validation(MethodArgumentNotValidException error) {
        var fields = new java.util.LinkedHashMap<String, String>();
        error.getBindingResult().getFieldErrors().forEach(e -> fields.putIfAbsent(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(Map.of("code", "INVALID_INPUT", "message", "입력값을 확인해 주세요.", "fields", fields));
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity<?> unreadable() { return ResponseEntity.badRequest().body(Map.of("code", "INVALID_INPUT", "message", "요청 형식을 확인해 주세요.")); }
}
