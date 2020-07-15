package com.jaenyeong.chapter_02_JPA.webCommon.domainClassConverter.controller;

import com.jaenyeong.chapter_02_JPA.webCommon.domainClassConverter.entity.WebPost;
import com.jaenyeong.chapter_02_JPA.webCommon.domainClassConverter.repository.WebPostRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebPostController {

	private final WebPostRepository webPostRepository;

	public WebPostController(WebPostRepository webPostRepository) {
		this.webPostRepository = webPostRepository;
	}

//	@GetMapping("/posts/{id}")
//	public String getPost(@PathVariable Long id) {
//		Optional<WebPost> byId = webPostRepository.findById(id);
//		WebPost webPost = byId.get();
//		return webPost.getTitle();
//	}

	// DomainClassConverter
	@GetMapping("/posts/{id}")
	public String getPost(@PathVariable("id") WebPost webPost) {
		return webPost.getTitle();
	}
}
