package com.jaenyeong;

import com.jaenyeong.chapter_02_JPA.Jaenyeong.JaenyeongRegistrar;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
// 스프링 부트가 아니었다면 @Configuration가 태깅된 클래스에 @EnableJpaRepositories를 태깅했어야 함
//@EnableJpaRepositories
//@Configuration
// 빈 임포트
@Import(JaenyeongRegistrar.class)
// @Async 어노테이션이 태깅된 비동기 쿼리를 위해 @EnableAsync 어노테이션 태깅
// 하지만 예제에서는 비동기 처리가 원활히 이루어지지 않고 테스트 코드 작성이 어려움
// 따라서 가급적 사용을 권장하지 않음
//@EnableAsync
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
}
