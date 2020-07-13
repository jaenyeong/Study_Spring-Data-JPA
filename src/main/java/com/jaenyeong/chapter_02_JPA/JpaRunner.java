package com.jaenyeong.chapter_02_JPA;

import com.jaenyeong.chapter_02_JPA.entity.Account;
import com.jaenyeong.chapter_02_JPA.entity.Comment;
import com.jaenyeong.chapter_02_JPA.entity.Post;
import com.jaenyeong.chapter_02_JPA.entity.Study;
import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {

	// JPA 핵심
	@PersistenceContext
	EntityManager entityManager;

	@Override
//	@Transactional
	public void run(ApplicationArguments args) throws Exception {
//		exampleBasic();

		examplePersistent();
	}

	private void examplePersistent() {
		Session session = entityManager.unwrap(Session.class);

//		Post post = new Post();
//		post.setTitle("Spring Data JPA 제목");
//
//		Comment comment1 = new Comment();
//		comment1.setComment("contents.. [1]");
//		// Post 객체에 comment 필드에 cascade 옵션 없으면 데이터베이스에 저장되지 않음
//		post.addComment(comment1);
//
//		Comment comment2 = new Comment();
//		comment2.setComment("contents.. [2]");
//		post.addComment(comment2);
//
//		session.save(post);

		Post searchPost = session.get(Post.class, 1L);
		// 삭제
//		session.delete(searchPost);

		System.out.println("==*--------------*-------------*==");
		System.out.println(searchPost.getTitle());

		// Post 객체가 가진 Comment 리스트 출력
		searchPost.getComments().forEach(c -> {
			System.out.println("==*---------------------------*==");
			System.out.println(c.getComment());
		});

//		Comment eagerComment = session.get(Comment.class, 2L);
//		System.out.println("==*--------------*-------------*==");
//		System.out.println(eagerComment.getComment());
//		System.out.println(eagerComment.getPost().getTitle());
	}

	private void exampleBasic() {
		/*
		 * Transient
		 */
		Account account = new Account();
		account.setUserName("jaenyeong");
		account.setPassword("jpa");

//		entityManager.persist(account);

		Study study = new Study();
		study.setName("Spring Data JPA");

		// Study table - @ManyToOne
		// Study 객체에서 Account PK를 FK 형태로 가지고 있음
//		study.setOwner(account);

		// Account table - @OneToMany
		// 조인 테이블로 생성됨
		// optional, account에만 관계 설정을 해주면 테이블에 매핑 데이터가 삽입되지 않음
//		account.getStudies().add(study);

		// required, 주인인 study에도 관계를 설정해줘야함
//		study.setOwner(account);

		// account.getStudies().add(study); study.setOwner(account);
		// 위 2개 호출
		account.addStudy(study);

		/*
		 * persistent
		 */
		// 핵심 API
		Session session = entityManager.unwrap(Session.class);
		session.save(account);
		session.save(study);

		// DB에서 직접 가져오지 않고 1차 캐싱된 데이터를 반환
		Account jaenyeong = session.load(Account.class, account.getId());
		jaenyeong.setUserName("Noah1");
		jaenyeong.setUserName("Noah2");
		// 최종적으로 데이터가 변경되지 않은경우 업데이트 쿼리가 발생하지 않음
		jaenyeong.setUserName("jaenyeong");
		System.out.println("==*--------------*-------------*==");
		System.out.println(jaenyeong.getUserName());
	}
}
