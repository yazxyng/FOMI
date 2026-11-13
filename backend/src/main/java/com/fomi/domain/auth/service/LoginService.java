package com.fomi.domain.auth.service;
import com.fomi.domain.auth.dto.*;
import com.fomi.domain.auth.entity.AuthSession;
import com.fomi.domain.auth.repository.AuthSessionRepository;
import com.fomi.domain.profile.entity.User;
import com.fomi.domain.profile.repository.UserRepository;
import java.time.*;
import java.security.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
@Service
public class LoginService {
 private final UserRepository users;
 private final AuthSessionRepository sessions;
 private final PasswordEncoder encoder;
 private final String dummyHash;
 private final SecureRandom random = new SecureRandom();
 public LoginService(UserRepository users, AuthSessionRepository sessions, PasswordEncoder encoder) {
  this.users=users; this.sessions=sessions; this.encoder=encoder;
  dummyHash=encoder.encode(UUID.randomUUID().toString());
 }
 @Transactional public LoginResponse login(LoginRequest request) {
  var user=users.findByUsername(request.username());
  boolean matches=encoder.matches(request.password(),user.map(User::getPasswordHash).orElse(dummyHash));
  if (user.isEmpty() || !matches) throw new InvalidCredentials();
  return issue(new AuthSession(user.get(),Instant.now().plus(Duration.ofDays(14))));
 }
 private LoginResponse issue(AuthSession session) {
  String access=token(), refresh=token();
  session.rotate(hash(access),hash(refresh),Instant.now().plusSeconds(900));
  sessions.saveAndFlush(session);
  return new LoginResponse("Bearer",access,900,refresh,profile(session.getUser()));
 }
 @Transactional public LoginResponse refresh(String token) {
  var session=sessions.lockByRefreshHash(hash(token)).orElseThrow(InvalidCredentials::new);
  if (!session.getRefreshExpiresAt().isAfter(Instant.now())) throw new InvalidCredentials();
  return issue(session);
 }
 @Transactional public void logout(String token) {
  sessions.lockByRefreshHash(hash(token)).ifPresent(sessions::delete);
 }
 @Transactional(readOnly=true) public Optional<UUID> authenticate(String token) {
  if (!token.matches("[A-Za-z0-9_-]{43}")) return Optional.empty();
  return sessions.findByAccessHash(hash(token)).filter(s->s.getAccessExpiresAt().isAfter(Instant.now()))
    .filter(s->s.getRefreshExpiresAt().isAfter(Instant.now())).map(s->s.getUser().getId());
 }
 @Transactional(readOnly=true) public SignupResponse me(UUID id) { return profile(users.findById(id).orElseThrow(InvalidCredentials::new)); }
 private SignupResponse profile(User user) { return new SignupResponse(user.getId(),user.getUsername(),user.getNickname()); }
 private String token() { byte[] bytes=new byte[32]; random.nextBytes(bytes); return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes); }
 public static String hash(String token) {
  try { return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(token.getBytes(StandardCharsets.UTF_8))); }
  catch (NoSuchAlgorithmException e) { throw new IllegalStateException(e); }
 }
 public static class InvalidCredentials extends RuntimeException {}
}
