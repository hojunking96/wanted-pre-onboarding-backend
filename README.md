# 원티드 프리온보딩 백엔드 인턴십 - 선발 과제

### 지원자 : 송호준

<br>

## 환경 세팅

### 버전

- Java : 17
- SpringBoot : 3.1.2
- MySQL : 8.0

### Database

- MySQL을 개발(dev), 운영(prod) 환경의 DB로 사용했습니다.
- H2 DB를 테스트(test) 환경의 DB로 사용했습니다.
    - 테스트 시간을 단축하고자, In-Memory 방식으로 구현했습니다.

### 설정 파일

- application-secret.yml 파일을 사용했습니다.
    - 초기 개발이 끝난 후 값과 형식을 바꿔 gitIgnore를 통해 숨겼습니다.

### 서버

- NCP(Naver Cloud Platform) 사용하여 서버를 생성했습니다.
- ~~서버 접속용 공인 IP는 “http://118.67.133.82:8080” 으로, 도메인 없이 서버만 띄웠습니다.~~
- 서버 반납했습니다.
![스크린샷 2023-08-10 오전 3 20 25](https://github.com/hojunking96/wanted-pre-onboarding-backend/assets/99067128/6260bea2-420b-4b55-862a-915f710c04b1)
### 도커

- docker-compose 를 사용하여 MySQL, SpringBoot application을 하나의 네트워크로 구성, 배포했습니다.
#### DockerFile 코드

```dockerfile
FROM openjdk:17-jdk-alpine
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=prod","/app.jar"]
```

#### docker-compose.yml 코드

```yaml
version: '3'

services:
  mysql:
    image: mysql:8.0
    ports:
      - 3306:3306
    volumes:
      - mysql-data:/var/lib/mysql
    environment:
      MYSQL_ROOT_PASSWORD: root_password
      MYSQL_DATABASE: database
      MYSQL_USER: my_user
      MYSQL_PASSWORD: my_password
    networks:
      - spring-network

  wantedpreonboardingbackend:
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - 8080:8080
    networks:
      - spring-network

networks:
  spring-network:

volumes:
  mysql-data:
```

- docker-compose.yml 파일의 핵심 내용은 숨긴 채로 깃에 업로드했고, 서버 내에서 직접 수정 후 배포했습니다.

<br>

## 구현 공통 사항

- JWT 토큰을 사용하는 REST API를 구현했습니다.
- ResponseForm 을 만들어, 일관된 응답 형식을 클라이언트에게 제공하도록 구현했습니다.
- 성공과 실패에 해당하는 에러코드, 메세지 등을 enum을 활용하여 매칭했습니다.
    - 결과를 나타내는 code는 ‘S or F -’ + ‘3자리 숫자’ 형식으로, S - 는 성공을 의미, F - 는 실패를 의미합니다.
    - 뒤 3자리 숫자는 성공/실패를 분류하기 위해 사용됩니다.
        - 전 범위의 성공/실패의 경우 0XX의 값을 가집니다.
        - 회원 관련 성공/실패의 경우 1XX, 게시글 관련 성공/실패의 경우 2XX의 값을 가집니다.

<br>

## 데이터베이스 테이블 구조

![스크린샷 2023-08-08 오전 4 10 26](https://github.com/hojunking96/wanted-pre-onboarding-backend/assets/99067128/dd135320-a003-4797-83ed-fd1a58edb184)

<br>

## API 명세서(request/response 포함)

### PostMan API 명세서

- https://documenter.getpostman.com/view/25528463/2s9Xy2QCa6

### Swagger

- http://118.67.133.82:8080/swagger-ui/index.html#/

<br>

## 구현한 API의 동작을 촬영한 데모 영상 링크
https://drive.google.com/file/d/1rM12d3yE9TBQBu9hBKOl1Uky2ygeRo7o/view?usp=sharing

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
- [x] 과제 6 : 특정 게시글을 수정하는 엔드포인트
    - [x] 게시글 수정 기능
    - [x] 수정 권한 체크
- [x] 과제 7 : 특정 게시글을 삭제하는 엔드포인트
    - [x] 게시글 삭제 기능
    - [x] 삭제 권한 체크

<br>

## 추가 구현사항

- [x] Member TC 추가
    - [x] MemberController TC 추가
    - [x] MemberService TC 추가
- [x] Post TC 추가
    - [x] PostController TC 추가
    - [x] PostService TC 추가
