package com.jaenyeong.chapter_02_JPA.domainEvent;

import org.springframework.context.event.EventListener;

// ApplicationListener를 구현 또는 메서드에 @EventListener 어노테이션 태깅
// 해당 리스너가 빈으로 등록 되어 있어야 함
//public class PostListener implements ApplicationListener<PostPublishedEvent> {
public class PostListener {

//	@Override
	@EventListener
	public void onApplicationEvent(PostPublishedEvent event) {
		System.out.println("*----------------------------------*");
		System.out.println(event.getPost().getTitle() + " is published");
		System.out.println("*----------------------------------*");
	}
}
