package com.bitgroupware.community.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMeetingroomReservationVo is a Querydsl query type for MeetingroomReservationVo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMeetingroomReservationVo extends EntityPathBase<MeetingroomReservationVo> {

    private static final long serialVersionUID = 945944781L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMeetingroomReservationVo meetingroomReservationVo = new QMeetingroomReservationVo("meetingroomReservationVo");

    public final QMeetingroomVo meetingroom;

    public final com.bitgroupware.member.vo.QMemberVo member;

    public final DateTimePath<java.util.Date> mrResDate = createDateTime("mrResDate", java.util.Date.class);

    public final StringPath mrResEnd = createString("mrResEnd");

    public final NumberPath<Integer> mrResNo = createNumber("mrResNo", Integer.class);

    public final StringPath mrResStart = createString("mrResStart");

    public QMeetingroomReservationVo(String variable) {
        this(MeetingroomReservationVo.class, forVariable(variable), INITS);
    }

    public QMeetingroomReservationVo(Path<? extends MeetingroomReservationVo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMeetingroomReservationVo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMeetingroomReservationVo(PathMetadata metadata, PathInits inits) {
        this(MeetingroomReservationVo.class, metadata, inits);
    }

    public QMeetingroomReservationVo(Class<? extends MeetingroomReservationVo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.meetingroom = inits.isInitialized("meetingroom") ? new QMeetingroomVo(forProperty("meetingroom")) : null;
        this.member = inits.isInitialized("member") ? new com.bitgroupware.member.vo.QMemberVo(forProperty("member"), inits.get("member")) : null;
    }

}

