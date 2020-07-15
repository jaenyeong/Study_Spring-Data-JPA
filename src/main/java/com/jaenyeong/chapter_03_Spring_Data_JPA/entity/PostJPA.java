package com.jaenyeong.chapter_03_Spring_Data_JPA.entity;

import com.jaenyeong.chapter_02_JPA.entity.Comment;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
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
}
