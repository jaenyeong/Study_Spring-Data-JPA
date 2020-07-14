package com.jaenyeong;

import com.jaenyeong.chapter_02_JPA.Jaenyeong.JaenyeongRegistrar;
import com.jaenyeong.chapter_02_JPA.repository.SimpleMyCommonRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
// 커스텀 레퍼지토리 구현시 구현 클래스명의 접미어 설정
// default = "impl";
//@EnableJpaRepositories(repositoryImplementationPostfix = "default")
// Common repository 생성시 @EnableJpaRepositories 어노테이션 repositoryBaseClass 속성에 설정
@EnableJpaRepositories(repositoryBaseClass = SimpleMyCommonRepository.class)
public class JpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JpaApplication.class, args);
	}
}
