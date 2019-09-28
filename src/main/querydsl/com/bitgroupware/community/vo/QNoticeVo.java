package com.bitgroupware.community.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QNoticeVo is a Querydsl query type for NoticeVo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QNoticeVo extends EntityPathBase<NoticeVo> {

    private static final long serialVersionUID = 2104391375L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QNoticeVo noticeVo = new QNoticeVo("noticeVo");

    public final com.bitgroupware.member.vo.QMemberVo member;

    public final ListPath<NoticeFileVo, QNoticeFileVo> noticeFileList = this.<NoticeFileVo, QNoticeFileVo>createList("noticeFileList", NoticeFileVo.class, QNoticeFileVo.class, PathInits.DIRECT2);

    public final StringPath ntCate = createString("ntCate");

    public final NumberPath<Integer> ntCnt = createNumber("ntCnt", Integer.class);

    public final StringPath ntContent = createString("ntContent");

    public final DateTimePath<java.util.Date> ntDate = createDateTime("ntDate", java.util.Date.class);

    public final StringPath ntDelCheck = createString("ntDelCheck");

    public final StringPath ntFileCheck = createString("ntFileCheck");

    public final NumberPath<Integer> ntNo = createNumber("ntNo", Integer.class);

    public final StringPath ntTitle = createString("ntTitle");

    public QNoticeVo(String variable) {
        this(NoticeVo.class, forVariable(variable), INITS);
    }

    public QNoticeVo(Path<? extends NoticeVo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QNoticeVo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QNoticeVo(PathMetadata metadata, PathInits inits) {
        this(NoticeVo.class, metadata, inits);
    }

    public QNoticeVo(Class<? extends NoticeVo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.bitgroupware.member.vo.QMemberVo(forProperty("member"), inits.get("member")) : null;
    }

}

