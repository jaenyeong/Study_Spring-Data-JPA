package com.jaenyeong.chapter_03_Spring_Data_JPA.repository;

import com.jaenyeong.chapter_03_Spring_Data_JPA.entity.PostJPA;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostJPARepositoryTest {

	@Autowired
	private PostJPARepository postJPARepository;

	@PersistenceContext
	private EntityManager entityManager;

	@Test
	public void crud() throws Exception {
		PostJPA post = new PostJPA();
		post.setTitle("JPA");
		postJPARepository.save(post);

		List<PostJPA> all = postJPARepository.findAll();
		assertThat(all.size()).isEqualTo(1);
	}

	@Test
	public void save() throws Exception {
		PostJPA post1 = new PostJPA();
		post1.setTitle("JPA");
		// 삽입 쿼리 호출 - persist
		PostJPA savePost1 = postJPARepository.save(post1);

		assertThat(entityManager.contains(post1)).isTrue();
		assertThat(entityManager.contains(savePost1)).isTrue();
		assertThat(post1 == savePost1).isTrue();

		PostJPA post2 = new PostJPA();
		post2.setId(post1.getId());
		post2.setTitle("hibernate");
		// 같은 아이디 값으로 인해 삽입이 아닌 업데이트 쿼리 호출 - merge
		// 새로운 객체가 아닌경우 EntityManager의 merge 메서드로 전달
		// "JPA" 대신 "hibernate" 값을 업데이트 하게 됨
		// 파라미터로 전달받은 객체의 복사본을 생성 > 복사본을 영속화, 복사본 객체를 반환
		PostJPA savePost2 = postJPARepository.save(post2);

		assertThat(entityManager.contains(post2)).isFalse();
		assertThat(entityManager.contains(savePost2)).isTrue();
		assertThat(post2 == savePost2).isFalse();

		// 따라서 실수를 줄이려면 파라미터로 전달한 객체의 인스턴스를 사용하지 말고 결과값으로 반환해주는 객체의 인스턴스를 사용할 것

		// 아래와 같이 파라미터로 넘긴 객체의 인스턴스를 사용하면 "hibernate"로 업데이트 쿼리를 전송
//		post2.setTitle("수정 안됨");

		// 아래와 같이 반환된 객체의 인스턴스를 사용하면 "수정 완료"로 업데이트 쿼리를 전송
		savePost2.setTitle("수정 완료");

		List<PostJPA> all = postJPARepository.findAll();
		assertThat(all.size()).isEqualTo(1);
	}
}
