package com.jaenyeong.chapter_02_JPA.repository.postRepo;

import com.jaenyeong.chapter_02_JPA.entity.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

// 클래스명은 생성한 레퍼지토리 인터페이스명에 Impl를 붙여줘야 함
@Repository
@Transactional
public class CustomPostRepositoryImpl implements CustomPostRepository<Post> {

	@Autowired
	EntityManager entityManager;

	@Override
	public List<Post> findMyPost() {
		System.out.println("CustomPostRepositoryImpl findMyPost");

		return entityManager
				.createQuery("SELECT p FROM WebPost as p", Post.class)
				.getResultList();
	}

	// 기존 존재하는 기능을 덮어씀 (재정의, 오버라이딩)
	@Override
	public void delete(Post entity) {
		System.out.println("Custom delete");
		entityManager.remove(entity);
	}
}
