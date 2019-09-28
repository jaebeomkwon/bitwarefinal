package com.bitgroupware.community.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QMeetingroomVo is a Querydsl query type for MeetingroomVo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMeetingroomVo extends EntityPathBase<MeetingroomVo> {

    private static final long serialVersionUID = -1376630127L;

    public static final QMeetingroomVo meetingroomVo = new QMeetingroomVo("meetingroomVo");

    public final NumberPath<Integer> mrMaxPerson = createNumber("mrMaxPerson", Integer.class);

    public final StringPath mrName = createString("mrName");

    public final NumberPath<Integer> mrNo = createNumber("mrNo", Integer.class);

    public QMeetingroomVo(String variable) {
        super(MeetingroomVo.class, forVariable(variable));
    }

    public QMeetingroomVo(Path<? extends MeetingroomVo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMeetingroomVo(PathMetadata metadata) {
        super(MeetingroomVo.class, metadata);
    }

}

