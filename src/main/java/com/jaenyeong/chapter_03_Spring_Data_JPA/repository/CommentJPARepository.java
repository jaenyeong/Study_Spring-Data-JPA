package com.jaenyeong.chapter_03_Spring_Data_JPA.repository;

import com.jaenyeong.chapter_03_Spring_Data_JPA.entity.CommentJPA;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentJPARepository extends JpaRepository<CommentJPA, Long> {
	// Post는 FETCH, LOAD 상관없이 EAGER로 즉시 가져옴
//	@EntityGraph(value = "CommentJPA.post", type = EntityGraph.EntityGraphType.FETCH)
//	@EntityGraph(value = "CommentJPA.post", type = EntityGraph.EntityGraphType.LOAD)
	// @NamedEntityGraph 어노테이션 작성 없이 Repository에 @EntityGraph 어노테이션에 attributePaths 속성으로 설정 가능
	@EntityGraph(attributePaths = {"post"})
	Optional<CommentJPA> getById(Long id);
}
