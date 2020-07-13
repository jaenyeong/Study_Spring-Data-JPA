package com.jaenyeong.chapter_02_JPA.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Study {
	@Id @GeneratedValue
	private Long id;
	private String name;

	// 이쪽이 주인
	// 일반적으로 FK를 가진 쪽이 주인
	// 관계에 매핑을 이쪽에서 해주지 않으면 데이터가 저장되지 않음
	@ManyToOne
	private Account owner;

	public Long getId() {
		return id;
	}

	public Study setId(Long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Study setName(String name) {
		this.name = name;
		return this;
	}

	public Account getOwner() {
		return owner;
	}

	public Study setOwner(Account owner) {
		this.owner = owner;
		return this;
	}
}
