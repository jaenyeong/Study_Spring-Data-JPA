package com.jaenyeong.chapter_02_JPA.repository;

import com.jaenyeong.chapter_02_JPA.entity.Comment;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.List;

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

	@Test
	public void queryMethodName() throws Exception {
		createComment(100, "Spring Data JPA");
		createComment(55, "HIBERNATE SPRING");

//		List<Comment> comments = myCommentRepository.findByCommentContainsIgnoreCaseOrderByLikeCountDesc("Spring");
//		assertThat(comments.size()).isEqualTo(2);
//		assertThat(comments).first().hasFieldOrPropertyWithValue("likeCount", 100);

		PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "LikeCount"));

//		Page<Comment> comments = myCommentRepository.findByCommentContainsIgnoreCase("Spring", pageRequest);
//		assertThat(comments.getNumberOfElements()).isEqualTo(2);
//		assertThat(comments).first().hasFieldOrPropertyWithValue("likeCount", 100);

		// Stream은 닫아줘야 하기 때문에 try/catch 블록에 리소스로 받음
//		try (Stream<Comment> comments = myCommentRepository.findByCommentContainsIgnoreCase("Spring", pageRequest);) {
//			Comment firstComment = comments.findFirst().get();
//			assertThat(firstComment.getLikeCount()).isEqualTo(100);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

	@Test
	public void asyncQuery() throws Exception {
		createComment(100, "Spring Data JPA");
		createComment(55, "HIBERNATE SPRING");

//		myCommentRepository.flush();
//		List<Comment> all = myCommentRepository.findAll();
//		assertThat(all.size()).isEqualTo(2);

		PageRequest pageRequest =
				PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "LikeCount"));

		// 이 때는 블로킹이 되지 않음
//		Future<List<Comment>> future =
//				myCommentRepository.findByCommentContainsIgnoreCase("Spring", pageRequest);

		// 결과 여부 확인
//		future.isDone();
//		System.out.println("*--------------------====*====-------------------*");
//		System.out.println("is Done? " + future.isDone());

		// 결과가 나올때까지 대기 - 이때 블로킹 됨
//		List<Comment> comments = future.get();
//		comments.forEach(System.out::println);

		// 위 테스팅 만으로는 비동기 처리가 이루어지지 않음
		// @Async 어노테이션이 태깅된 쿼리의 비동기 처리를 위해
		// JpaApplication 클래스 파일에 @EnableAsync 어노테이션 태깅해서 사용해야 함
		// 하지만 예제에서는 태깅 후에도 비동기 처리가 원활히 이루어지지 않고 테스트 코드 작성이 어려움
		// 따라서 가급적 사용을 권하지 않음

		// 아래 처리도 마찬가지로
		// JpaApplication 클래스 파일에 @EnableAsync 어노테이션 태깅해서 사용해야 함
		// 하지만 테스트 진행 시에 아래 테스트 코드가 없다고 판단하여
		// 위 데이터 삽입 쿼리 또한 실행되지 않음
		// 테스트코드는 전적으로 롤백처리 되기 때문에
		// 예제 태스트 코드에서 아무런 작업을 수행하지 않는다고 판단하면 데이터를 삽압하지 않음
		ListenableFuture<List<Comment>> future =
				myCommentRepository.findByCommentContainsIgnoreCase("Spring", pageRequest);

		System.out.println("*--------------------====*====-------------------*");
		System.out.println("is Done? " + future.isDone());

		// 언제 호출될지 모름
		future.addCallback(new ListenableFutureCallback<>() {
			@Override
			public void onFailure(Throwable ex) {
				System.out.println(ex);
			}

			@Override
			public void onSuccess(@Nullable List<Comment> result) {
				// 이 영역이 실행되기 전에 메인 스레드가 종료되어 결과 출력이 되지 않음
				System.out.println("Async *--------------------====*====-------------------*");
				System.out.println(result.size());
				result.forEach(System.out::println);
			}
		});

//		Thread.sleep(5000L);
	}

	private void createComment(int likeCount, String data) {
		Comment comment = new Comment();
		comment.setLikeCount(likeCount);
		comment.setComment(data);
		myCommentRepository.save(comment);
	}
}
