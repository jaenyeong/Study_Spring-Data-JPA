package com.jaenyeong.chapter_02_JPA.repository.postRepo;

import com.jaenyeong.chapter_02_JPA.entity.Post;
import com.jaenyeong.chapter_02_JPA.repository.MyCommonRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

//public interface PostExtendCommonRepository extends MyCommonRepository<Post, Long> {
// queryDSL
public interface PostExtendCommonRepository extends MyCommonRepository<Post, Long>, QuerydslPredicateExecutor<Post> {
}
