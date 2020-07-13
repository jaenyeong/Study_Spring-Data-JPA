package com.jaenyeong;

import com.jaenyeong.chapter_02_JPA.Jaenyeong.JaenyeongRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
// 스프링 부트가 아니었다면 @Configuration가 태깅된 클래스에 @EnableJpaRepositories를 태깅했어야 함
//@EnableJpaRepositories
//@Configuration
// 빈 임포트
@Import(JaenyeongRegistrar.class)
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
}
