package com.jaenyeong.chapter_02_JPA.repository;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface CommonRepository<T, Id extends Serializable> extends Repository<T, Id> {
	<E extends T> E save(E entity);
	List<T> findAll();
	long count();
}
