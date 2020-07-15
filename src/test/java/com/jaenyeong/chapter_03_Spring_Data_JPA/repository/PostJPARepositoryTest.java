package com.jaenyeong.chapter_03_Spring_Data_JPA.repository;

import com.jaenyeong.chapter_03_Spring_Data_JPA.entity.PostJPA;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

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

	@Test
	public void findByTitleStartsWith() throws Exception {
		PostJPA post = new PostJPA();
		post.setTitle("Spring Data JPA");
		postJPARepository.save(post);

		List<PostJPA> findPosts = postJPARepository.findByTitleStartsWith("Spring");
		assertThat(findPosts.size()).isEqualTo(1);
	}

	@Test
	public void findByTitle() throws Exception {
		PostJPA post = new PostJPA();
		post.setTitle("Spring Data JPA");
		postJPARepository.save(post);

//		List<PostJPA> findPosts = postJPARepository.findByTitle("Spring Data JPA");

		// Sort
		List<PostJPA> findPosts1 = postJPARepository.findByTitle("Spring Data JPA", Sort.by("title"));
		assertThat(findPosts1.size()).isEqualTo(1);

		// 아래와 같은 함수는 수용하지 않음
//		List<PostJPA> findPosts2 = postJPARepository.findByTitle("Spring Data JPA", Sort.by("LENGTH(title)"));

		List<PostJPA> findPosts3 = postJPARepository.findByTitle("Spring Data JPA", JpaSort.unsafe("LENGTH(title)"));
		assertThat(findPosts3.size()).isEqualTo(1);
	}

	@Test
	public void updateTitle() throws Exception {
		PostJPA post = new PostJPA();
		post.setTitle("Spring Data JPA");
		PostJPA savePost = postJPARepository.save(post);

//		int updateCount = postJPARepository.updateTitle("hibernate", savePost.getId());
//		assertThat(updateCount).isEqualTo(1);

		// 업데이트 쿼리는 데이터베이스에 값을 변경하는 쿼리만 호출, persistence context 캐시를 변경하진 않음
		// 따라서 업데이트 쿼리는 전송 되지만 persistence context 캐시에 있는 값을 반환할 뿐
		// 조회 쿼리를 전송하여 값을 가져오지 않기 때문에 findById 메서드 호출로 가져온 PostJPA의 title 값은 "Spring Data JPA" 반환
		// @Modifying(clearAutomatically = true) 어노테이션으로 persistence context 캐시를 비워주면
		// findById 메서드 호출 시 데이터 조회 쿼리를 통해 값을 가져옴
//		Optional<PostJPA> byId = postJPARepository.findById(savePost.getId());
//		assertThat(byId.get().getTitle()).isEqualTo("hibernate");

		// 결론적으로 가급적 별도의 쿼리를 호출하지 말고 아래와 같이 persistence context 캐시를 이용한 처리를 권장
		savePost.setTitle("스프링 데이터 JPA");
		List<PostJPA> all = postJPARepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo("스프링 데이터 JPA");
	}
}
