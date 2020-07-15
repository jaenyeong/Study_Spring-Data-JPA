package com.jaenyeong.chapter_02_JPA.queryDSL;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.io.Serializable;

// IDEA에서 entityInformation 클래스 인식을 못하여 경고하는 컴파일 에러 끄기
@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
public class CustomRepositoryImpl<T, ID extends Serializable>
	// QuerydslJpaRepository 클래스가 deprecated 되어 그대로 SimpleJpaRepository 클래스 상속
		extends SimpleJpaRepository<T, ID>
		implements CustomRepository<T, ID> {

	private EntityManager entityManager;

	public CustomRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
	}

	@Override
	public boolean contains(Object entity) {
		return entityManager.contains(entity);
	}
}
