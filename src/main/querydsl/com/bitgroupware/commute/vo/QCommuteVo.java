package com.bitgroupware.commute.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommuteVo is a Querydsl query type for CommuteVo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCommuteVo extends EntityPathBase<CommuteVo> {

    private static final long serialVersionUID = 1624987908L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommuteVo commuteVo = new QCommuteVo("commuteVo");

    public final StringPath commuteDate = createString("commuteDate");

    public final NumberPath<Integer> commuteNo = createNumber("commuteNo", Integer.class);

    public final StringPath commuteOfftime = createString("commuteOfftime");

    public final StringPath commuteOntime = createString("commuteOntime");

    public final StringPath commuteStatus = createString("commuteStatus");

    public final com.bitgroupware.member.vo.QMemberVo memberVo;

    public QCommuteVo(String variable) {
        this(CommuteVo.class, forVariable(variable), INITS);
    }

    public QCommuteVo(Path<? extends CommuteVo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommuteVo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommuteVo(PathMetadata metadata, PathInits inits) {
        this(CommuteVo.class, metadata, inits);
    }

    public QCommuteVo(Class<? extends CommuteVo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberVo = inits.isInitialized("memberVo") ? new com.bitgroupware.member.vo.QMemberVo(forProperty("memberVo"), inits.get("memberVo")) : null;
    }

}

