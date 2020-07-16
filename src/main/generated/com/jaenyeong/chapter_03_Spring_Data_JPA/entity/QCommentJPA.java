package com.jaenyeong.chapter_03_Spring_Data_JPA.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommentJPA is a Querydsl query type for CommentJPA
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCommentJPA extends EntityPathBase<CommentJPA> {

    private static final long serialVersionUID = -562307930L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommentJPA commentJPA = new QCommentJPA("commentJPA");

    public final BooleanPath best = createBoolean("best");

    public final StringPath comment = createString("comment");

    public final NumberPath<Integer> down = createNumber("down", Integer.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QPostJPA post;

    public final NumberPath<Integer> up = createNumber("up", Integer.class);

    public QCommentJPA(String variable) {
        this(CommentJPA.class, forVariable(variable), INITS);
    }

    public QCommentJPA(Path<? extends CommentJPA> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommentJPA(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommentJPA(PathMetadata metadata, PathInits inits) {
        this(CommentJPA.class, metadata, inits);
    }

    public QCommentJPA(Class<? extends CommentJPA> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPostJPA(forProperty("post")) : null;
    }

}

