package com.jaenyeong.chapter_02_JPA.repository;

import com.jaenyeong.chapter_02_JPA.entity.Comment;
import com.jaenyeong.chapter_02_JPA.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;

public interface MyCommentRepository extends CommonRepository<Comment, Long> {

//	@Query(value = "SELECT C FROM Comment as C", nativeQuery = true)
	// title이라는 필드가 Comment에 없기 때문에 에러 발생
//	List<Comment> findByTitleContains(String keyword);
//	List<Comment> findByCommentContains(String keyword);
//	List<Comment> findByCommentContainsIgnoreCase(String keyword);
//	List<Comment> findByCommentContainsIgnoreCaseAndLikeCountGreaterThan(String keyword, int likeCount);
//	List<Comment> findByCommentContainsIgnoreCaseOrderByLikeCountDesc(String keyword);
//	Page<Comment> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);

//	Stream<Comment> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);

	// 비동기 쿼리
	// @Async 어노테이션만으로는 비동기적으로 수행되지 않음
	// 이 어노테이션은 백그라운드 스레드 풀에 해당 작업을 위임, 별도의 스레드에서 동작 시키는 것
	@Async
//	Future<List<Comment>> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);
	ListenableFuture<List<Comment>> findByCommentContainsIgnoreCase(String keyword, Pageable pageable);

	Page<Comment> findByLikeCountGreaterThanAndPost(int likeCount, Post post, Pageable pageable);
}
