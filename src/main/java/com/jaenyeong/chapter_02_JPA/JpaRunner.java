package com.jaenyeong.chapter_02_JPA;

import com.jaenyeong.chapter_02_JPA.entity.Account;
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

		// 핵심 API
		Session session = entityManager.unwrap(Session.class);
		session.save(account);
		session.save(study);
	}
}
