package com.jaenyeong.chapter_03_Spring_Data_JPA.repository;

import com.jaenyeong.chapter_03_Spring_Data_JPA.entity.PostJPA;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostJPARepository extends JpaRepository<PostJPA, Long> {
	List<PostJPA> findByTitleStartsWith(String title);

	// 메서드명으로 네임드 쿼리를 찾음
	// 네임드 쿼리를 선언하지 않고 @Query 어노테이션으로 쿼리를 정의할 수 있음
//	@Query(value = "SELECT p FROM PostJPA AS p WHERE p.title = ?1", nativeQuery = true)
	@Query("SELECT p FROM PostJPA AS p WHERE p.title = ?1")
//	List<PostJPA> findByTitle(String title);
	// Sort 정렬 옵션 추가
	// 정렬 옵션에는 프로퍼티 또는 alias만 받을 수 있음
	List<PostJPA> findByTitle(String title, Sort sort);
}
