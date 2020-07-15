package com.jaenyeong.chapter_03_Spring_Data_JPA.repository;

import com.jaenyeong.chapter_03_Spring_Data_JPA.entity.PostJPA;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJPARepository extends JpaRepository<PostJPA, Long> {
}
