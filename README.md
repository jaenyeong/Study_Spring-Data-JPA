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
    * ``` @OneToMany(mappedBy = "webPost", cascade = CascadeType.PERSIST) ```
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

#### 스프링 Data JPA
* JpaRepository<Entity, Id> 인터페이스
  * JpaRepository 인터페이스를 구현하면 클래스에 @Repository가 없어도 빈으로 등록해 줌

* @EnableJpaRepositories
  * 스프링 부트를 사용하지 않았다면 @Configuration가 태깅된 클래스(설정 클래스)에 @EnableJpaRepositories를 태깅했어야 함
  * 동작 원리 핵심

* 동작 원리
  * 시작은 @Import(JpaRepositoriesRegistrar.class)
  * 핵심은 ImportBeanDefinitionRegistrar 인터페이스

#### 정리
* SQL 확인
  * logging.level.org.hibernate.SQL=debug
    * spring.jpa.show-sql=true 와 동일함
  * logging.level.org.hibernate.type.descriptor.sql=trace

### 스프링 Data JPA 활용

#### 파트
* Spring Data
  * SQL & NoSQL 저장소 지원 프로젝트의 묶음(집합)
* Spring Data Common
  * 여러 저장소 프로젝트의 공통적인 기능 지원(제공)
* Spring Data Rest
  * 저장소의 데이터를 하이퍼미디어 기반 HTTP 리소스로(REST API로) 제공하는 프로젝트
* Spring Data JPA
  * Spring Data에 속한 프로젝트 중 하나
  * Spring Data Common이 제공하는 기능에 JPA 관련 기능 추가

### 스프링 Data Common

#### Repository
* JpaRepository
  * Spring Data JPA에서 지원하는 인터페이스
  * PagingAndSortingRepository 인터페이스를 구현, PagingAndSortingRepository 부터는 Spring Data Common에 속함
  * Repository < CrudRepository < PagingAndSortingRepository < JpaRepository

* 테스트
  * @DataJpaTest는 트랜잭션인데 테스트에서는 모두 롤백처리 하기 때문에 애당초 데이터 삽입 쿼리를 날리지 않음
    * @DataJdbcTest와 혼동하지 말 것
  * 따라서 삽입 쿼리 확인은 테스트에 @Rollback(false) 어노테이션 태깅할 것
  * 테스트시 Postgres 데이터베이스를 사용하지 않고 H2 데이터베이스를 사용

#### Repository Interface 정의
* @RepositoryDefinition
  * Repository 인터페이스로 공개할 메소드를 직접 일일이 정의할 때 사용
* @NoRepositoryBean
  * 공통 인터페이스 정의할 때 사용

#### Null
* 스프링 데이터 2.0 부터 자바 8의 Optional 지원
  * Optional<Post> findById(Long id);

* 컬렉션은 Null을 리턴하지 않고, 비어있dd는 컬렉션을 리턴

* 스프링 프레임워크 5.0부터 지원하는 Null 애노테이션 지원
  * @NonNullApi(패키지 레벨)
    * package-info.java 파일에 패키지위에 어노테이션 태깅
    * 패키지 안에 모든 파라미터, 리턴 타입 등이 @NonNull이 태깅된것과 같음
      * 따라서 Null 허용하려면 해당 위치에 @Nullable 어노테이션을 일일이 태깅해야 해야함
    * 현재까진 툴 지원이 안되는 걸로 보임
  * @NonNull, @Nullable
  * 런타임 체크 지원 함
  * JSR 305 애노테이션을 메타 애노테이션으로 가지고 있음 (IDE 및 빌드 툴 지원)

* 인텔리J 설정
  * Build, Execution, Deployment
    * Compiler
      * Add runtime assertion for notnull-annotated methods and parameters
      * CONFIGURE ANNOTATION
      * 각각 Nullable, NonNUll에 스프링 패키지 어노테이션 추가

#### Query 생성
* Spring 데이터 저장소의 쿼리 만드는 방법
  * 메소드명을 분석해서 쿼리 만들기 (CREATE)
    * Spring Data가 메서드명을 분석해 쿼리를 생성해주는 방법
    * ``` List<Comment> findByTitleContains(String keyword); ```
    * 쿼리를 메서드명으로 만들지 못하는 경우 레파지토리 빈을 생성하다 에러 발생하여 앱 종료됨
  * 미리 정의해 둔 쿼리 찾아 사용하기 (USE_DECLARED_QUERY)
    * 기본 JPQL : ``` @Query("SELECT C FROM Comment as C") ```
    * SQL : ``` @Query(value = "SELECT C FROM Comment as C", nativeQuery = true) ```
  * 미리 정의한 쿼리 찾아보고 없으면 만들기 (CREATE_IF_NOT_FOUND)
    * 기본값 (위 2가지 방법을 합친 것)

* 어노테이션 순서
  * @EnableJpaRepositories 어노테이션에서 QueryLookupStrategy 속성 안에 RepositoryQuery를 생성하는데 쓰이는 전략 확인
  * RepositoryQuery 구현체 > JpaQueryLookupStrategy 클래스
  * JpaQueryLookupStrategy 클래스 안에 위에서 설명한 CreateQueryLookupStrategy, DeclaredQueryLookupStrategy 정적 클래스 존재
  * DeclaredQueryLookupStrategy 클래스 (이미 정의된 쿼리를 찾아내는 방법)
    * 정의된 쿼리를 찾을 때 어노테이션 우선 순위
      * @Query, @Procedure, @NamedQuery 순서대로 적용

* 쿼리 만드는 방법
  * 리턴타입 {접두어}{도입부}By{프로퍼티 표현식}(조건식)[(And|Or){프로퍼티 표현식}(조건식)]{정렬 조건} (파라미터)
    * ```
      Page<Comment> findByLikeGreaterThanAndPostOrderByCreatedDesc(int likeCount, Post webPost, Pageable pageable);
      ```
    * ```
      List<Comment> findByLikeGreaterThanAndPost(int likeCount, Post webPost, Sort sort);
      ```
    * 리턴타입
      * E, Optional<E>, List<E>, Page<E>, Slice<E>, Stream<E>
    * 접두어
      * Find, Get, Query, Count, 기타..
    * 도입부
      * Distinct, First(N), Top(N)
    * 프로퍼티 표현식
      * Person.Address.ZipCode => find(Person)ByAddress_ZipCode(...)
    * 조건식
      * IgnoreCase, Between, LessThan, GreaterThan, Like, Contains, 기타..
    * 정렬조건
      * OrderBy{프로퍼티}Asc|Desc
    * 파라미터
      * Pageable, Sort

* 쿼리 찾는 방법
  * 메소드명으로 쿼리를 표현하기 힘든 경우에 사용
  * 저장소 기술에 따라 다름
  * JPA: @Query @NamedQuery

* 기본 예제
  * ```
    List<Person> findByEmailAddressAndLastname(EmailAddress emailAddress, String lastname);
    ```
  * ```
    // distinct  
    List<Person> findDistinctPeopleByLastnameOrFirstname(String lastname, String firstname);
    ```
  * ```
    List<Person> findPeopleDistinctByLastnameOrFirstname(String lastname, String firstname);
    ```
  * ```
    // ignoring case
    List<Person> findByLastnameIgnoreCase(String lastname);
    ```
  * ```
    // ignoring case
    List<Person> findByLastnameAndFirstnameAllIgnoreCase(String lastname, String firstname);
    ```

* 정렬 예제
  * ``` List<Person> findByLastnameOrderByFirstnameAsc(String lastname); ```
  * ``` List<Person> findByLastnameOrderByFirstnameDesc(String lastname); ```

* 페이징 예제
  * ``` Page<User> findByLastname(String lastname, Pageable pageable); ```
  * ``` Slice<User> findByLastname(String lastname, Pageable pageable); ```
  * ``` List<User> findByLastname(String lastname, Sort sort); ```
  * ``` List<User> findByLastname(String lastname, Pageable pageable); ```

* 스트림 예제
  * ``` Stream<User> readAllByFirstnameNotNull(); ```
    * try-with-resource 사용할 것
    * Stream을 다 쓴다음에 close() 해야 함

#### Async Query
* 비동기 쿼리
  * 쿼리에 @Async 어노테이션 태깅
    * 하지만 @Async 어노테이션만으로는 비동기적으로 수행되지 않음
    * 이 어노테이션은 백그라운드 스레드 풀에 해당 작업을 위임, 별도의 스레드에서 동작 시키는 것
    * 비동기 처리를 위해서는 @SpringBootApplication 이 태깅된 클래스에 @EnableAsync 어노테이션 태깅* 
    * 하지만 예제에서는 태깅 후에도 비동기 처리가 원활히 이루어지지 않고 테스트 코드 작성이 어려움
    * 따라서 가급적 사용을 권하지 않음
  * @Async Future<User> findByFirstname(String firstname);
    * Future는 자바 5  
  * @Async CompletableFuture<User> findOneByFirstname(String firstname);
    * CompletableFuture는 자바 8
  * @Async ListenableFuture<User> findOneByLastname(String lastname);
    * ListenableFuture는 스프링 프레임워크
    * 제일 깔끔함
  * 해당 메소드를 스프링 TaskExecutor에 전달해서 별도의 쓰레드에서 실행
  * Reactive랑은 다른 것임

* 테스트 예제
  * 테스트 진행 시에 ListenableFuture 객체를 사용하여 addCallback 메서드 호출 하는 테스트 코드가 없다고 판단
  * 따라서 데이터 삽입 쿼리 또한 호출, 실행되지 않음
  * 테스트코드는 기본적으로 롤백처리 되기 때문에
  * 예제 태스트 코드에서 아무런 작업을 수행하지 않는다고 판단하면 데이터를 삽압하지 않음

* 권장하지 않는 이유
  * 테스트 코드 작성이 어려움
  * 코드 복잡도 증가
  * 성능상 이득이 없음 
  * DB 부하는 결국 같고 메인 스레드 대신 백드라운드 스레드가 일하는 정도의 차이
    * 성능 튜닝이 필요하다면 쿼리 호출 횟수, 최소한의 데이터를 가져오는 등 다른 부분에서 튜닝하는 것이 바람직 
  * 단, 백그라운드로 실행하고 결과를 받을 필요가 없는 작업이라면 @Async를 사용해서 응답 속도를 향상 시킬 수는 있음

#### Custom repository
* 쿼리 메소드(쿼리 생성과 쿼리 찾아쓰기)로 해결이 되지 않는 경우 직접 구현(코딩) 가능
  * 스프링 데이터 레퍼지토리 인터페이스에 기능 추가
  * 스프링 데이터 레퍼지토리 기본 기능 덮어쓰기 가능
  * 구현 방법
    * 커스텀 레퍼지토리 인터페이스 정의
    * 레퍼지토리 인터페이스를 구현하는 클래스 생성 (기본 접미어는 Impl)
      * 클래스명 : 인터페이스명 + Impl
    * 엔티티 레퍼지토리 인터페이스에 커스텀 레퍼지토리 인터페이스 상속 추가

* 커스텀
  * 원하는 기능(쿼리) 추가하기
  * 기본 기능 덮어쓰기
  * 접미어 설정하기
    * @SpringBootApplication 어노테이션이 태깅된 JpaApplication 클래스에 아래 어노테이션으로 설정
    * ``` @EnableJpaRepositories(repositoryImplementationPostfix = "default") ```
    * 레퍼지토리 접미어가 일치하지 않으면 자체적으로 구현체를 생성하게 되어 직접 구현한 레퍼지토리에 기능을 사용할 수 없음

* 공통 레퍼지토리
  * 공통적인 기능 추가 또는 덮어쓸 기능 존재시
  * 아래와 같은 방법으로 구현
  * JpaRepository를 상속 받는 인터페이스 정의
    * 공통 레퍼지토리에 @NoRepositoryBean 어노테이션 태깅
    * 기본 구현체를 상속 받는 커스텀 구현체 만들기
    * @EnableJpaRepositories에 repositoryBaseClass 속성 설정
      * ``` @EnableJpaRepositories(repositoryBaseClass = SimpleMyCommonRepository.class) ```

#### JPA 어노테이션
* 엔티티 매핑 어노테이션
  * @Entity
    * 엔티티 클래스임을 지정
  * @Table
    * 엔티티가 매핑될 테이블을 지정
    * 생략하면 엔티티 클래스명과 동일한 이름에 테이블과 매핑됨
  * @Column
    * 지정된 필드를 테이블 컬럼과 매핑
    * 생략하면 필드명과 동일한 이름의 테이블 컬럼과 매핑됨

* 엔티티 속성 매핑 어노테이션
  * @Basic
    * 테이블의 단순 타입 컬럼과 매핑됨
    * 자바 원시 데이터 타입
      * String, BigInteger, Date, byte[], char[], Character[], Serialize 인터페이스를 구현한 여러 타입
    * fetch 속성
      * EAGER(기본값), LAZY 값을 통해 로딩 시점 설정
    * optional 속성
      * 런타임 중 DB에 저장되기 전에 체크 되는 속성 
      * boolean 타입으로 지정 가능
    * @Column
      * nullable 속성은 DB에 테이블 스키마가 만들어지는 시점에 속성에 Not Null 여부를 적용할지 판단
  * @Enumerated
    * 열거형 데이터를 매핑
    * 열거형 값은 ordinal 이라는 인덱스 값과 연동됨
  * @Lob
    * DB의 CLOB, BLOB 데이터 타입과 매핑됨
    * 속성 타입이 String, char[] 이면 CLOB, 그 외에는 BLOB과 매핑됨
  * @Temporal
    * 날짜 타입 필드(JPA 2.1 버전까지는 Date, Calendar)와 매핑됨
  * @Transient
    * DB 컬럼에 매핑되지 않는 필드 지정 (임시 또는 소스 로직상만 필요한 필드)
    * 필드 또는 getter에 태깅
  * @Access
    * 프로퍼티 접근 방식 지정
    * JPA 어노테이션은 필드와 getter에 태깅할 수 있지만 동시에 부여할 수 없고 한 쪽에만 태깅 가능
    * 일반적으로 @Id 어노테이션을 기준으로 영속화 됨
      * @Id 어노테이션을 필드에 붙이면 필드를 기준으로, getter에 붙이면 getter를 기준으로 영속화 됨
    * AccessType.FIELD 경우 메서드를 거치지 않고 직접 필드를 읽어오기 때문에 메서드에 별도 로직이 존재하는 경우 동작하지 않음
      * AccessType.PROPERTY

#### Domain event publish
* 도메인 관련 이벤트 발생
  * ApplicationContext는 단순 빈팩토리를 넘어서 이벤트 퍼블리셔 기능
    * ApplicationContext extends ApplicationEventPublisher
  * 이벤트
    * 이벤트 객체 구현시 ApplicationEvent 객체 상속
    * ``` extends ApplicationEvent ```
  * 리스너
    * 리스너 객체 구현시 ApplicationListener 인터페이스 구현
    * ``` implements ApplicationListener<E extends ApplicationEvent> ```
    * @EventListener
      * 위 ApplicationListener 인터페이스를 구현하지 않고 메서드에 @EventListener 어노테이션 태깅
      * 해당 리스너가 빈으로 등록 되어 있어야 함
  * 예제
    * 이벤트 : ``` public class PostPublishedEvent extends ApplicationEvent ```
    * 리스너 : ``` public class PostListener implements ApplicationListener<PostPublishedEvent> ```
    * 테스트
      * 기본적으로 스프링은 슬라이싱 테스트(일부 레이어만 테스트 하는 것)이기 때문에 테스트 패키지에서 빈으로 따로 등록 필요
      * 예제에서는 @DataJpaTest 어노테이션을 사용하여 테스트를 진행하고 있기 때문에 데이터 관련 빈들만 등록하여 테스트가 진행됨
        * 프로덕션 코드에서 @Component 등을 태깅하여도 테스트엔 적용되지 않음
        * @Repository 어노테이션을 사용하면 테스트에서 인식할 수도 있으나 바람직한 방법은 아님
      * 스프링 슬라이싱 테스트
        * @JsonTest
        * @WebMvcTest
        * @WebFluxTest
        * @DataJpaTest
      * 테스트 패키지에 ListenerTestConfig 클래스 구현하여 @Configuration 어노테이션 태깅
        * ListenerTestConfig 안에서 PostListener 객체를 빈으로 등록
      * 리스너 클래스를 직접 구현하고 싶지 않은 경우
        * 빈으로 등록되는 아래와 같은 메서드에서
        * ``` public ApplicationListener<PostPublishedEvent> notCreatePostListener() ```
        * 익명 클래스 구현 또는 람다식으로 반환 객체를 작성하여 사용

* 스프링 데이터의 도메인 이벤트 Publisher
  * @DomainEvents
    * 도메인 이벤트를 모아두는 메서드
  * @AfterDomainEventPublication
    * 누적된 이벤트를 비움
  * extends AbstractAggregateRoot<E>
  * 현재는 save() 할 때만 발생

#### QueryDSL
* QueryDSL
  * 정적 타입을 이용해 타입 세이프한 쿼리 만들 수 있게 도와주는 라이브러리(프레임워크)
  * JPA, SQL, MongoDB, JDO, Lucene, Collection 지원

* 사용 이유
  * 조건문을 표현하는 방법이 타입 세이프함(자바코드로 조건문 표현), 조건문 조합 가능

* 일반적으로 대부분의 쿼리
  * ``` Optional<T> findOne(Predicate) ```
    * 특정 조건으로 무언가 하나를 찾음
  * ``` List<T>|Page<T>|.. findAll(Predicate) ```
    * 특정 조건으로 무언가 여러개를 찾음

* gradle 설정
  * 플러그인 추가
    * ``` id 'com.ewerk.gradle.plugins.querydsl' version '1.0.10' ```
  * 자동 생성된 dsl 클래스를 보관할 디렉토리 경로 선언(설정)
    * ``` def querydslSrcDir = 'src/main/generated' ```
  * queryDsl 설정
    * ```
      querydsl {
          library = "com.querydsl:querydsl-apt"
          jpa = true
          querydslSourcesDir = querydslSrcDir
      }
      ```
  * 프로젝트 소스 경로 설정에 위에서 선언한 디렉토리 추가
    * ```
      sourceSets {
          main {
              java {
                  srcDirs = ['src/main/java', querydslSrcDir]
              }
          }
      }
      ```
  * queryDsl 컴파일 설정
    * ```
      compileQuerydsl{
          options.annotationProcessorPath = configurations.querydsl
      }
      
      configurations {
          querydsl.extendsFrom compileClasspath
      }
      ```
  * 의존성 추가
    * querydsl-apt : 코드를 생성해주는 모듈 (엔티티 모델에 맞는 쿼리용 도메인 특화 언어를 만들어줌)
      * ``` implementation group: 'com.querydsl', name: 'querydsl-apt' ```
    * querydsl-jpa : 도메인 특화 언어(domain specific language)
    * ``` implementation group: 'com.querydsl', name: 'querydsl-jpa' ```
  * 테스트 시 생성된 dsl을 찾지 못한다면 툴에서 dsl 파일 경로 설정 추가
  * DSL 파일을 찾지 못하는 경우 gradle > other > compileQuerydsl 클릭

* 스프링 데이터 JPA + QueryDSL
  * 인터페이스: QuerydslPredicateExecutor<T>
  * 구현체: QuerydslJpaRepository<T>

* 연동방법
  * 기본 레퍼지토리 사용
    * 기본적으로 QuerydslJpaRepository를 사용
      * QuerydslJpaRepository(현재 deprecated)가 SimpleJpaRepository를 상속하면서  
        QuerydslPredicateExecutor 인터페이스를 구현하고 있음
    * 레퍼지토리에서 QuerydslPredicateExecutor 인터페이스 상속
      * ``` extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T> ```
    * 예제
      * queryDSL 패키지 AccountRepository 클래스
      * ```
        public interface AccountRepository 
                extends JpaRepository<Account, Long>, QuerydslPredicateExecutor<Account>
        ```
  * 커스터마이징 한 기본 레퍼지토리 사용
    * QuerydslPredicateExecutor 구현체가 없는 경우 제대로 사용할 수 없음
      * QuerydslJpaRepository(현재 deprecated)가 SimpleJpaRepository를 상속하면서  
        QuerydslPredicateExecutor 인터페이스를 구현하고 있음
    * 커스터마이징 한 기본 레퍼지토리가 SimpleJpaRepository가 아닌 QuerydslJpaRepository를 상속하도록 구현
    * 예제
      * repository 패키지 SimpleMyCommonRepository 클래스
      * ```
        public class SimpleMyCommonRepository<T, ID extends Serializable>
        		extends QuerydslJpaRepository<T, ID> implements MyCommonRepository<T, ID>
        ```

### Spring Common Web

#### 웹 지원 기능
* 스프링 데이터 웹 지원 기능 설정
  * 스프링 부트를 사용하는 경우 자동 설정
  * 스프링 부트 사용하지 않는 경우
    * ```
      @Configuration
      @EnableWebMvc
      @EnableSpringDataWebSupport
      class WebConfiguration {}
      ```

* 제공 기능
  * 도메인 클래스 컨버터
  * @RequestHandler 메소드에서 Pageable과 Sort 매개변수 사용 
  * Page 관련 HATEOAS(Hypermedia As The Engine Of Application state) 기능 제공
    * PagedResourcesAssembler
    * PagedResoure
  * Payload 프로젝션
    * 요청으로 들어오는 데이터 중 일부만 바인딩 받아오기
    * @ProjectedPayload, @XBRead, @JsonPath
  * 요청 쿼리 매개변수를 QueryDSLdml Predicate로 받아오기
    * ?firstname=Mr&lastname=White => Predicate

#### DomainClassConverter
* Spring Converter(Formatter-문자열기반)
  * 예제에서는 데이터를 바인딩 받는 객체에 id 필드의 타입이 Long이기 때문에 Formatter가 아닌 Converter로 받게 됨

* 테스트 설정
  * @SpringBootTest 테스트는 Slicing test가 아닌 Integrated test이기 때문에 모든 빈이 등록되어 테스트 됨
  * application.properties(yaml) 파일에 있는 빈 목록들까지 사용하지 않는 경우
    * test 경로에 application-test.properties 파일을 생성하고 사용하는 빈 설정만 작성한 후
    * @ActiveProfiles("test") 어노테이션 태깅

#### Pageable & Sort
* 스프링 MVC HandlerMethodArgumentResolver

* 스프링 MVC 핸들러 메소드의 매개변수로 받을 수 있는 객체를 확장하고 싶을 때 사용하는 인터페이스

* 페이징과 정렬 관련 매개변수
  * page: 0부터 시작
  * size: 기본값 20
  * sort: property,property(,ASC|DESC)
  * 예) sort=created,desc&sort=title (asc가 기본값)

#### HATEOAS
* Hypermedia As The Engine Of Application state
  * RESTful API를 사용하는 클라이언트가 전적으로 서버와 동적인 상호작용이 가능하도록 하는 것
    * 이러한 방법은 클라이언트가 서버로부터 어떠한 요청을 할 때  
      요청에 필요한(의존되는) URI를 응답에 포함시켜 반환 하는 것으로 가능하게 할 수 있음
  * REST API 단점을 보완함
    * 엔드 포인트 URI(URL) 변경 시 모두 수정해야 하는 문제
    * 현재 자원의 상태를 고려하지 않는 문제
  * 장점
    * 요청 URI가 변경되더라도 클라이언트에서 동적으로 생성된 URI를 사용함으로써  
      클라이언트가 URI 수정에 따른 코드를 변경하지 않아도 되는 편리함을 제공
    * URI 정보를 통해 들어오는 요청을 예측할 수 있게 됨
    * Resource가 포함된 URI를 보여주기 때문에 Resource에 대한 확신을 얻을 수 있음
    * 클라이언트를 개발하는 사람들이 특정 메소드로부터 올 수 있는 결과 동작에 대해 예측하는 것이 가능해지고  
      API가 변경되더라도 키가 바뀌지 않는 한 URI로 주어진 링크(link)만 유지하면 되므로 별도의 대응이 요구되지 않게 됨

* Page를 PagedResource로 변환
  * 일단 HATEOAS 의존성 추가 (starter-hateoas)
    * ``` implementation group: 'org.springframework.boot', name: 'spring-boot-starter-hateoas' ```
  * 핸들러 매개변수로 PagedResourcesAssembler

* 리소스 변환 전
  * ```
    {  
       "content":[  
    ...
          {  
             "id":111,
             "title":"jpa",
             "created":null
          }
       ],
       "pageable":{  
          "sort":{  
             "sorted":true,
             "unsorted":false
          },
          "offset":20,
          "pageSize":10,
          "pageNumber":2,
          "unpaged":false,
          "paged":true
       },
       "totalElements":200,
       "totalPages":20,
       "last":false,
       "size":10,
       "number":2,
       "first":false,
       "numberOfElements":10,
       "sort":{  
          "sorted":true,
          "unsorted":false
       }
    }
    ```

* 리소스 변환 후
  * ```
    {  
       "_embedded":{  
          "postList":[  
             {  
                "id":140,
                "title":"jpa",
                "created":null
             },
    ...
             {  
                "id":109,
                "title":"jpa",
                "created":null
             }
          ]
       },
       "_links":{  
          "first":{  
             "href":"http://localhost/posts?page=0&size=10&sort=created,desc&sort=title,asc"
          },
          "prev":{  
             "href":"http://localhost/posts?page=1&size=10&sort=created,desc&sort=title,asc"
          },
          "self":{  
             "href":"http://localhost/posts?page=2&size=10&sort=created,desc&sort=title,asc"
          },
          "next":{  
             "href":"http://localhost/posts?page=3&size=10&sort=created,desc&sort=title,asc"
          },
          "last":{  
             "href":"http://localhost/posts?page=19&size=10&sort=created,desc&sort=title,asc"
          }
       },
       "page":{  
          "size":10,
          "totalElements":200,
          "totalPages":20,
          "number":2
       }
    }
    ```

#### Spring Data Common 정리
* 스프링 데이터 Repository
* 쿼리 메소드
  * 메소드명 보고 만들기
  * 메소드명 보고 찾기
* Repository 정의
  * 내가 쓰고 싶은 메소드만 골라서 만들기
  * Null 처리
* 쿼리 메소드 정의하는 방법
* 리포지토리 커스터마이징
  * 리포지토리 하나 커스터마이징
  * 모든 리포지토리의 베이스 커스터마이징
* 도메인 이벤트 Publish
* 스프링 데이터 확장 기능
  * QueryDSL 연동
  * 웹 지원

### Spring Data JPA

#### Jpa Repository
* @EnableJpaRepositories
  * 스프링 부트 사용할 때는 자동 설정
  * 스프링 부트 사용하지 않을 때는 @Configuration과 같이 사용
  
* @Repository
  * 안붙여도 됨
  * 태깅해도 동작하는데는 문제 없음
  
* 스프링 @Repository
  * SQLExcpetion 또는 JPA 관련 예외를 스프링의 DataAccessException으로 변환

#### 엔티티 저장 (Save 메서드)
* JpaRepository의 save 메서드
  * save 메서드는 단순히 새 엔티티를 추가하는 메소드가 아님
  * Transient 상태의 객체라면 EntityManager.persist()
  * Detached 상태의 객체라면 EntityManager.merge()

* Transient인지 Detached 인지 판단하는 방법
  * 엔티티의 @Id 프로퍼티를 찾음
  * 해당 id가 null이면 Transient 상태로 판단
    * id가 null이 아니면 Detached 상태로 판단
  * 엔티티가 Persistable 인터페이스를 구현하고 있다면 isNew() 메소드에 위임
  * JpaRepositoryFactory를 상속받는 클래스를 만들고  
    getEntityInformation()을 오버라이딩해서 자신이 원하는 판단 로직을 구현할 수도 있음

* EntityManager
  * EntityManager.persist()
    * Persist() 메소드에 넘긴 그 엔티티 객체를 Persistent 상태로 변경
  * EntityManager.Merge() 
    * 메소드에 파라미터로 넘긴 그 엔티티의 복사본을 생성, 그 복사본을 다시 Persistent 상태로 변경(영속화)하고 그 복사본을 반환
    * 실수를 줄이려면 파라미터로 전달한 인스턴스를 사용하지 말고 결과값으로 반환해주는 객체를 사용할 것

#### Query 메서드
* 쿼리 생성
  * And, Or
  * Is, Equals
  * LessThan, LessThanEqual, GreaterThan, GreaterThanEqual
  * After, Before
  * IsNull, IsNotNull, NotNull
  * Like, NotLike
  * StartingWith, EndingWith, Containing
  * OrderBy
  * Not, In, NotIn
  * True, False
  * IgnoreCase

* 쿼리 찾아쓰기
  * 엔티티에 정의한 쿼리를 찾아 사용 (JPA Named 쿼리)
    * @NamedQuery
      * JPQL
    * @NamedNativeQuery
      * Native query
  * 레퍼지토리 메소드에 정의한 쿼리 사용
    * @Query
    * @Query(nativeQuery=true)

* Sort
  * @Query와 같이 사용할 때 제약 사항 존재
    * Order by 절에서 함수를 호출하는 경우, Sort 호출 못함
      * JpaSort.unsafe()를 사용해야 함
    * Sort는 그 안에서 사용한 프로퍼티 또는 alias가 엔티티에 없는 경우에 예외가 발생
    * JpaSort.unsafe()를 사용하면 함수 호출을 할 수 있음
      * ``` JpaSort.unsafe(“LENGTH(firstname)”); ```

#### Named Parameter & SpEL
* Named Parameter
  * @Query에서 참조하는 매개변수를 이름으로 참조하는 방법 (:title과 같이)
    * ```
      Query("SELECT p FROM Post AS p WHERE p.title = :title")
      List<Post> findByTitle(@Param("title") String title, Sort sort);
      ```
  * @Query에서 참조하는 매개변수를 채번으로 참조하는 방법 (?1, ?2와 같이)
    * ```
      @Query("SELECT p FROM Post AS p WHERE p.title = ?1")
      List<Post> findByTitle(String title, Sort sort);
      ```

* SpEL (Spring Expression Language)
  * 스프링 표현 언어
  * @Query에서 엔티티 이름을 #{#entityName} 으로 표현 가능
    * ```
      @Query("SELECT p FROM #{#entityName} AS p WHERE p.title = :title")
      List<Post> findByTitle(@Param("title") String title, Sort sort);
      ```

#### Update Query 메서드
* 쿼리 생성
  * find
  * count
  * delete
 
* 엔티티(객체)를 persistence context 관리 중 상태의 변화를 감지하면 flush, 동기화 됨 (업데이트 쿼리가 전송)

* Update 또는 Delete 쿼리 직접 정의
  * @Modifying @Query
    * ```
      @Modifying(clearAutomatically = true, flushAutomatically = true)
      @Query("UPDATE Post p SET p.title = ?2 WHERE p.id = ?1")
      int updateTitle(Long id, String title);
      ```
    * clearAutomatically 속성은 업데이트 쿼리 호출 후 persistence context에 캐시를 clear
    * flushAutomatically 속성은 쿼리 호출 전 persistence context에 캐시를 flush
  * 권장하지 않는 방법

#### EntityGraph
* 쿼리 메소드마다 연관 관계의 Fetch 모드를 설정 가능

* @NamedEntityGraph
  * @Entity에서 재사용할 여러 엔티티 그룹을 정의할 때 사용
  * 예제
    * ``` @NamedEntityGraph(name = "CommentJPA.post", attributeNodes = @NamedAttributeNode("post")) ```
* @EntityGraph
  * @NamedEntityGraph에 정의되어 있는 엔티티 그룹을 사용함
  * 그래프 타입 설정 가능
    * FETCH(기본값) : 설정한 엔티티 애트리뷰트는 EAGER 패치 설정이 안된 나머지는 LAZY 패치
      * 프리미티브 타입은 EAGER로 가져옴
    * LOAD : 설정한 엔티티 애트리뷰트는 EAGER 패치 나머지는 기본 패치 전략 따름
      * @OneToOne, @ManyToOne : EAGER
      * @ManyToMany, @OneToMany : LAZY
    * 예제
      * ``` @EntityGraph(value = "CommentJPA.post", type = EntityGraph.EntityGraphType.FETCH) ```
      * ``` @EntityGraph(value = "CommentJPA.post", type = EntityGraph.EntityGraphType.LOAD) ```

* 엔티티에 @NamedEntityGraph 어노테이션을 태깅하지 않고  
  레퍼지토리 쿼리에 @EntityGraph 어노테이션 태깅, attributePaths 속성 설정으로도 사용 가능
  * 예제
    * ``` @EntityGraph(attributePaths = {"post"}) ```
