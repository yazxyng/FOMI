# 로컬 개발 시작

## 준비

Node.js 24 LTS, npm, JDK 17이 필요합니다. Gradle은 별도 설치 없이 Wrapper를 사용합니다.

## 프론트

```sh
cd frontend
npm ci
cp .env.example .env
npm start
```

`.env`의 `EXPO_PUBLIC_API_URL`을 설정합니다. 웹/iOS 시뮬레이터는 `http://localhost:8080`, Android 에뮬레이터는 `http://10.0.2.2:8080`, 실제 휴대폰은 `http://개발컴퓨터의_LAN_IP:8080`을 사용합니다. 실제 폰과 컴퓨터는 같은 네트워크에 연결해야 합니다. 환경변수 수정 후 Expo를 재시작합니다.

웹 확인은 `npm run web`, 타입 검사는 `npm run typecheck`, 린트는 `npm run lint`입니다. 첫 화면의 버튼으로 서버 상태를 확인합니다. Expo Go 지원 SDK와 설치 버전이 맞아야 하며 호환되지 않으면 SDK에 맞는 개발 빌드를 사용합니다. 스토어 테스트 배포에는 Expo/Apple 계정 등 추가 준비가 필요합니다.

## 백엔드

```sh
cd backend
./gradlew bootRun
```

상태 확인: `http://localhost:8080/actuator/health` → `{"status":"UP"}`.

```sh
./gradlew test bootJar
```

`PORT`로 서버 포트를 바꿀 수 있습니다. 웹 테스트 주소가 localhost가 아닌 경우 `CORS_ALLOWED_ORIGINS`에 프론트 origin을 쉼표로 구분해서 설정합니다. 현재 CORS는 상태 확인 경로만 허용합니다.

## 외부 서비스

Supabase·Gemini·Dify는 아직 연결하지 않았습니다. 키 없이 기본 실행이 가능합니다. `.env.example`은 향후 연동에 필요한 변수 목록이며 백엔드가 `.env`를 자동으로 읽지는 않습니다. 실제 값은 IDE 또는 배포 환경변수로 주입합니다. DB·인증 구현 전에는 상태 확인 외의 사용자 API를 공개하지 않습니다.

## 검증 기록

초기 설정에서 타입 검사·린트·웹 export와 백엔드 테스트·bootJar 생성을 확인했습니다. 실제 Android/iOS 설치 테스트는 아직 하지 않았습니다. npm audit에서 Expo CLI와 린트 도구의 간접 의존성에 moderate 경고 10건이 보고되었습니다. 강제 수정은 Expo를 이전 메이저 버전으로 바꾸는 제안이 포함되어 있어 적용하지 않았습니다. 배포 전 SDK 호환성을 유지하는 업데이트를 다시 확인합니다.
