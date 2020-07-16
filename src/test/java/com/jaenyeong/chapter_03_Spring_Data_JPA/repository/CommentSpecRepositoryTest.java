package com.jaenyeong.chapter_03_Spring_Data_JPA.repository;

import com.jaenyeong.chapter_03_Spring_Data_JPA.entity.CommentJPA;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jaenyeong.chapter_03_Spring_Data_JPA.entity.CommentSpecs.isBest;
import static com.jaenyeong.chapter_03_Spring_Data_JPA.entity.CommentSpecs.isGood;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentSpecRepositoryTest {

	@Autowired
	CommentSpecRepository comments;

	@Test
	public void specs() {
		Page<CommentJPA> pages = comments.findAll(isBest().and(isGood()), PageRequest.of(0, 10));
	}
}
