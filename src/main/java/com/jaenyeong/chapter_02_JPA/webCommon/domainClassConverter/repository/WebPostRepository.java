package com.jaenyeong.chapter_02_JPA.webCommon.domainClassConverter.repository;

import com.jaenyeong.chapter_02_JPA.webCommon.domainClassConverter.entity.WebPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WebPostRepository extends JpaRepository<WebPost, Long> {
}
