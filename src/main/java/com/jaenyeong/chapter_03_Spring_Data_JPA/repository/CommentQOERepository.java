package com.jaenyeong.chapter_03_Spring_Data_JPA.repository;

import com.jaenyeong.chapter_03_Spring_Data_JPA.entity.CommentJPA;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

public interface CommentQOERepository extends
		JpaRepository<CommentJPA, Long>,
		JpaSpecificationExecutor<CommentJPA>,
		QueryByExampleExecutor<CommentJPA> {

	@EntityGraph(attributePaths = {"post"})
	Optional<CommentJPA> getById(Long id);

	<T> List<T> findByPost_Id(Long id, Class<T> type);
}
