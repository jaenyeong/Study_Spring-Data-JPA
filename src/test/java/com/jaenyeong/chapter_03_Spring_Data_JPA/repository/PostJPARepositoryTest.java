package com.jaenyeong.chapter_03_Spring_Data_JPA.repository;

import com.jaenyeong.chapter_03_Spring_Data_JPA.entity.PostJPA;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PostJPARepositoryTest {

	@Autowired
	private PostJPARepository postJPARepository;

	@Test
	public void crud() throws Exception {
		PostJPA post = new PostJPA();
		post.setTitle("JPA");
		postJPARepository.save(post);

		List<PostJPA> all = postJPARepository.findAll();
		assertThat(all.size()).isEqualTo(1);
	}
}
