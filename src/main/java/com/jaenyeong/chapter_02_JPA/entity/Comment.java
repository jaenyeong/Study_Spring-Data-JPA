package com.jaenyeong.chapter_02_JPA.entity;

import javax.persistence.*;

@Entity
public class Comment {
	@Id @GeneratedValue
	private Long id;
	private String comment;

	@ManyToOne
//	@ManyToOne(fetch = FetchType.EAGER)
	private Post post;

	public Long getId() {
		return id;
	}

	public Comment setId(Long id) {
		this.id = id;
		return this;
	}

	public String getComment() {
		return comment;
	}

	public Comment setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public Post getPost() {
		return post;
	}

	public Comment setPost(Post post) {
		this.post = post;
		return this;
	}
}