# Board UI (Spring Boot + Thymeleaf)

이 ZIP은 **화면 리소스만** 포함합니다.
- templates (Thymeleaf)
- static (CSS/JS)

## 바로 작동(SSR 기준)
JS(fetch)가 없어도 **기본 form submit**으로 동작하도록 되어 있습니다.
- 저장/수정: POST + (수정 시) hidden `_method=put`
- 삭제: POST + hidden `_method=delete`

> 위 방식은 Spring MVC의 HiddenHttpMethodFilter가 필요합니다.
Spring Boot 기본 설정에서 사용 가능한 경우가 많지만, 환경에 따라 아래 설정을 추가하세요.

### application.yml (권장)
spring:
  mvc:
    hiddenmethod:
      filter:
        enabled: true

## JSON API가 있으면 (선택)
JS는 `fetch`로 JSON API를 **시도**하고, 실패하면 자동으로 form submit로 폴백합니다.
따라서 API가 없어도 렌더/저장은 기본적으로 됩니다.

## 템플릿에서 기대하는 Model 값(예시)
- posts/view.html: `post`
  - id, title, author, content, createdAt, viewCount, status, tags
- posts/form.html: `mode` ('new' or 'edit'), `post`(edit일 때)
- admin/batch-status.html: `batchRows`
- admin/batch-dashboard.html:
  - trendLabels, trendSuccess, trendFail, durationLabels, durationValues
- admin/users/list.html: `admins`
- admin/users/form.html: `mode`, `admin`

## 링크/라우팅
사이드바 링크는 기본값으로 아래를 가정합니다.
- /posts/1, /posts/new
- /admin/batch/status, /admin/batch/dashboard
- /admin/users

프로젝트에 맞게 templates/common/layout.html 의 href만 바꾸면 됩니다.
