package com.jaenyeong.chapter_02_JPA.domainEvent;

import com.jaenyeong.chapter_02_JPA.entity.Post;
import com.jaenyeong.chapter_02_JPA.repository.postRepo.PostExtendCommonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
// 테스트 실행시 ListenerTestConfig 클래스를 가져와 실행하여 내부에 PostListener 클래스가 빈으로 등록됨
@Import(ListenerTestConfig.class)
public class PostPublishedEventTest {

	@Autowired
	PostExtendCommonRepository postRepository;

	@Autowired
	ApplicationContext applicationContext;

	@Test
	public void event() throws Exception {
		Post post = new Post();
		post.setTitle("hibernate");
		PostPublishedEvent event = new PostPublishedEvent(post);

		applicationContext.publishEvent(event);
	}

	@Test
	public void aggregateEvent() throws Exception {
		Post post = new Post();
		post.setTitle("hibernate");

		assertThat(postRepository.contains(post)).isFalse();

		// 퍼블리싱
		postRepository.save(post.publish());

		assertThat(postRepository.contains(post)).isTrue();

		postRepository.delete(post);
		postRepository.flush();
	}
}
