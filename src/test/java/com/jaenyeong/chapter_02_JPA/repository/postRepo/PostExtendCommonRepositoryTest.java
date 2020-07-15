package com.jaenyeong.chapter_02_JPA.repository.postRepo;

import com.jaenyeong.chapter_02_JPA.entity.Post;
import com.jaenyeong.chapter_02_JPA.entity.QPost;
import com.querydsl.core.types.Predicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostExtendCommonRepositoryTest {

	@Autowired
	PostExtendCommonRepository postRepository;

	@Test
	public void crud() throws Exception {
		Post post = new Post();
		post.setTitle("hibernate");

		// EntityManager 객체가 Null이어서 해당 데이터를 찾지 못함
//		assertThat(postRepository.contains(post)).isFalse();

		postRepository.save(post);

		// EntityManager 객체가 Null이어서 해당 데이터를 찾지 못함
//		assertThat(postRepository.contains(post)).isTrue();

		postRepository.delete(post);
		postRepository.flush();
	}

	@Test
	public void queryDsl() throws Exception {
		Post post = new Post();
		post.setTitle("hibernate");
		postRepository.save(post.publish());

		Predicate predicate = QPost.post.title.containsIgnoreCase("Hi");
		Optional<Post> one = postRepository.findOne(predicate);
		assertThat(one).isNotEmpty();
	}
}
