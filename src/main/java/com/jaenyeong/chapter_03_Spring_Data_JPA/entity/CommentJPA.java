package com.jaenyeong.chapter_03_Spring_Data_JPA.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

// @NamedEntityGraph 어노테이션 작성 없이 Repository에 @EntityGraph 어노테이션에 attributePaths 속성으로 설정 가능
//@NamedEntityGraph(name = "CommentJPA.post", attributeNodes = @NamedAttributeNode("post"))
//@Data
@Entity
// Auditing 사용을 위해 @EntityListeners 어노테이션 태깅
@EntityListeners(AuditingEntityListener.class)
public class CommentJPA {
	@Id @GeneratedValue
	private Long id;
	private String comment;

	// 기본값이 EAGER
//	@ManyToOne(fetch = FetchType.EAGER)
	@ManyToOne(fetch = FetchType.LAZY)
	private PostJPA post;

	private int up;
	private int down;
	private boolean best;

	@CreatedDate
	private Date created;
	@LastModifiedDate
	private Date updated;
	@CreatedBy
	@ManyToOne
	private AccountAudit createdBy;
	@LastModifiedBy
	@ManyToOne
	private AccountAudit updatedBy;

	@PrePersist
	public void prePersist() {
		System.out.println("Pre Persist is called");
//		this.created = new Date();
	}

	public Long getId() {
		return id;
	}

	public CommentJPA setId(Long id) {
		this.id = id;
		return this;
	}

	public String getComment() {
		return comment;
	}

	public CommentJPA setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public PostJPA getPost() {
		return post;
	}

	public CommentJPA setPost(PostJPA post) {
		this.post = post;
		return this;
	}

	public int getUp() {
		return up;
	}

	public CommentJPA setUp(int up) {
		this.up = up;
		return this;
	}

	public int getDown() {
		return down;
	}

	public CommentJPA setDown(int down) {
		this.down = down;
		return this;
	}

	public boolean isBest() {
		return best;
	}

	public CommentJPA setBest(boolean best) {
		this.best = best;
		return this;
	}
}
