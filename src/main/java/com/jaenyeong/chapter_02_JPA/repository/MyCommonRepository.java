package com.jaenyeong.chapter_02_JPA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface MyCommonRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
	// 퍼시스턴스에 존재하는지 확인
	boolean contains(T entity);
}
