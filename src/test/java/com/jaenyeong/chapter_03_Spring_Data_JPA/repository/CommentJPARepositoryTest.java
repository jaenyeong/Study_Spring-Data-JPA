package com.jaenyeong.chapter_03_Spring_Data_JPA.repository;

import com.jaenyeong.chapter_03_Spring_Data_JPA.entity.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@DataJpaTest
// Auditing 테스트를 위해 변경
@SpringBootTest
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

	@Test
	public void projection() {
//		comments.findByPost_Id(1L);

		PostJPA post = new PostJPA();
		post.setTitle("jpa");
		PostJPA savePost = postJPARepository.save(post);

		CommentJPA comment = new CommentJPA();
		comment.setComment("Spring Data JPA projection");
		comment.setPost(savePost);
		comment.setUp(10);
		comment.setDown(1);

		comments.save(comment);

		comments.findByPost_Id(savePost.getId(), CommentJPASummary.class).forEach(c -> {
			System.out.println("**************----------------**************");
			System.out.println(c.getVotes());
		});

		comments.findByPost_Id(savePost.getId(), CommentJPASummaryClazz.class).forEach(c -> {
			System.out.println("**************----------------**************");
			System.out.println(c.getVotes());
		});

		comments.findByPost_Id(savePost.getId(), CommentJPAOnly.class).forEach(c -> {
			System.out.println("**************----------------**************");
			System.out.println(c.getComment());
		});
	}
}
