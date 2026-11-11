# FOMI · 포미

하루 3~4분 마음 대화와 작은 실천을 돕는 앱입니다.

- 마음 대화와 기록
- ADHD 관련 실행 부담을 낮추는 할 일 쪼개기
- 나만의 마음·집중 사용설명서
- 50일 성장 앨범과 보상

포미는 일상 기록과 실행을 지원하며 의료 진단·치료를 제공하거나 효과를 주장하지 않습니다.

<details>
<summary><strong>기술 구성</strong></summary>

| 영역 | 기술 |
| --- | --- |
| 모바일 | React Native / Expo |
| 백엔드 | Spring Boot |
| DB·인증·저장소 | Supabase |
| AI 연동 | Gemini API / Dify |

팀은 개발자 3명, 디자이너 2명으로 구성됩니다. 앱과 백엔드는 한 레포에서 관리하며 각각 배포합니다.

</details>

<details>
<summary><strong>파일 구조</strong></summary>

```text
FOMI/
├── frontend/                 # React Native · Expo 앱
│   ├── src/app/              # Expo Router 화면 경로
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

Expo 앱과 Spring Boot 서버를 초기화했습니다. [개발 환경 실행 가이드](docs/development.md)를 참고하세요. 빈 폴더의 `.gitkeep`은 실제 파일을 추가할 때 삭제할 수 있습니다.

- [프론트 구조와 준비 사항](frontend/README.md)
- [백엔드 구조와 준비 사항](backend/README.md)
- [Supabase 관리 기준](supabase/README.md)
- [문서 작성 위치](docs/README.md)

GitHub 협업은 하나의 레포에서 진행하고 앱과 서버의 빌드·배포는 각 디렉터리를 기준으로 분리합니다. 기존 커밋 scope `mobile`은 `frontend/`, `backend`는 `backend/`, `db`는 `supabase/` 변경에 사용합니다.

</details>

<details>
<summary><strong>커밋 규칙</strong></summary>

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

</details>

<details>
<summary><strong>브랜치와 PR 규칙</strong></summary>

### 브랜치 구성

| 브랜치 | 용도 |
| --- | --- |
| `main` | 리뷰와 검사를 통과한 코드를 모으는 공용 브랜치 |
| `cym`, `ksm`, `kyr` | 각 개발자가 자신의 이니셜 브랜치에서 계속 작업 |

1. 개발자는 본인의 이니셜 브랜치에서만 작업합니다. 기능마다 새 브랜치를 만들지 않으며 다른 사람의 브랜치에 직접 push하지 않습니다.
2. 작업 시작 전 최신 원격 상태를 받아오고, 본인 브랜치에 최신 `origin/main`을 merge합니다. 충돌은 본인 브랜치에서 해결한 뒤 확인합니다.
3. `main`에 직접 push하지 않고, **본인 이니셜 브랜치 → `main`** 방향으로 PR을 보냅니다.
4. PR 하나는 한 가지 목적에 집중합니다. 열린 PR에 push한 커밋도 같은 PR에 포함되므로, 관련 없는 작업은 현재 PR 병합 후 시작합니다. 진행 중이면 Draft PR을 사용합니다.
5. PR 제목은 커밋 형식인 `type(scope): 변경 내용`을 따릅니다. PR 템플릿에 변경 이유·내용·확인 방법을 작성하고 해당 없는 항목은 삭제하거나 해당 없음을 표시합니다.
6. 작성자 외 개발자 최소 1명의 승인을 받고, 수정 요청과 논의를 해결한 뒤 병합합니다. 화면·디자인 변경은 디자이너에게도 확인을 요청합니다.
7. 변경 범위에 맞는 빌드·테스트·수동 확인을 수행합니다. 설정된 자동 검사는 통과해야 합니다. 미확인 항목은 이유와 함께 명시합니다.
8. PR은 **Create a merge commit** 방식으로 병합합니다. 개인 브랜치를 반복 사용하므로 Squash merge나 Rebase merge 대신 병합 이력을 유지합니다.
9. **병합 후에도 이니셜 브랜치를 삭제하지 않습니다.** 본인 브랜치에 최신 `origin/main`을 merge한 후 다음 작업을 이어갑니다. 공유된 브랜치 이력을 강제로 덮어쓰는 force push는 하지 않습니다.

### 작업 순서 예시

아래 `kyr`는 예시입니다. 본인에게 해당하는 이니셜로 바꿔 사용합니다. 브랜치를 바꾸기 전 진행 중인 변경은 먼저 커밋하거나 보관합니다.

```sh
git fetch origin
git switch kyr
git pull --ff-only origin kyr
git merge origin/main
# 작업하고 변경 파일을 확인한 뒤 커밋
git push origin kyr
```

처음 로컬에 개인 브랜치가 없다면 `git switch --track origin/kyr`로 연결합니다. 이후 GitHub에서 base를 `main`, compare를 본인 이니셜로 선택해 PR을 만듭니다.

### 화면 변경 확인

- 스크린샷 또는 짧은 영상을 첨부하고 기기·운영체제를 기록합니다.
- 팀 테스트 기기는 Android 4대, iPhone 1대입니다. 확인한 플랫폼과 확인하지 못한 플랫폼을 구분합니다.
- 기존의 몸통 있는 단순한 흰색 2D 말티즈 캐릭터를 유지합니다. 원본 없이 임의로 재생성하거나 교체하지 않습니다.

### 민감 정보 관리

- API 키, 비밀번호, `.env`, 실제 사용자의 마음 대화·개인정보는 커밋하거나 PR에 첨부하지 않습니다.
- 필요한 환경변수 이름은 실제 값 없이 `.env.example` 등으로 공유합니다.

> 이 문서와 PR 템플릿은 팀 협업 규칙입니다. 직접 push 제한·승인 필수 조건·Merge commit 허용·브랜치 자동 삭제 해제은 GitHub 저장소 설정에서 별도로 적용해야 합니다. 현재 자동 검사나 브랜치 보호가 설정되었다는 뜻은 아닙니다.

</details>
