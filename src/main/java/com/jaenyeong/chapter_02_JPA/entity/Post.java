package com.jaenyeong.chapter_02_JPA.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//@NamedQueries({@NamedQuery(name = "all_post", query = "SELECT p FROM Post AS p")})
@Entity
public class Post {
	@Id
	@GeneratedValue
	private Long id;
	private String title;

	//	@OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST)
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
//	@OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
//	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Comment> comments = new HashSet<>();

	// 255자
	// @Lob 어노테이션은 데이터베이스의 CLOB, BLOB 데이터 타입과 매핑됨
	// String, char[] 이면 CLOB, 그 외에는 BLOB과 매핑됨
	@Lob
	private String content;

	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

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

	public String getContent() {
		return content;
	}

	public Post setContent(String content) {
		this.content = content;
		return this;
	}

	public Date getCreated() {
		return created;
	}

	public Post setCreated(Date created) {
		this.created = created;
		return this;
	}

	@Override
	public String toString() {
		return "Post{" +
				"id=" + id +
				", title='" + title + '\'' +
				// Comments 때문에 comment 데이터를 가져오는 쿼리 출력됨
//				", comments=" + comments +
				", content='" + content + '\'' +
				", created=" + created +
				'}';
	}
}
