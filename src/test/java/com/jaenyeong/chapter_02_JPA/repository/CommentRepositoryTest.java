package com.jaenyeong.chapter_02_JPA.repository;

import com.jaenyeong.chapter_02_JPA.entity.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {

	@Autowired
	CustomCommentRepository customCommentRepository;

	@Autowired
	MyCommentRepository myCommentRepository;

	@Test
	public void crud() throws Exception {
		Comment comment = new Comment();
		comment.setComment("Hello Comment test 01");

		// custom
		customCommentRepository.save(comment);

		List<Comment> all = customCommentRepository.findAll();
		assertThat(all.size()).isEqualTo(1);

		// my
		myCommentRepository.save(comment);

		List<Comment> myALl = myCommentRepository.findAll();
		assertThat(myALl.size()).isEqualTo(1);

		long count = myCommentRepository.count();
		assertThat(count).isEqualTo(1);
	}

	@Test
	public void nullCheck() throws Exception {
		// 컬렉션을 반환하는 경우에는 Null을 반환하지 않고 빈 컬렉션을 반환
		// Null 체크할 필요 없음
		List<Comment> comments = myCommentRepository.findAll();
		assertThat(comments).isEmpty();

		// findById 메서드의 반환 타입을 Optional로 설정한 경우
//		Optional<Comment> byId = myCommentRepository.findById(100L);
//		assertThat(byId).isEmpty();
//		Comment comment = byId.orElseThrow(IllegalArgumentException::new);

		// save 메서드 호출시 파라미터 값이 null인지 @NonNull 어노테이션으로 체크
//		myCommentRepository.save(null);
	}
}
