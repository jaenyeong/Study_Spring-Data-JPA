package com.jaenyeong.chapter_02_JPA.webCommon.domainClassConverter.controller;

import com.jaenyeong.chapter_02_JPA.webCommon.domainClassConverter.entity.WebPost;
import com.jaenyeong.chapter_02_JPA.webCommon.domainClassConverter.repository.WebPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebPostController {

	private final WebPostRepository posts;

	public WebPostController(WebPostRepository posts) {
		this.posts = posts;
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

	// pageable, sort
	@GetMapping("/posts/")
	public Page<WebPost> getPosts(Pageable pageable) {
		return posts.findAll(pageable);
	}
}
