# FOMI Frontend

React Native / Expo / TypeScript 앱을 위한 폴더 구조입니다. 아직 Expo 초기화나 의존성 설치는 하지 않았으며 실행 가능한 앱은 아닙니다.

## 폴더 역할

- `app/`: Expo Router의 경로와 화면 연결. `(auth)`는 인증, `(tabs)`는 주요 탭 화면에 사용합니다. 기능 구현은 `src/features`에 두고 경로 파일은 간결하게 유지합니다.
- `src/features/`: `auth` 인증, `chat` 마음 대화, `tasks` 할 일 쪼개기, `guide` 마음·집중 사용설명서, `album` 50일 앨범, `rewards` 보상, `profile` 사용자 설정.
- 각 기능의 `components/`, `hooks/`, `api/`, `types/`: 기능 전용 화면 요소·로직·서버 요청·타입.
- `src/components/`: 여러 기능이 공유하는 UI와 레이아웃.
- `src/lib/api/`: API 클라이언트와 인증 헤더 등 공통 통신 처리.
- `src/lib/supabase/`: Supabase 클라이언트·인증 세션 처리.
- `src/hooks/`, `stores/`, `theme/`, `types/`, `utils/`: 공통 훅·상태·디자인 토큰·타입·유틸리티. 기능 전용 코드는 각 기능 안에 둡니다.
- `assets/character/`: 기존 흰색 2D 말티즈 원본을 확보한 후 저장합니다. 현재 원본은 포함되어 있지 않습니다.
- `tests/`: 여러 기능을 연결하는 테스트. 기능 단위 테스트는 해당 코드 옆에 둘 수 있습니다.

## 초기화할 때

Expo Router를 사용하는 TypeScript 프로젝트의 설정·의존성을 이 폴더에 추가합니다. 기존 디렉터리를 덮어쓰지 않도록 임시 폴더에서 생성 후 합칩니다. 패키지 관리자는 npm으로 시작하고 생성된 `package-lock.json`을 커밋합니다. 루트에 별도 JS workspace는 당장 도입하지 않습니다.

`.env.example`을 참고해 `.env`를 작성합니다. 실제 설치 기기에서 `localhost`는 휴대폰 자신을 의미하므로 API 주소에는 접근 가능한 개발 서버 주소를 사용합니다.

`EXPO_PUBLIC_` 값은 공개 정보입니다. Gemini/Dify 키와 Supabase 관리자 키를 앱에 넣지 않습니다. 공개 Supabase 키를 사용하더라도 데이터 접근 권한은 RLS와 인증으로 제한해야 합니다.
