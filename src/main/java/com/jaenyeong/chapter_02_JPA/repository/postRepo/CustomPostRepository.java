package com.jaenyeong.chapter_02_JPA.repository.postRepo;

import com.jaenyeong.chapter_02_JPA.entity.Post;

import java.util.List;

public interface CustomPostRepository<T> {
	List<Post> findMyPost();

	// 기존 존재하는 기능을 덮어씀 (재정의, 오버라이딩)
	void delete(T entity);
}
