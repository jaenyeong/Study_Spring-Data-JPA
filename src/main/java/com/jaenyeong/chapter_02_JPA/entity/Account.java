package com.jaenyeong.chapter_02_JPA.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

// 데이터베이스 테이블과 매핑되는 엔티티 표시
// @Table이 생략되어 있는것과 동일
// name 속성으로 설정하는 것은 하이버네이트 안에서의 이름일뿐
// 실제 테이블 설정은 @Table 어노테이션의 name 속성으로 설정, 기본적으로 클래스명이 테이블명
// 데이터베이스의 예약어(키워드)로 잡혀 있는 단어를 클래스명으로 사용하지 않아야 함
// 데이터베이스 syntax 에러 발생
//@Entity(name = "myAccount")
@Entity
//@Table
public class Account {
	// PK 설정 및 자동으로 생성한 값 사용
	@Id
	@GeneratedValue
	private Long id;
	// @Column 어노테이션이 생략되어 있는 것과 동일
	@Column(nullable = false, unique = true)
	private String userName;
	private String password;

	@Embedded
	@AttributeOverrides({
			// Address의 street 필드를 home_street 이라는 컬럼명으로 사용
			@AttributeOverride(name = "street", column = @Column(name = "home_street"))
	})
	private Address address;

	// mappedBy 속성으로 매핑하지 않으면 양쪽 필드에서 어노테이션 태깅해도 단방향 설정에 불과함
	// mappedBy 연결을 해야 불필요한 스키마, 데이터가 생기지 않음
	@OneToMany(mappedBy = "owner")
	private Set<Study> studies = new HashSet<>();

//	@Embedded
//	private Address homeAddress;
//
//	@Embedded
//	private Address officeAddress;

	// Study, Account 관계 설정
	public void addStudy(Study study) {
		this.getStudies().add(study);
		study.setOwner(this);
	}

	// Study, Account 관계 삭제
	public void removeStudy(Study study) {
		this.getStudies().remove(study);
		study.setOwner(null);
	}

	public Long getId() {
		return id;
	}

	public Account setId(Long id) {
		this.id = id;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public Account setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public Account setPassword(String password) {
		this.password = password;
		return this;
	}

	public Address getAddress() {
		return address;
	}

	public Account setAddress(Address address) {
		this.address = address;
		return this;
	}

	public Set<Study> getStudies() {
		return studies;
	}

	public Account setStudies(Set<Study> studies) {
		this.studies = studies;
		return this;
	}
}
