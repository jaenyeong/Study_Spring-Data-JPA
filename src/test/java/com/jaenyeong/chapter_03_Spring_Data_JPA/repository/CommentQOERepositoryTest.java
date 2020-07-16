package com.jaenyeong.chapter_03_Spring_Data_JPA.repository;

import com.jaenyeong.chapter_03_Spring_Data_JPA.entity.CommentJPA;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentQOERepositoryTest {

	@Autowired
	CommentQOERepository comments;

	@Test
	public void queryByExample() throws Exception {
		CommentJPA prove = new CommentJPA();
		prove.setBest(true);

		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withIgnorePaths("up", "down");

		Example<CommentJPA> example = Example.of(prove, exampleMatcher);

		comments.findAll(example);
	}
}
