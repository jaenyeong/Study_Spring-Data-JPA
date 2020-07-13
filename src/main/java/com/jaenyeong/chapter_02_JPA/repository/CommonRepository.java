package com.jaenyeong.chapter_02_JPA.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface CommonRepository<T, Id extends Serializable> extends Repository<T, Id> {
	<E extends T> E save(@NonNull E entity);

	List<T> findAll();

	long count();

	// Optional 타입으로 반환
//	<E extends T> Optional<E> findById(Id id);

	@Nullable
	<E extends T> E findById(Id id);
}
