# LLM ThreadPool 병목 재현 및 최적화 예제

이 프로젝트는 Spring Boot 기반 환경에서 LLM API 호출 시 발생할 수 있는 **ThreadPool 병목 현상**을 재현하고, `@Async` 및 `ThreadPoolTaskExecutor`로 이를 **최적화하는 방법**을 예시로 제공합니다.

---

## 🚀 개요

대규모 LLM SaaS 서비스에서, 동시 요청량이 많아질 경우 **기존 FixedThreadPool 기반 처리 방식**은 병목을 유발할 수 있습니다.  
이 프로젝트는 아래 두 가지 케이스를 비교합니다:

- `Executors.newFixedThreadPool(2)` → 병목 발생
- `@Async + ThreadPoolTaskExecutor` → 병목 개선

---

## 🧪 테스트 API

| Endpoint | 설명 | 병목 여부 |
|----------|------|------------|
| `GET /api/llm/call` | 기존 방식 (FixedThreadPool 사용) | ❗ 병목 발생 |
| `GET /api/llm/call-optimized` | 개선된 방식 (`@Async + ThreadPoolTaskExecutor`) | ✅ 병목 완화 |

---

## ⚙️ 실행 방법

```bash
./gradlew bootRun
