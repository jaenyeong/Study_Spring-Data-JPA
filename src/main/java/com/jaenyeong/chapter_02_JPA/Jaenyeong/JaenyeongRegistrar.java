package com.jaenyeong.chapter_02_JPA.Jaenyeong;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class JaenyeongRegistrar implements ImportBeanDefinitionRegistrar {

	// JPA Repository 객체를 찾아 빈으로 등록하는 동작 원리
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
		genericBeanDefinition.setBeanClass(Jaenyeong.class);
		genericBeanDefinition.getPropertyValues().add("name", "noah");

		registry.registerBeanDefinition("jaenyeong", genericBeanDefinition);
	}
}
