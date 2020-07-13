package com.jaenyeong.chapter_02_JPA.repository;

import com.jaenyeong.chapter_02_JPA.entity.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

	@Autowired
	CustomCommentRepository customCommentRepository;

	@Autowired
	MyCommentRepository myCommentRepository;

	@Test
	public void crud() throws Exception {
		Comment comment = new Comment();
		comment.setComment("Hello Comment test 01");

		// custom
		customCommentRepository.save(comment);

		List<Comment> all = customCommentRepository.findAll();
		assertThat(all.size()).isEqualTo(1);

		// my
		myCommentRepository.save(comment);

		List<Comment> myALl = myCommentRepository.findAll();
		assertThat(myALl.size()).isEqualTo(1);

		long count = myCommentRepository.count();
		assertThat(count).isEqualTo(1);
	}
}
