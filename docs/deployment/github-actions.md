# GitHub Actions 자동 검사

`.github/workflows/ci.yml`은 main 대상 PR, main push, 수동 실행에서 동작합니다.

- `Frontend checks`: npm ci → 타입 검사 → 린트 → 웹 번들 생성
- `Backend checks`: Java 17 → Gradle 테스트 → 실행 JAR 생성

실제 DB 비밀번호, AI 키, Expo 계정 없이 실행합니다. 앱스토어 빌드나 배포는 하지 않습니다. 프론트의 개인 `.env`는 GitHub에 전달되지 않습니다.

## GitHub에 반영한 후

1. 저장소 Actions 탭에서 CI 실행 결과를 확인합니다. 워크플로가 비활성화되어 있다면 Settings → Actions → General에서 공식 actions 사용을 허용합니다.
2. main 보호를 적용하려면 Settings → Rules → Rulesets에서 main을 대상으로 하는 branch ruleset을 만듭니다.
3. Require a pull request before merging을 활성화하고 승인 수를 1명으로 정합니다.
4. Require status checks to pass를 켜고 `Frontend checks`, `Backend checks`를 선택합니다. 검사가 한 번 실행된 후 목록에 나타날 수 있습니다.
5. Ruleset enforcement를 Active로 저장합니다.

이 파일 생성만으로 GitHub 저장소 설정이나 main 보호가 바뀌지는 않습니다. 웹 export는 모바일 네이티브 빌드 및 실제 기기 테스트를 대체하지 않습니다.
