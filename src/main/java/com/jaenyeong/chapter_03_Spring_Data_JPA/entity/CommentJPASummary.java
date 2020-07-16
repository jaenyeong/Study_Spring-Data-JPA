package com.jaenyeong.chapter_03_Spring_Data_JPA.entity;

public interface CommentJPASummary {
	String getComment();

	int getUp();

	int getDown();

	// 오픈 프로젝션
	// 성능 최적화는 못함
//	@Value("#{target.up + ' ' + target.down}")
//	String getVotes();

	// 이 방법을 가장 추천
	default String getVotes() {
		return getUp() + " " + getDown();
	}
}
