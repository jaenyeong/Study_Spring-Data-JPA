package com.jaenyeong.chapter_02_JPA.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class SimpleMyCommonRepository<T, ID extends Serializable>
		extends SimpleJpaRepository<T, ID>
		implements MyCommonRepository<T, ID> {

	private EntityManager entityManager;

	public SimpleMyCommonRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public boolean contains(Object entity) {
		return entityManager.contains(entity);
	}

	@Override
	public List<T> findAll() {
		return super.findAll();
	}
}
