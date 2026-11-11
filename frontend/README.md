# FOMI Frontend

React Native / Expo / TypeScript 앱을 위한 폴더 구조입니다. Expo 프로젝트를 초기화했습니다. 실행 방법은 [개발 환경 가이드](../docs/development.md)를 참고하세요.

## 폴더 역할

- `src/app/`: Expo Router의 경로와 화면 연결. `(auth)`는 인증, `(tabs)`는 주요 탭 화면에 사용합니다. 기능 구현은 `src/features`에 두고 경로 파일은 간결하게 유지합니다.
- `src/features/`: `auth` 인증, `chat` 마음 대화, `tasks` 할 일 쪼개기, `guide` 마음·집중 사용설명서, `album` 50일 앨범, `rewards` 보상, `profile` 사용자 설정.
- 각 기능의 `components/`, `hooks/`, `api/`, `types/`: 기능 전용 화면 요소·로직·서버 요청·타입.
- `src/components/`: 여러 기능이 공유하는 UI와 레이아웃.
- `src/lib/api/`: API 클라이언트와 인증 헤더 등 공통 통신 처리.
- `src/lib/supabase/`: Supabase 클라이언트·인증 세션 처리.
- `src/hooks/`, `stores/`, `theme/`, `types/`, `utils/`: 공통 훅·상태·디자인 토큰·타입·유틸리티. 기능 전용 코드는 각 기능 안에 둡니다.
- `assets/character/`: 기존 흰색 2D 말티즈 원본을 확보한 후 저장합니다. 현재 원본은 포함되어 있지 않습니다.
- `tests/`: 여러 기능을 연결하는 테스트. 기능 단위 테스트는 해당 코드 옆에 둘 수 있습니다.

## 실행

[개발 환경 가이드](../docs/development.md)를 참고하세요.
