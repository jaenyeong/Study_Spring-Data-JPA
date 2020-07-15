package com.jaenyeong.chapter_02_JPA.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Comment {
	@Id
	@GeneratedValue
	private Long id;
	private String comment;

	@ManyToOne
//	@ManyToOne(fetch = FetchType.EAGER)
	private Post post;

	private Date created;
	// like 라는 필드명 사용시 데이터베이스 키워드로 인식하기 때문에 에러
	private Integer likeCount = 0;

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

	public Date getCreated() {
		return created;
	}

	public Comment setCreated(Date created) {
		this.created = created;
		return this;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public Comment setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
		return this;
	}

	@Override
	public String toString() {
		return "Comment{" +
				"id=" + id +
				", comment='" + comment + '\'' +
//				", post=" + post +
//				", created=" + created +
//				", likeCount=" + likeCount +
				'}';
	}
}
