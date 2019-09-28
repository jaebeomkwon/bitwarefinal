package com.bitgroupware.community.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDocCenterFileVo is a Querydsl query type for DocCenterFileVo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDocCenterFileVo extends EntityPathBase<DocCenterFileVo> {

    private static final long serialVersionUID = -1803849084L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDocCenterFileVo docCenterFileVo = new QDocCenterFileVo("docCenterFileVo");

    public final QDocCenterVo docCenter;

    public final StringPath docFileName = createString("docFileName");

    public final NumberPath<Integer> docFileNo = createNumber("docFileNo", Integer.class);

    public final StringPath docFileType = createString("docFileType");

    public final StringPath docFileUrl = createString("docFileUrl");

    public QDocCenterFileVo(String variable) {
        this(DocCenterFileVo.class, forVariable(variable), INITS);
    }

    public QDocCenterFileVo(Path<? extends DocCenterFileVo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDocCenterFileVo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDocCenterFileVo(PathMetadata metadata, PathInits inits) {
        this(DocCenterFileVo.class, metadata, inits);
    }

    public QDocCenterFileVo(Class<? extends DocCenterFileVo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.docCenter = inits.isInitialized("docCenter") ? new QDocCenterVo(forProperty("docCenter"), inits.get("docCenter")) : null;
    }

}

