# FOMI Backend

Spring Boot / Java / Gradle 백엔드를 위한 패키지 구조입니다. 아직 Spring Initializr 프로젝트, Gradle Wrapper, 애플리케이션 진입점과 실행 설정은 포함되어 있지 않습니다.

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

## 초기화할 때

Spring Initializr에서 Java / Gradle 프로젝트를 생성하고 `com.fomi`를 기본 패키지로 설정합니다. Web, Validation, Security, OAuth2 Resource Server, Data JPA, PostgreSQL Driver, Actuator를 초기 의존성 후보로 검토합니다. 사용하는 Java·Spring Boot 버전은 초기화 시 지원 범위를 확인해 확정하고 Gradle Wrapper를 커밋합니다.

`.env.example`은 변수 목록입니다. Spring Boot가 이 파일을 자동으로 읽는다는 뜻은 아닙니다. IDE 실행 설정이나 배포 서비스에서 환경변수를 주입하고 `application.yml`에서 매핑해야 합니다. 배포 시 서버 포트는 플랫폼의 `PORT`에 연결합니다.

## 인증과 DB 경계

초기 설계 기준은 Supabase Auth로 로그인하고 Spring에서 발급 토큰을 검증하는 방식입니다. `auth` 도메인에서 별도 비밀번호 DB를 만들지 않습니다. 최종 인증 방식과 접근 권한은 구현 전에 API 문서에 확정합니다.

DB 스키마 변경의 기준은 루트 `supabase/migrations/`입니다. Hibernate가 운영 스키마를 자동 변경하지 않도록 구성합니다. Spring이 DB에 직접 연결하는 경우 Supabase RLS에만 의존하지 않고 사용자별 접근 권한을 서버에서도 검증합니다.

API 키와 사용자의 마음 대화 원문은 로그에 출력하지 않습니다. 이 구조만으로 인증·권한 검증이 구현된 것은 아닙니다.
