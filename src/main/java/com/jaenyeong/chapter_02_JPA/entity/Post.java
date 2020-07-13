package com.jaenyeong.chapter_02_JPA.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Post {
	@Id @GeneratedValue
	private Long id;
	private String title;

//	@OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
//	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
//	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Comment> comments = new HashSet<>();

	public void addComment(Comment comment) {
		this.getComments().add(comment);
		comment.setPost(this);
	}

	public Long getId() {
		return id;
	}

	public Post setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public Post setTitle(String title) {
		this.title = title;
		return this;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public Post setComments(Set<Comment> comments) {
		this.comments = comments;
		return this;
	}
}
