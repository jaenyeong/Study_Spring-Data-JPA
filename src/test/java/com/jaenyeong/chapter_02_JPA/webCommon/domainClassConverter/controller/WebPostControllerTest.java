package com.jaenyeong.chapter_02_JPA.webCommon.domainClassConverter.controller;

import com.jaenyeong.chapter_02_JPA.webCommon.domainClassConverter.entity.WebPost;
import com.jaenyeong.chapter_02_JPA.webCommon.domainClassConverter.repository.WebPostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 모든 빈이 등록이되는 통합 테스트
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
// application.properties(yaml) 파일에 있는 빈 목록들까지 사용하지 않는 경우
// test 경로에 application-test.properties 파일을 생성하고 사용하는 빈 설정만 작성한 후
// @ActiveProfiles("test") 어노테이션 태깅
//@ActiveProfiles("test")
public class WebPostControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	WebPostRepository webPostRepository;

	@Test
	public void getPost() throws Exception {
		WebPost webPost = new WebPost();
		webPost.setTitle("JPA");
		webPostRepository.save(webPost);

		mockMvc.perform(
				get("/posts/" + webPost.getId()))
				.andDo(print())
				.andExpect(status().isOk())
		.andExpect(content().string("JPA"));
	}
}
