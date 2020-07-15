package com.jaenyeong.chapter_02_JPA.queryDSL;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BookRepositoryTest {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	EntityManager entityManager;

	@Test
	public void test() throws Exception {
		Book book = new Book();
		book.setTitle("spring");
		book.setContent("data");

//		bookRepository.save(book);
//		assertEquals(1, bookRepository.findAll().size());
//
//		Optional<Book> ring = bookRepository.findOne(QBook.book.title.contains("ring"));
//		assertTrue(ring.isPresent());
//
//		Optional<Book> jpa = bookRepository.findOne(QBook.book.title.contains("jpa"));
//		assertTrue(jpa.isEmpty());

		Book newBook = bookRepository.save(book);
		assertEquals(1, bookRepository.findAll().size());

		// JpaRepository 클래스 상속에서 contains 메서드를 정의한 CustomRepository 클래스 상속으로 변경

		// EntityManager 객체가 Null이어서 해당 데이터를 찾지 못함
//		assertTrue(bookRepository.contains(newBook));

		Optional<Book> ring = bookRepository.findOne(QBook.book.title.contains("ring"));
		assertTrue(ring.isPresent());
	}
}
