package com.jaenyeong.chapter_03_Spring_Data_JPA.repository;

import com.jaenyeong.chapter_03_Spring_Data_JPA.entity.PostJPA;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostJPARepository extends JpaRepository<PostJPA, Long> {
	List<PostJPA> findByTitleStartsWith(String title);

	// 메서드명으로 네임드 쿼리를 찾음
	// 네임드 쿼리를 선언하지 않고 @Query 어노테이션으로 쿼리를 정의할 수 있음
//	@Query(value = "SELECT p FROM PostJPA AS p WHERE p.title = ?1", nativeQuery = true)
	// 채번 쿼리
//	@Query("SELECT p FROM PostJPA AS p WHERE p.title = ?1")
	// named parameter
//	@Query("SELECT p FROM PostJPA AS p WHERE p.title = :title")
	// SpEL
	@Query("SELECT p FROM #{#entityName} AS p WHERE p.title = :title")
//	List<PostJPA> findByTitle(String title);
	// Sort 정렬 옵션 추가
	// 정렬 옵션에는 프로퍼티 또는 alias만 받을 수 있음
//	List<PostJPA> findByTitle(String title, Sort sort); // 채번 쿼리
	List<PostJPA> findByTitle(@Param("title") String title, Sort sort); // named parameter

	// clearAutomatically 속성은 업데이트 쿼리 호출 후 persistence context에 캐시를 clear
	// flushAutomatically 속성은 쿼리 호출 전 persistence context에 캐시를 flush
	@Modifying(clearAutomatically = true)
	@Query("UPDATE PostJPA p Set p.title = ?1 WHERE p.id = ?2")
	int updateTitle(String title, Long id);
}
