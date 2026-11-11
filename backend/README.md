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

초기 설계 기준은 Supabase Auth로 로그인하고 Spring에서 발급 토큰을 검증하는 방식입니다. `auth` 도메인에서 별도 비밀번호 DB를 만들지 않습니다. 최종 인증 방식과 접근 권한은 구현 전에 API 문서에 확정합니다.

DB 스키마 변경의 기준은 루트 `supabase/migrations/`입니다. Hibernate가 운영 스키마를 자동 변경하지 않도록 구성합니다. Spring이 DB에 직접 연결하는 경우 Supabase RLS에만 의존하지 않고 사용자별 접근 권한을 서버에서도 검증합니다.

API 키와 사용자의 마음 대화 원문은 로그에 출력하지 않습니다. 이 구조만으로 인증·권한 검증이 구현된 것은 아닙니다.
