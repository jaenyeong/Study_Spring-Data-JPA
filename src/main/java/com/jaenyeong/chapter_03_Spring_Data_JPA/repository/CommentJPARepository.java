package com.jaenyeong.chapter_03_Spring_Data_JPA.repository;

import com.jaenyeong.chapter_03_Spring_Data_JPA.entity.CommentJPA;
import com.jaenyeong.chapter_03_Spring_Data_JPA.entity.CommentJPASummary;
import com.jaenyeong.chapter_03_Spring_Data_JPA.entity.CommentJPASummaryClazz;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentJPARepository extends JpaRepository<CommentJPA, Long> {
	// Post는 FETCH, LOAD 상관없이 EAGER로 즉시 가져옴
//	@EntityGraph(value = "CommentJPA.post", type = EntityGraph.EntityGraphType.FETCH)
//	@EntityGraph(value = "CommentJPA.post", type = EntityGraph.EntityGraphType.LOAD)
	// @NamedEntityGraph 어노테이션 작성 없이 Repository에 @EntityGraph 어노테이션에 attributePaths 속성으로 설정 가능
	@EntityGraph(attributePaths = {"post"})
	Optional<CommentJPA> getById(Long id);

	//	List<CommentJPA> findByPost_Id(Long id);
	// 인터페이스로 프로젝션
//	@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
//	List<CommentJPASummary> findByPost_Id(Long id);

	// 클래스로 프로젝션
//	List<CommentJPASummaryClazz> findByPost_Id(Long id);

	// 제네릭 사용
	// 프로젝션의 타입을 넘겨줘야 한다는 단점이 있음
	<T> List<T> findByPost_Id(Long id, Class<T> type);
}
