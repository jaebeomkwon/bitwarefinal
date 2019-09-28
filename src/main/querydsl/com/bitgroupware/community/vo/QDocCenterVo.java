package com.bitgroupware.community.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocCenterVo is a Querydsl query type for DocCenterVo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDocCenterVo extends EntityPathBase<DocCenterVo> {

    private static final long serialVersionUID = -4661400L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocCenterVo docCenterVo = new QDocCenterVo("docCenterVo");

    public final StringPath docCate = createString("docCate");

    public final ListPath<DocCenterFileVo, QDocCenterFileVo> docCenterFileList = this.<DocCenterFileVo, QDocCenterFileVo>createList("docCenterFileList", DocCenterFileVo.class, QDocCenterFileVo.class, PathInits.DIRECT2);

    public final NumberPath<Integer> docCnt = createNumber("docCnt", Integer.class);

    public final StringPath docContent = createString("docContent");

    public final DateTimePath<java.util.Date> docDate = createDateTime("docDate", java.util.Date.class);

    public final StringPath docDelCheck = createString("docDelCheck");

    public final StringPath docFileCheck = createString("docFileCheck");

    public final NumberPath<Integer> docNo = createNumber("docNo", Integer.class);

    public final StringPath docTitle = createString("docTitle");

    public final com.bitgroupware.member.vo.QMemberVo member;

    public QDocCenterVo(String variable) {
        this(DocCenterVo.class, forVariable(variable), INITS);
    }

    public QDocCenterVo(Path<? extends DocCenterVo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocCenterVo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocCenterVo(PathMetadata metadata, PathInits inits) {
        this(DocCenterVo.class, metadata, inits);
    }

    public QDocCenterVo(Class<? extends DocCenterVo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.bitgroupware.member.vo.QMemberVo(forProperty("member"), inits.get("member")) : null;
    }

}

