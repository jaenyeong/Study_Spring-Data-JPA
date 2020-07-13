package com.jaenyeong.chapter_02_JPA.repository;

import com.jaenyeong.chapter_02_JPA.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// 최근 레퍼지토리 사용
// POJO라고 할 수는 없음
// 제네릭 첫 번째는 엔티티 타입, 두 번째는 엔티티에서 사용하는 ID 필드 타입
public interface JpaPostRepository extends JpaRepository<Post, Long> {
	Page<Post> findByTitleContains(String title, Pageable pageable);

	long countByTitleContains(String title);
}
