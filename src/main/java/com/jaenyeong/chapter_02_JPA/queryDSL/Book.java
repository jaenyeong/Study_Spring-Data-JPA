package com.jaenyeong.chapter_02_JPA.queryDSL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

//@Data
@Entity
public class Book {
	@Id @GeneratedValue
	private Long id;
	private String title;
	@Lob
	private String content;

	public Long getId() {
		return id;
	}

	public Book setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Book setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getContent() {
		return content;
	}

	public Book setContent(String content) {
		this.content = content;
		return this;
	}
}
