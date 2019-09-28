package com.bitgroupware.community.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoticeFileVo is a Querydsl query type for NoticeFileVo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNoticeFileVo extends EntityPathBase<NoticeFileVo> {

    private static final long serialVersionUID = -2059862421L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoticeFileVo noticeFileVo = new QNoticeFileVo("noticeFileVo");

    public final QNoticeVo notice;

    public final StringPath ntFileName = createString("ntFileName");

    public final NumberPath<Integer> ntFileNo = createNumber("ntFileNo", Integer.class);

    public final StringPath ntFileType = createString("ntFileType");

    public final StringPath ntFileUrl = createString("ntFileUrl");

    public QNoticeFileVo(String variable) {
        this(NoticeFileVo.class, forVariable(variable), INITS);
    }

    public QNoticeFileVo(Path<? extends NoticeFileVo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoticeFileVo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoticeFileVo(PathMetadata metadata, PathInits inits) {
        this(NoticeFileVo.class, metadata, inits);
    }

    public QNoticeFileVo(Class<? extends NoticeFileVo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.notice = inits.isInitialized("notice") ? new QNoticeVo(forProperty("notice"), inits.get("notice")) : null;
    }

}

