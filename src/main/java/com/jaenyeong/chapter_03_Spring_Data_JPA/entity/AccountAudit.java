package com.jaenyeong.chapter_03_Spring_Data_JPA.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AccountAudit {
	@Id @GeneratedValue
	private Long id;
	private String userName;
	private String password;

	public Long getId() {
		return id;
	}

	public AccountAudit setId(Long id) {
		this.id = id;
		return this;
	}

	public String getUserName() {
		return userName;
	}

	public AccountAudit setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public AccountAudit setPassword(String password) {
		this.password = password;
		return this;
	}
}
