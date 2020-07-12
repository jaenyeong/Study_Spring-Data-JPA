package com.jaenyeong.chapter_02_JPA;

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
		account.setName("noah2");
		account.setPassword("jpa");

//		entityManager.persist(account);

		// 핵심 API
		Session session = entityManager.unwrap(Session.class);
		session.save(account);
	}
}
