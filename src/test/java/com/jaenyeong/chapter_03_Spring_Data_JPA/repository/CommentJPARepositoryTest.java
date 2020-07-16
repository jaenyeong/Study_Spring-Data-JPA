package com.jaenyeong.chapter_03_Spring_Data_JPA.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentJPARepositoryTest {

	@Autowired
	CommentJPARepository comments;

	@Autowired
	PostJPARepository postJPARepository;

	@Test
	public void getComment() throws Exception {
		// fetch
		comments.getById(1L);

		System.out.println("*-----------------------------------------*");

		// lazy
		comments.findById(1L);
	}
}
