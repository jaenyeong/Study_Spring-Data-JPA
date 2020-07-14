package com.jaenyeong.chapter_02_JPA.repository.postRepo;

import com.jaenyeong.chapter_02_JPA.entity.Post;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
// @DataJpaTest 스프링 부트 기능, 데이터 레이어만 테스트 (@DataJdbcTest 어노테이션과 헷갈리지 말 것)
// @DataJpaTest는 트랜잭션인데 테스트에서는 모두 롤백처리 하기 때문에 애당초 데이터 삽입 쿼리를 날리지 않음
// 테스트시 Postgres 데이터베이스를 사용하지 않고 H2 데이터베이스를 사용 (의존성 추가된 경우예)
@DataJpaTest
public class JpaPostRepositoryTest {

	@Autowired
	JpaPostRepository postRepository;

	@Test
    // @DataJpaTest 트랜잭션 롤백 무시, 삽입 쿼리 확인용
	@Rollback(false)
	public void crudRepository() throws Exception {
		// Junit이 아닌 AssertJ의 assertThat 사용

		// given
		Post post = new Post();
		post.setTitle("Hello spring data common");
		assertThat(post.getId()).isNull();

		// when
		Post newPost = postRepository.save(post);

		// then
		assertThat(newPost.getId()).isNotNull();

		// when
		List<Post> posts = postRepository.findAll();

		// then
		assertThat(posts.size()).isEqualTo(1);
		assertThat(posts).contains(newPost);

		// when
		// 페이징 처리
		Page<Post> page = postRepository.findAll(PageRequest.of(0, 10));

		// then
		assertThat(page.getTotalElements()).isEqualTo(1);
		assertThat(page.getNumber()).isEqualTo(0);
		assertThat(page.getSize()).isEqualTo(10);
		assertThat(page.getNumberOfElements()).isEqualTo(1);

		// when
		Page<Post> page2 = postRepository.findByTitleContains("spring", PageRequest.of(0, 10));

		// then
		assertThat(page2.getTotalElements()).isEqualTo(1);
		assertThat(page2.getNumber()).isEqualTo(0);
		assertThat(page2.getSize()).isEqualTo(10);
		assertThat(page2.getNumberOfElements()).isEqualTo(1);

		// when
		long cntSpring = postRepository.countByTitleContains("spring");

		// then
		assertThat(cntSpring).isEqualTo(1);
	}

	@Test
	public void crud() throws Exception {
		postRepository.findMyPost();

		Post post = new Post();
		post.setTitle("hibernate");
		postRepository.save(post);

		// 삽입 후 바로 삭제 및 테스트 종료가 된다면 데이터 삽입 쿼리가 날라가지 않음
		// 따라서 저장 후 바로 결과값 조회 쿼리를 호출한다면 그 전 삽입 쿼리가 날라가야 된다고 판단하여
		// 이 전에 삽입한 쿼리를 flush하게 됨
		postRepository.findMyPost();

		postRepository.delete(post);

		// 테스트 특성상 롤백처리 되기 때문에 테스트 종료되면 삭제 쿼리가 의미가 없다고 판단하여 위 삭제 쿼리가 호출되지 않음
		// 따라서 강제로 삭제 쿼리 전송을 위해 flush 호출
		postRepository.flush();
	}
}
