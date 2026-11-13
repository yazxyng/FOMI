# FOMI Backend

Spring Boot / Java / Gradle 백엔드를 위한 패키지 구조입니다. Spring Initializr와 Gradle Wrapper를 구성했습니다. 실행 방법은 [개발 환경 가이드](../docs/development.md)를 참고하세요.

## 패키지 역할

- `com.fomi.domain`: 기능별 비즈니스 코드. 프론트와 같은 `auth`, `chat`, `tasks`, `guide`, `album`, `rewards`, `profile` 단위를 사용합니다.
- 각 도메인의 `controller`: HTTP 요청과 응답 처리.
- `service`: 비즈니스 로직과 트랜잭션.
- `repository`: DB 접근.
- `entity`: 영속성 모델.
- `dto`: API 요청·응답 모델. Entity를 API 응답으로 직접 노출하지 않습니다.
- `global/config`, `security`, `exception`, `response`: 공통 설정·인증 검증·예외·응답 처리.
- `infrastructure/ai/gemini`, `ai/dify`: 외부 AI 호출 어댑터. 도메인 로직과 공급자별 통신 코드를 분리합니다.
- `infrastructure/storage`: 파일 저장소 연동.
- `src/main/resources`: 애플리케이션 설정.
- `src/test/java/com/fomi`: 운영 코드 패키지 구조에 맞춘 테스트.

## 실행

[개발 환경 가이드](../docs/development.md)를 참고하세요.

## 인증과 DB 경계

회원가입·로그인·토큰 발급과 검증은 Spring에서 담당합니다. Supabase는 PostgreSQL DB로만 사용하며 Supabase Auth는 사용하지 않습니다.

DB 스키마 변경의 기준은 루트 `supabase/migrations/`입니다. Hibernate가 운영 스키마를 자동 변경하지 않도록 구성합니다. Spring이 DB에 직접 연결하는 경우 Supabase RLS에만 의존하지 않고 사용자별 접근 권한을 서버에서도 검증합니다.

API 키와 사용자의 마음 대화 원문은 로그에 출력하지 않습니다. 이 구조만으로 인증·권한 검증이 구현된 것은 아닙니다.


## VS Code에서 Supabase 연결

1. `backend/.env.example`을 `backend/.env`로 복사합니다. 이미 있다면 덮어쓰지 않습니다.
2. `.env`의 `DB_PASSWORD`에 Supabase **데이터베이스 비밀번호**를 입력합니다. API 키나 계정 로그인 비밀번호가 아닙니다. 따옴표는 넣지 않습니다.
3. `backend`에서 `./gradlew bootRun`을 실행합니다.
4. `http://localhost:8080/actuator/health`가 HTTP 200과 `{"status":"UP"}`을 반환하면 DB 상태 검사도 통과한 것입니다. 세부 연결 정보는 공개하지 않습니다.

Direct connection은 IPv6 연결이 필요할 수 있습니다. 연결 시간 초과가 나면 Supabase의 Connect → Session pooler에서 JDBC 호스트와 사용자명을 확인해 `DB_URL`, `DB_USERNAME`을 함께 변경합니다. 포트는 5432이고 `sslmode=require`를 유지합니다. 호스트는 프로젝트 화면에서 확인하며 추측하지 않습니다.

`.env`는 Git에서 제외됩니다. 프론트엔드 환경변수에 DB 비밀번호를 넣지 않습니다. Hibernate는 테이블을 자동 생성·변경하지 않습니다. 테스트는 외부 DB 대신 메모리 DB를 사용합니다.
