package com.jaenyeong.chapter_02_JPA.webCommon.domainClassConverter.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QWebPost is a Querydsl query type for WebPost
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWebPost extends EntityPathBase<WebPost> {

    private static final long serialVersionUID = -12344099L;

    public static final QWebPost webPost = new QWebPost("webPost");

    public final DateTimePath<java.util.Date> created = createDateTime("created", java.util.Date.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath title = createString("title");

    public QWebPost(String variable) {
        super(WebPost.class, forVariable(variable));
    }

    public QWebPost(Path<? extends WebPost> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWebPost(PathMetadata metadata) {
        super(WebPost.class, metadata);
    }

}

