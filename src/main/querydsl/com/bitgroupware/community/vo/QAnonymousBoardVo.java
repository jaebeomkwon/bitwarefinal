package com.bitgroupware.community.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QAnonymousBoardVo is a Querydsl query type for AnonymousBoardVo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAnonymousBoardVo extends EntityPathBase<AnonymousBoardVo> {

    private static final long serialVersionUID = -997342160L;

    public static final QAnonymousBoardVo anonymousBoardVo = new QAnonymousBoardVo("anonymousBoardVo");

    public final NumberPath<Integer> bcnt = createNumber("bcnt", Integer.class);

    public final StringPath bcontent = createString("bcontent");

    public final DateTimePath<java.util.Date> bdate = createDateTime("bdate", java.util.Date.class);

    public final NumberPath<Integer> bgroup = createNumber("bgroup", Integer.class);

    public final NumberPath<Integer> bindent = createNumber("bindent", Integer.class);

    public final NumberPath<Integer> bno = createNumber("bno", Integer.class);

    public final StringPath bpw = createString("bpw");

    public final NumberPath<Integer> bstep = createNumber("bstep", Integer.class);

    public final StringPath btitle = createString("btitle");

    public QAnonymousBoardVo(String variable) {
        super(AnonymousBoardVo.class, forVariable(variable));
    }

    public QAnonymousBoardVo(Path<? extends AnonymousBoardVo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAnonymousBoardVo(PathMetadata metadata) {
        super(AnonymousBoardVo.class, metadata);
    }

}

