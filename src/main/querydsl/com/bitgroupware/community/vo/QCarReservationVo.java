package com.bitgroupware.community.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCarReservationVo is a Querydsl query type for CarReservationVo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCarReservationVo extends EntityPathBase<CarReservationVo> {

    private static final long serialVersionUID = -887242417L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCarReservationVo carReservationVo = new QCarReservationVo("carReservationVo");

    public final QCarVo car;

    public final StringPath carArrival = createString("carArrival");

    public final StringPath carDeparture = createString("carDeparture");

    public final DateTimePath<java.util.Date> carResDate = createDateTime("carResDate", java.util.Date.class);

    public final StringPath carResEnd = createString("carResEnd");

    public final NumberPath<Integer> carResNo = createNumber("carResNo", Integer.class);

    public final StringPath carResStart = createString("carResStart");

    public final com.bitgroupware.member.vo.QMemberVo member;

    public QCarReservationVo(String variable) {
        this(CarReservationVo.class, forVariable(variable), INITS);
    }

    public QCarReservationVo(Path<? extends CarReservationVo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCarReservationVo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCarReservationVo(PathMetadata metadata, PathInits inits) {
        this(CarReservationVo.class, metadata, inits);
    }

    public QCarReservationVo(Class<? extends CarReservationVo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.car = inits.isInitialized("car") ? new QCarVo(forProperty("car")) : null;
        this.member = inits.isInitialized("member") ? new com.bitgroupware.member.vo.QMemberVo(forProperty("member"), inits.get("member")) : null;
    }

}

