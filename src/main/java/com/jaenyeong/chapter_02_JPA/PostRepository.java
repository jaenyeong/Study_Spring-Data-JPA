package com.jaenyeong.chapter_02_JPA;

import com.jaenyeong.chapter_02_JPA.entity.Post;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
// 예전에 사용하던 방식
//public class PostRepository extends GenericRepository<Post, Long> {
public class PostRepository {

//	@Autowired
	// @Autowired 대신 JPA 어노테이션 사용하여 스프링 코드를 줄임 (비침투적)
	@PersistenceContext
	EntityManager entityManager;

	public Post add(Post post) {
		entityManager.persist(post);
		return post;
	}

	public void delete(Post post) {
		entityManager.refresh(post);
	}

	public List<Post> findAll() {
		return entityManager.createQuery("SELECT p FROM Post as p", Post.class).getResultList();
	}
}
