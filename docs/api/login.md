# 로그인 및 인증

회원가입과 별도로 Spring이 인증합니다. Supabase Auth는 사용하지 않습니다.

## 준비

Supabase SQL Editor에서 `supabase/migrations/202611130002_create_auth_sessions.sql`을 한 번 실행하고 서버를 재시작합니다. 기존 회원 테이블은 유지합니다.

## API

- `POST /api/auth/login`: `{"username":"fomi_test01","password":"test-password123"}`
- 성공 200: `tokenType`, `accessToken`, `expiresIn`(900초), `refreshToken`, `user`(id, username, nickname).
- 틀린 비밀번호와 없는 회원은 동일하게 401 `INVALID_CREDENTIALS`를 반환합니다.
- `GET /api/users/me`: `Authorization: Bearer <accessToken>` 헤더를 사용합니다. 토큰이 없거나 만료되면 401.
- `POST /api/auth/refresh`: `{"refreshToken":"발급된 값"}`. 새로운 두 토큰을 반환하며 이전 두 토큰은 무효가 됩니다. 앱에서 두 값을 함께 교체해야 합니다.
- `POST /api/auth/logout`: 동일한 refreshToken JSON. 성공 204, 해당 로그인 세션의 두 토큰을 폐기합니다. 이미 폐기된 값도 204.

토큰은 32바이트 난수 기반 opaque token이며 JWT가 아닙니다. 서버는 SHA-256 해시만 저장합니다. Access Token은 15분, Refresh Token은 최초 로그인부터 14일이며 갱신으로 기한을 연장하지 않습니다. 재로그인은 별도 세션을 생성하고, 로그아웃은 현재 세션만 종료합니다.

앱에서는 토큰을 보안 저장소에 보관하고 운영 환경에서는 HTTPS로 전송합니다. 로그인·갱신 응답은 no-store입니다. 쿠키와 HTTP 세션을 사용하지 않는 Bearer 인증이므로 CSRF는 비활성화했습니다. 공개 경로 외에는 Spring Security 인증이 필요합니다.

## 확인

backend에서 `./gradlew test` 실행. 회원가입 회귀 테스트와 로그인·잘못된 비밀번호·미인증 접근·갱신·이전 토큰 재사용 차단·로그아웃 후 차단을 확인합니다. 테스트 DB는 H2이며 실제 Supabase 검증은 별도입니다.

운영 배포 전에는 로그인 시도 제한과 만료 세션 정리 작업을 추가해야 합니다. 소셜 로그인과 비밀번호 재설정은 이번 범위에 포함하지 않습니다.
