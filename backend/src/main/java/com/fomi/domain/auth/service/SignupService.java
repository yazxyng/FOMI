package com.fomi.domain.auth.service;
import com.fomi.domain.auth.dto.*;
import com.fomi.domain.profile.entity.User;
import com.fomi.domain.profile.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class SignupService {
    private final UserRepository users;
    private final PasswordEncoder encoder;
    public SignupService(UserRepository users, PasswordEncoder encoder) { this.users = users; this.encoder = encoder; }
    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (!request.password().equals(request.passwordConfirm()))
            throw new IllegalArgumentException("비밀번호가 일치하지 않아요.");
        String nickname = request.nickname() == null ? "" : request.nickname().strip();
        if (nickname.isEmpty()) nickname = "포미친구";
        if (nickname.length() > 12) throw new IllegalArgumentException("닉네임은 12자 이하로 입력해 주세요.");
        if (users.existsByUsername(request.username())) throw new DuplicateUsernameException();
        User user = users.saveAndFlush(new User(request.username(), nickname, encoder.encode(request.password())));
        return new SignupResponse(user.getId(), user.getUsername(), user.getNickname());
    }
    public static class DuplicateUsernameException extends RuntimeException {}
}
