package com.jaenyeong.chapter_03_Spring_Data_JPA.entity;

import com.jaenyeong.chapter_02_JPA.entity.Comment;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
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
