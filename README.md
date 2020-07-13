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

#### Entity type mapping
* javax.persistence 패키지

* @Entity
  * 엔티티는 객체 세상에서 부르는 이름
  * 보통 클래스와 같은 이름을 사용하기 때문에 값을 변경하지 않음
  * 엔티티의 이름은 JQL에서 쓰임
  * name 속성으로 설정하는 것은 하이버네이트 안에서의 이름일뿐
    * 실제 테이블명도 name 속성값과 동일 (name="myAccount") 설정시 my_account 테이블 생성
    * 잘못된 설명인지, 추가 설정이 필요한지 확인 필요
  * @Table이 태깅되어 있는것과 동일

* @Table
  * 릴레이션 세상에서 부르는 이름
  * @Entity의 이름이 기본값
  * 테이블의 이름은 SQL에서 쓰임
  * 데이터베이스의 예약어(키워드)로 잡혀 있는 단어를 클래스명으로 사용하지 않아야 함
    * 사용할 경우 데이터베이스 syntax 에러 발생

* @Id
  * 엔티티의 주키를 맵핑할 때 사용
  * 자바의 모든 프리미티브 타입과 그 래퍼 타입을 사용할 수 있음
    * int, long과 같은 프리미티브 타입은 초깃값이 0, 실제 데이터에서 키 값이 0인 경우와 혼동될 수 있음
    * 래퍼 클래스 타입은 초깃값이 null이기 때문에 구분 가능
    * 따라서 래퍼 클래스 타입을 키로 사용
  * Date랑 BigDecimal, BigInteger도 사용 가능
  * 복합키를 만드는 맵핑하는 방법도 있지만 그건 논외

* @GeneratedValue
  * 주키의 생성 방법을 매핑하는 애노테이션
  * 생성 전략과 생성기를 설정할 수 있음
  * 기본 전략은 AUTO: 사용하는 DB에 따라 적절한 전략 선택
    * 데이터베이스 벤더에 따라 기본 생성 전략이 다름
    * 명시적으로 설정 가능 (기본 AUTO)
  * TABLE, SEQUENCE, IDENTITY 중 하나

* @Column
  * @Entity 어노테이션이 태깅된 객체에 모든 멤버변수에는 @Column 어노테이션이 태깅된것과 마찬가지
  * unique
  * nullable
  * length
  * columnDefinition
    * 컬럼을 벤더에 특화된 SQL 문법을 이용하여 정의할 경우 사용

* @Temporal
  * JPA 2.1까지는 Date와 Calendar만 지원
    * JPA 2.2부터 LocalDate, LocalTime, LocalDateTime 등 가능
  * TIME
  * DATE
  * TIMESTAMP (DATE + TIME)

* @Transient
  * 컬럼으로 맵핑하고 싶지 않은 멤버 변수에 사용

* application.properties(YAML) 설정
  * spring.jpa.show-sql=true
    * SQL 보기
  * spring.jpa.properties.hibernate.format_sql=true
    * 읽기 형식 지정

* hibernate ddl-auto 설정
  * update 설정시 이미 컬럼이 생성된 경우에는 unique, nullable 등 속성이나 데이터 타입을 변경하여도 적용되지 않음

#### Value type mapping
* Entity 타입과 Value 타입 구분
  * 식별자가 있어야 하는가
  * 독립적으로 존재해야 하는가

* Value 타입 종류
  * 기본 타입 (String, Date, Boolean, ...), 기본 타입의 컬렉션
  * Composite Value 타입, Composite 타입의 컬렉션
  * Collection Value 타입

* Composite Value 타입 맵핑
  * @Embeddable - 클래스 선언 시 태깅
  * @Embedded - 사용하는 클래스에서 사용시 태깅
  * @AttributeOverrides
  * @AttributeOverride
    * Address의 street 필드를 home_street 이라는 컬럼명으로 사용
    * @AttributeOverride(name = "street", column = @Column(name = "home_street"))

#### 1:N mapping
* 관계에는 항상 두 엔티티가 존재
  * 둘 중 하나는 그 관계의 주인(owning)이고 다른 쪽은 종속(non-owning)
  * 해당 관계의 반대쪽 레퍼런스를 가지고 있는 쪽이 주인

* 단방향에서의 관계의 주인은 명확함
  * 관계를 정의한 쪽이 그 관계의 주인

* 단방향 @ManyToOne
  * 기본값은 FK 생성
  * 예제에서 ``` Study ``` 객체에 ``` Account owner ``` 필드 선언 후 @ManyToOne 태깅
    * Study가 Account의 FK(owner_id)를 갖고 있음

* 단방향 @OneToMany
  * 기본값은 조인 테이블 생성
  * 예제에서 ``` Account ``` 객체에 ``` Set<Study> studies ``` 필드 선언 후 @OneToMany 태깅
    * account_studies 조인 테이블 생성

* 양방향
  * FK 가지고 있는 쪽이 오너
    * 따라서 기본값은 @ManyToOne 가지고 있는 쪽이 주인
  * 주인이 아닌쪽(@OneToMany쪽)에서 mappedBy 사용해서 관계를 맺고 있는 필드를 설정해야 함
    * mappedBy 속성으로 매핑하지 않으면 양쪽 필드에서 어노테이션 태깅해도 단방향 설정에 불과함

* 양방향
  * @ManyToOne (이쪽이 주인)
  * @OneToMany(mappedBy)
  * 주인한테 관계를 설정해야 DB에 반영됨

#### JPA Cascade
* 엔티티의 상태 변화를 전파(전이) 시키는 옵션

* 엔티티 상태
  * Transient : JPA가 모르는 상태
    * 단순 객체 생성시
    * @Transient 어노테이션이 태깅된 컬럼
  * Persistent : JPA가 관리중인 상태 (1차 캐시, Dirty Checking, Write Behind, ...)
    * 1차 캐시 : Persistent Context에 인스턴스가 저장(캐싱) 또는 그 상태
    * Dirth checking : 변경 상태 검사, 트랜잭션 내부에서 엔티티의 변경 상태를 검사하여 자동으로 데이터베이스에 반영
    * Write Behind : 엔티티의 변경된 데이터를 데이터베이스의 최대한 늦게(최대한 적은 횟수로) 반영
  * Detached : JPA가 더이상 관리하지 않는 상태
  * Removed : JPA가 관리하긴 하지만 삭제하기로 한 상태

* 예제
  * Post 객체에 ``` Set<Comment> comments ``` 필드에 cascade 옵션 적용
    * ``` @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST) ```
    * Post 객체에 comments 필드가 persistent 상태로 변경되어 데이터베이스에 저장됨

#### Fetch
* 연관 관계의 엔티티(객체 데이터)를 가져오는 방법
  * Eager
    * 연관관계에 있는 엔티티들을 지금 모두 가져옴
  * Lazy
    * 연관관계에 있는 엔티티들을 지금 가져오지 않고 getter 등 사용시에 가져옴
  * @OneToMany의 기본값은 Lazy
  * @ManyToOne의 기본값은 Eager

* Lazy 로딩으로 인한 N + 1 문제
  * 예를 들어 Post 객체 목록을 가져온 후 반복해서 Post에 속한(참조된) Comment를 출력한다고 가정했을 때
    * 전체 Post 목록 데이터를 가져오는 쿼리
    * 각 Post에 속한 Comment 데이터를 각각 Post 목록 수만큼 가져오는 쿼리 (사용 직전에 가져오는 Lazy 로딩 때문)
    * 비효율적
  * 하지만 예제에서는 Comment를 한 번에 쿼리로 가져옴

#### Query
* JPQL (HQL)
  * Java Persistence Query Language / Hibernate Query Language
  * SQL 과의 차이는 데이터베이스 테이블이 아닌, 엔티티 객체 모델 기반으로 쿼리 작성
  * JPA 또는 하이버네이트가 해당 쿼리를 SQL로 변환해서 실행함
  * 문자열이기 때문에 타입 세이프하지 않음 (오타 등)

* Criteria
  * 타입 세이프 쿼리
  
* Native Query
  * SQL 쿼리 실행
