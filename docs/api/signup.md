# 회원가입 API

`POST /api/auth/signup` — 인증 없이 호출합니다. 로그인과 토큰 발급은 별도 구현 예정입니다.

```json
{"username":"fomi_01","nickname":"예령","password":"example-password","passwordConfirm":"example-password"}
```

- 아이디: 소문자·숫자·밑줄 4~20자, 중복 불가.
- 닉네임: 앞뒤 공백 제거, 최대 12자. 생략·null·빈 값은 `포미친구`. 중복 허용.
- 비밀번호: 10~64자, 공백을 제거하지 않음. PBKDF2 해시로 저장.
- 성공: 201, `id`, `username`, `nickname` 반환. 자동 로그인 안 함.
- 입력 오류: 400 `INVALID_INPUT`, 중복 아이디: 409 `DUPLICATE_USERNAME`.

## DB 준비 및 실행

Supabase SQL Editor에서 `supabase/migrations/202611130001_create_users.sql` 내용을 한 번 실행한 후 백엔드를 재시작합니다. 기존 users 테이블이 있다면 먼저 구조를 비교하고, 삭제하거나 덮어쓰지 않습니다. 클라이언트의 테이블 접근은 RLS 및 권한 회수로 차단합니다.

VS Code 터미널에서 backend 위치 기준:

```bash
./gradlew test
./gradlew bootRun
```

회원가입 확인은 VS Code의 REST 클라이언트 또는 API 도구로 위 JSON을 전송합니다. 실제 비밀번호를 요청 예제나 Git에 저장하지 않습니다. 로그인은 아직 구현하지 않았으므로 후속 작업에서 로그인 전용 API와 토큰 처리를 추가해야 합니다.
