package com.jaenyeong.chapter_03_Spring_Data_JPA.entity;

import org.springframework.data.jpa.domain.Specification;

public class CommentSpecs {

	public static Specification<CommentJPA> isBest() {
		return (Specification<CommentJPA>) (root, query, builder)
				-> builder.isTrue(root.get(CommentJPA_.best));
	}

	public static Specification<CommentJPA> isGood() {
		return (Specification<CommentJPA>) (root, query, builder)
				-> builder.greaterThanOrEqualTo(root.get(CommentJPA_.up), 10);
	}
}
