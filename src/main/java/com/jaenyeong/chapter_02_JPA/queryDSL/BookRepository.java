package com.jaenyeong.chapter_02_JPA.queryDSL;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;

//public interface BookRepository extends JpaRepository<Book, Long> {
//public interface BookRepository extends JpaRepository<Book, Long>, QuerydslPredicateExecutor<Book> {
public interface BookRepository extends CustomRepository<Book, Long>, QuerydslPredicateExecutor<Book> {
}
