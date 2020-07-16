package com.jaenyeong.chapter_03_Spring_Data_JPA.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostJPA is a Querydsl query type for PostJPA
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPostJPA extends EntityPathBase<PostJPA> {

    private static final long serialVersionUID = -2031503439L;

    public static final QPostJPA postJPA = new QPostJPA("postJPA");

    public final SetPath<com.jaenyeong.chapter_02_JPA.entity.Comment, com.jaenyeong.chapter_02_JPA.entity.QComment> comments = this.<com.jaenyeong.chapter_02_JPA.entity.Comment, com.jaenyeong.chapter_02_JPA.entity.QComment>createSet("comments", com.jaenyeong.chapter_02_JPA.entity.Comment.class, com.jaenyeong.chapter_02_JPA.entity.QComment.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    public QPostJPA(String variable) {
        super(PostJPA.class, forVariable(variable));
    }

    public QPostJPA(Path<? extends PostJPA> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPostJPA(PathMetadata metadata) {
        super(PostJPA.class, metadata);
    }

}

