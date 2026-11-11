# FOMI · 포미

하루 3~4분 마음 대화와 작은 실천을 돕는 앱입니다.

- 마음 대화와 기록
- ADHD 관련 실행 부담을 낮추는 할 일 쪼개기
- 나만의 마음·집중 사용설명서
- 50일 성장 앨범과 보상

포미는 일상 기록과 실행을 지원하며 의료 진단·치료를 제공하거나 효과를 주장하지 않습니다.

## 기술 구성

| 영역 | 기술 |
| --- | --- |
| 모바일 | React Native / Expo |
| 백엔드 | Spring Boot |
| DB·인증·저장소 | Supabase |
| AI 연동 | Gemini API / Dify |

팀은 개발자 3명, 디자이너 2명으로 구성됩니다. 앱과 백엔드는 한 레포에서 관리하며 각각 배포합니다.

## 파일 구조

```text
FOMI/
├── frontend/                 # React Native · Expo 앱
│   ├── app/                  # Expo Router 화면 경로
│   ├── src/
│   │   ├── features/         # auth, chat, tasks, guide, album, rewards, profile
│   │   ├── components/       # 공통 UI · 레이아웃
│   │   ├── lib/              # API · Supabase 클라이언트
│   │   ├── hooks/
│   │   ├── stores/
│   │   ├── theme/
│   │   ├── types/
│   │   └── utils/
│   ├── assets/               # 이미지 · 폰트 · 기존 캐릭터 원본
│   └── tests/
├── backend/                  # Spring Boot 서버
│   └── src/
│       ├── main/
│       │   ├── java/com/fomi/
│       │   │   ├── domain/   # 도메인별 controller/service/repository/entity/dto
│       │   │   ├── global/   # 설정 · 보안 · 공통 예외 · 응답
│       │   │   └── infrastructure/ # Gemini · Dify · 저장소 연동
│       │   └── resources/
│       └── test/java/com/fomi/
├── supabase/                 # SQL 마이그레이션 · DB 테스트
├── docs/                     # 기획 · 디자인 · API · 테스트 · 배포 문서
└── .github/                  # PR 템플릿
```

현재는 폴더 구조와 가이드를 준비한 단계이며 실행 가능한 앱·서버는 아직 초기화하지 않았습니다. 빈 폴더의 `.gitkeep`은 실제 파일을 추가할 때 삭제할 수 있습니다.

- [프론트 구조와 준비 사항](frontend/README.md)
- [백엔드 구조와 준비 사항](backend/README.md)
- [Supabase 관리 기준](supabase/README.md)
- [문서 작성 위치](docs/README.md)

GitHub 협업은 하나의 레포에서 진행하고 앱과 서버의 빌드·배포는 각 디렉터리를 기준으로 분리합니다. 기존 커밋 scope `mobile`은 `frontend/`, `backend`는 `backend/`, `db`는 `supabase/` 변경에 사용합니다.

## 커밋 규칙

```text
type(scope): 변경 내용
```

- 제목은 한국어로 간결하게 작성합니다. 끝에 마침표는 붙이지 않습니다.
- 하나의 커밋에는 하나의 목적을 담습니다.
- scope는 `mobile`, `backend`, `db`, `docs`, `design`, `repo` 중 선택합니다. 여러 영역에 걸친 변경이면 생략할 수 있습니다.
- 관련 이슈는 필요할 때 본문에 `Refs #12` 형태로 기록합니다.

| type | 용도 |
| --- | --- |
| feat | 새 기능 |
| fix | 오류 수정 |
| refactor | 동작을 유지하는 코드 구조 개선 |
| style | 코드 서식 변경: 공백, 정렬 등 |
| design | 화면 스타일·레이아웃·디자인 자산 변경 |
| docs | 문서 작성·수정 |
| test | 테스트 추가·수정 |
| chore | 의존성·개발 도구·기타 설정 |
| ci | 자동화·빌드·배포 파이프라인 변경 |
| revert | 이전 변경 되돌리기 |

```text
feat(mobile): 마음 대화 화면 추가
fix(backend): 대화 저장 시 사용자 식별 오류 수정
design(mobile): 할 일 카드 여백 조정
docs(repo): 커밋 규칙과 PR 규칙 추가
```

## 브랜치와 PR 규칙

1. `main`을 기준으로 작업 브랜치를 만듭니다. 예: `feat/12-mood-chat`, `fix/23-login`, `docs/commit-rules`.
2. `main`에 직접 push하지 않고 PR로 변경을 반영합니다.
3. PR 하나는 한 가지 목적에 집중합니다. 진행 중이면 Draft PR을 사용합니다.
4. PR 제목도 커밋 형식인 `type(scope): 변경 내용`을 따릅니다.
5. PR 템플릿에 변경 이유·내용·확인 방법을 작성합니다. 해당 없는 항목은 삭제하거나 해당 없음을 표시합니다.
6. 작성자 외 개발자 최소 1명의 승인을 받고, 수정 요청과 논의를 해결한 뒤 병합합니다. 화면·디자인 변경은 디자이너에게도 확인을 요청합니다.
7. 변경 범위에 맞는 빌드·테스트·수동 확인을 수행합니다. 자동 검사가 설정되어 있다면 통과해야 합니다. 미확인 항목은 이유와 함께 명시합니다.
8. Squash merge를 사용하고 최종 커밋 제목도 커밋 규칙에 맞춥니다. 병합한 작업 브랜치는 삭제합니다.

### 화면 변경 확인

- 스크린샷 또는 짧은 영상을 첨부하고 기기·운영체제를 기록합니다.
- 팀 테스트 기기는 Android 4대, iPhone 1대입니다. 확인한 플랫폼과 확인하지 못한 플랫폼을 구분합니다.
- 기존의 몸통 있는 단순한 흰색 2D 말티즈 캐릭터를 유지합니다. 원본 없이 임의로 재생성하거나 교체하지 않습니다.

### 민감 정보 관리

- API 키, 비밀번호, `.env`, 실제 사용자의 마음 대화·개인정보는 커밋하거나 PR에 첨부하지 않습니다.
- 필요한 환경변수 이름은 실제 값 없이 `.env.example` 등으로 공유합니다.

> 이 문서와 PR 템플릿은 팀 협업 규칙입니다. 직접 push 제한·승인 필수 조건·Squash merge 방식은 GitHub 저장소 설정에서 별도로 적용해야 합니다. 현재 자동 검사나 브랜치 보호가 설정되었다는 뜻은 아닙니다.
