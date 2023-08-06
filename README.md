# 원티드 프리온보딩 백엔드 인턴십 - 선발 과제

### 지원자 : 송호준

<br>

### 애플리케이션의 실행 방법(엔드포인트 호출 방법 포함)

### 데이터베이스 테이블 구조

### 구현한 API 동작을 촬영한 데모 영상 링크

### 구현 방법 및 이유에 대한 간략한 설명

### API 명세(request/response 포함)

<br>

## API 요구사항

- [x] 과제 1 : 사용자 회원가입 엔드포인트
  - [x] 이메일, 비밀번호로 회원가입 기능 구현
    - [x] 이메일 조건 : @ 포함
    - [x] 비밀번호 조건 : 8자 이상
    - [x] 비밀번호 암호화하여 저장
- [x] 과제 2 : 사용자 로그인 엔드포인트
  - [x] 이메일, 비밀번호 확인
    - [x] 이메일, 비밀번호 유효성 검사
    - [x] 회원 등록 여부 확인
  - [x] JWT 생성
  - [x] JWT 반환
- [x] 과제 3 : 새로운 게시글을 생성하는 엔드포인트
  - [x] 유저 검증 
  - [x] 게시글 생성
    - [x] 이후 게시글 정보와 함께 응답
- [x] 과제 4 : 게시글 목록을 조회하는 엔드포인트
  - [x] 게시글 목록 조회 기능
  - [x] 페이징 기능 구현
- [x] 과제 5 : 특정 게시글을 조회하는 엔드포인트
  - [x] 해당 게시글 상세 조회 기능
- [ ] 과제 6 : 특정 게시글을 수정하는 엔드포인트
  - [ ] 게시글 수정 기능
  - [ ] 수정 권한 체크 
- [ ] 과제 7 : 특정 게시글을 삭제하는 엔드포인트
  - [ ] 게시글 삭제 기능
  - [ ] 삭제 권한 체크
