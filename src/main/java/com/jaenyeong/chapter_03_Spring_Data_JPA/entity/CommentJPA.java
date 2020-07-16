package com.jaenyeong.chapter_03_Spring_Data_JPA.entity;

import lombok.Data;

import javax.persistence.*;

// @NamedEntityGraph 어노테이션 작성 없이 Repository에 @EntityGraph 어노테이션에 attributePaths 속성으로 설정 가능
//@NamedEntityGraph(name = "CommentJPA.post", attributeNodes = @NamedAttributeNode("post"))
@Data
@Entity
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
}
