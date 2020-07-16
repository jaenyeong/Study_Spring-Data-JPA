package com.jaenyeong.chapter_02_JPA.webCommon.domainClassConverter.entity;

import javax.persistence.*;
import java.util.Date;

//@Data
@Entity
public class WebPost {
	@Id @GeneratedValue
	private Long id;
	private String title;
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	public Long getId() {
		return id;
	}

	public WebPost setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public WebPost setTitle(String title) {
		this.title = title;
		return this;
	}

	public Date getCreated() {
		return created;
	}

	public WebPost setCreated(Date created) {
		this.created = created;
		return this;
	}
}
