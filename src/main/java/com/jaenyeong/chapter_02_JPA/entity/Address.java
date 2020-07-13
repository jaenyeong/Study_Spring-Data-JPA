package com.jaenyeong.chapter_02_JPA.entity;

import javax.persistence.Embeddable;

// Value 타입
@Embeddable
public class Address {
//	@Column
	private String street;
	private String city;
	private String state;
	private String zipCode;
}
