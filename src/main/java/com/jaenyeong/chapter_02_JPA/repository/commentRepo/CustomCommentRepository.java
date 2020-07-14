package com.jaenyeong.chapter_02_JPA.repository.commentRepo;

import com.jaenyeong.chapter_02_JPA.entity.Comment;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = Comment.class, idClass = Long.class)
public interface CustomCommentRepository {
	Comment save(Comment comment);

	List<Comment> findAll();
}
