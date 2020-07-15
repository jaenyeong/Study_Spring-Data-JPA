package com.jaenyeong.chapter_02_JPA.queryDSL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {
	@Id @GeneratedValue
	private Long id;
	private String userName;
	private String firstName;
	private String lastName;

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

	public String getFirstName() {
		return firstName;
	}

	public Account setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	public String getLastName() {
		return lastName;
	}

	public Account setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
}
