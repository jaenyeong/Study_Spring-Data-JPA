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
* 2.1.2
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
* ORM은 객체와 테이블 사이에 매핑 정보를 기술한 메타데이터를 사용, 객체 데이터를 데이터베이스에 자동으로 영속화 시켜주는 기술

#### 도메인 모델 사용 이유
* 객체 지향 프로그래밍 (디자인패턴 포함, 중복 코드 제거)
* 비즈니스 로직 작성에 집중
* 테스트 용이

#### 패러다임 불일치
* granularity(밀도)
  * 객체
    * 다양한 크기의 객체 생성 가능
    * 커스텀한 타입 생성이 쉬움
  * 릴레이션
    * 테이블
    * 기본 데이터 타입
    * UDT(user defined type) 추천하지 않음
* Subtype(하위타입)
  * 객체
    * 상속구조 생성 용이
    * 다형성
  * 릴레이션
    * 테이블 상속이 없음 (구현해도 표준 기술이 아님)
    * 다형적인 관계 표현방법 없음
* Identity(식별성)
  * 객체
    * 레퍼런스 동일성 (==)
    * 인스턴스 동일성 (equals();)
  * 릴레이션
    * Primary Key (주 키)
* Association(관계)
  * 객체
    * 객체 레퍼런스로 관계 표현
    * 다대다 관계 가능
  * 릴레이션
    * Foreign Key (외래키)로 관계 표현
    * 다대다 관계를 만들지 못함
      * 조인, 링크 테이블을 이용하여 두 개의 1대다 관계로 표현해야 함
* Data Navigation(네비게이션)
  * 객체
    * 레퍼런스를 이용 다른 객체로 이동 가능
    * 컬렉션 순회 가능
  * 릴레이션
    * 릴레이션 데이터 조회 비효율적, 요청이 적을수록 효과적
    * 따라서 join을 사용하지만 이또한 문제
    * lazy loading (n + 1 select)

### JPA

#### 초기 설정
* 프로젝트 생성 및 의존성 추가
  * 스프링부트 스타터 추가
  * 스프링부트 스타터 JPA 추가
* application.properties(YAML) 설정
  * spring datasource 설정 - 접속 URL, 계정 정보
  * hibernate ddl-auto 설정
    * create 설정 (개발시에만 사용, 운영에선 사용하지 말 것)
    * validate (운영시 설정)
    * update 설정은 컬럼, 데이터가 추가되지만 삭제나 이름 변경이 제대로 되지 않아 스키마 또는 데이터가 지저분해짐
* 경고 제거
  * Method org.postgresql.jdbc.PgConnection.createClob() is not yet implemented.
  * 설정 : spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation= true
