package com.jaenyeong.chapter_02_JPA.domainEvent;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListenerTestConfig {

//	@Bean
//	public PostListener postListener() {
//		return new PostListener();
//	}

	// 리스너 클래스를 직접 구현하고 싶지 않은 경우
	// 아래와 같이 익명 클래스를 구현 또는 람다식으로 작성하여 넘겨줌
	@Bean
	public ApplicationListener<PostPublishedEvent> notCreatePostListener() {
		return event -> {
			System.out.println("*----------------------------------*");
			System.out.println(event.getPost().getTitle() + " is published");
			System.out.println("*----------------------------------*");
		};
	}
}
