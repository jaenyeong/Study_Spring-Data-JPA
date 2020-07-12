# Study_Spring-Data-JPA
### 인프런 스프링 데이터 JPA (백기선)
https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%8D%B0%EC%9D%B4%ED%84%B0-jpa/dashboard
-----

## [Settings]
#### Project Name
* Study_Spring-data-jpa
#### java
* zulu jdk 11
#### gradle
* IDEA gradle wrapper
#### Spring boot
* 2.3.1
#### Docker & postgreSql
* 2.3.0.3(45519)
* 도커로 Postgres 실행 전 기존 실행되고 있는 Postgres 서버 종료
  * port 충돌 또는 기존 실행되고 있는 Postgres 서버로 연결되면 에러 발생 가능성
  * port 확인
    * lsof -i :{portNumber}
  * brew services stop postgresql
  * pg_ctl -D /usr/local/var/postgres stop
* 도커 현재 프로세스 확인
  * docker ps
* 도커로 postgreSql 실행
  * docker run -p 5432:5432 -e POSTGRES_PASSWORD=1234 -e POSTGRES_USER=jaenyeong -e \
    POSTGRES_DB=springdatajpa --name postgres_jpa -d postgres
    * -p : 포트 매핑
      * 호스트 포트 : 컨테이너 포트
    * -e : 환경 변수
    * --name : 컨테이너명
    * -d : 백그라운드에서 데몬으로 실행
* 도커 컨테이너 외부에서 명령 실행
  * docker exec -i -t postgres_jpa bash
    * -i : 인터랙티브 모드
    * -t : 타겟이 되는 컨테이너 (실행할 명령을 수행하는 컨테이너)
* 접속 후 사용자 변경 (기본 root)
  * su - postgres
* postgreSql 실행
  * psql springdatajpa
  * 에러 발생시 아래 방법으로 시도 (postgres라는 이름으로 접속을 시도하게 되어 문제가 발생하는 걸로 보임)
    * psql --username jaenyeong --dbname springdatajpa
* 데이터베이스 조회
  * \list
* 테이블 조회
  * \dt
* 쿼리
  * ``` SELECT * FROM account; ```
#### postgreSql driver
* 42.2.14
-----

## [Contents]

### 기존 데이터베이스 사용 문제점
* 실행 비용(리소스), 커넥션 생성 비용
* SQL은 데이터베이스 벤더마다 다를 수 있음
* 스키마 변경시 데이터베이스 의존 코드가 많이 변경됨
* 중복, 반복코드 많음
* 데이터를 읽어오는 시점이 애매함

### ORM (Object Relation Mapping)

### JPA
