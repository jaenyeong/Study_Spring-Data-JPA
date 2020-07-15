package com.jaenyeong.chapter_02_JPA.repository;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QuerydslJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public class SimpleMyCommonRepository<T, ID extends Serializable>
//		extends SimpleJpaRepository<T, ID>
	// QuerydslJpaRepository 클래스 deprecated
	// 따라서 SimpleJpaRepository 클래스를 그대로 상속하여 사용하여도 무방
		extends QuerydslJpaRepository<T, ID>
		implements MyCommonRepository<T, ID> {

	private EntityManager entityManager;

	// 기존 SimpleJpaRepository 상속시 생성자
//	public SimpleMyCommonRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
//		super(entityInformation, entityManager);
//		this.entityManager = entityManager;
//	}

	// QuerydslJpaRepository 상속시 생성자
	public SimpleMyCommonRepository(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
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
