# 코딩 테스트 과제

---------------------------

Note
- 객체 지향 설계를 위해 도메인형 디렉토리 구조를 사용하였습니다.
- JPA(맵핑을 위함), devTools(auto빌드기능을 위함) 디펜던시가 추가되었습니다.
- 휴대폰 인증 로직은 랜덤으로 생성된 6자리 숫자를 DB에 저장하고, 사용자가 입력한 숫자와 비교하여 일치 여부를 확인합니다.
- 회원가입 시에는 비밀번호를 암호화하여 저장합니다.
- 내정보 조회 혹은 비밀번호 리셋시에는 DB 테이블에 해당 전화번호가 인증 기록이 존재하는지 확인한 후 진행합니다.

---------------------------
데이터베이스 설명

- 데이터베이스는 인메모리 방식의 H2를 사용하였습니다. 
인메모리 방식은 애플리케이션을 시작했을 때 메모리에 데이터베이스가 활성화됐다가 애플리케이션이 종료되면 메모리에서 내려가면서 데이터베이스 데이터들도 같이 사라지는 방식입니다. 
애플리케이션을 종료하면 모든 데이터가 사라집니다.

- 웹 콘솔을 통해 H2 데이터베이스에 접근할 수 있습니다. 프로그램 실행 후 아래 주소를 입력해 접근할 수 있습니다.
웹 콘솔 주소: http://localhost:8080/h2-console
콘솔 로그인창에서 비밀번호 입력이 없어도 Connect 버튼을 누르면 접근할 수 있습니다.
jdbc url: jdbc:h2:mem:testdb
username: sa
password: (없음)


- User 테이블
  - 사용자 정보를 담고 있는 테이블
  - 사용자 번호(loginId), 사용자 이름(realName), 사용자 전화번호(telNum),
 사용자 이메일(email), 사용자 비밀번호(password)

---------------------------

API 명세서

- 호출 Tool
  - Postman 

- 공통 사항
  - POST 요청 시에는 Body 탭에서 JSON 형식으로 데이터를 입력해야 합니다.

  
- 회원가입
  - POST http://localhost:8080/users/add

  예시 JSON
  {
  "loginId": "thetestid",
  "password": "qwqowidjqwo",
  "realName": "홍길동",
  "telNum": "01083719899",
  "email": "qowidjqwoijd@naver.com"
  }


- 로그인
  - POST http://localhost:8080/users/login

  예시 JSON
  {
  "loginId": "thetestid",
  "password": "qwqowidjqwo"
  }


- 내 정보 보기
  - GET http://localhost:8080/users/myinfo/thetestid/01083719899


- 비밀번호 재설정
  - GET http://localhost:8080/users/resetpassword/01083719899/qwdqwdqw

 
