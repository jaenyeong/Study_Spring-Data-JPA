package com.jaenyeong.chapter_02_JPA;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// 데이터베이스 테이블과 매핑되는 엔티티 표시
// @Table이 생략되어 있는것과 동일
// name 속성으로 설정하는 것은 하이버네이트 안에서의 이름일뿐
// 실제 테이블 설정은 @Table 어노테이션의 name 속성으로 설정, 기본적으로 클래스명이 테이블명
// 데이터베이스의 예약어(키워드)로 잡혀 있는 단어를 클래스명으로 사용하지 않아야 함
// 데이터베이스 syntax 에러 발생
@Entity(name = "myAccount")
//@Table
public class Account {
	// PK 설정 및 자동으로 생성한 값 사용
	@Id @GeneratedValue
	private Long id;
	// @Column 어노테이션이 생략되어 있는 것과 동일
//	@Column
	private String name;
	private String password;

	public Long getId() {
		return id;
	}

	public Account setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Account setName(String name) {
		this.name = name;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public Account setPassword(String password) {
		this.password = password;
		return this;
	}
}
