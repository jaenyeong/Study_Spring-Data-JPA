package com.jaenyeong.chapter_03_Spring_Data_JPA.entity;

import com.jaenyeong.chapter_02_JPA.entity.Comment;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//@Data
@Entity
// @NamedQuery 어노테이션은 엔티티에 태깅하여 사용 - JPQL
//@NamedQuery(name = "PostJPA.findByTitle", query = "SELECT p FROM PostJPA AS p WHERE p.title = ?1")
public class PostJPA {
	@Id
	@GeneratedValue
	private Long id;
	private String title;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();

	@Lob
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	public Long getId() {
		return id;
	}

	public PostJPA setId(Long id) {
		this.id = id;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public PostJPA setTitle(String title) {
		this.title = title;
		return this;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public PostJPA setComments(Set<Comment> comments) {
		this.comments = comments;
		return this;
	}

	public String getContent() {
		return content;
	}

	public PostJPA setContent(String content) {
		this.content = content;
		return this;
	}

	public Date getCreated() {
		return created;
	}

	public PostJPA setCreated(Date created) {
		this.created = created;
		return this;
	}
}
