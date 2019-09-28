package com.bitgroupware.member.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberVo is a Querydsl query type for MemberVo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMemberVo extends EntityPathBase<MemberVo> {

    private static final long serialVersionUID = 1500786214L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberVo memberVo = new QMemberVo("memberVo");

    public final com.bitgroupware.company.vo.QDepartmentVo department;

    public final BooleanPath enabled = createBoolean("enabled");

    public final StringPath memAddr = createString("memAddr");

    public final StringPath memAddrCode = createString("memAddrCode");

    public final StringPath memAddrDetail = createString("memAddrDetail");

    public final StringPath memAddrExtra = createString("memAddrExtra");

    public final StringPath memId = createString("memId");

    public final StringPath memJoinDate = createString("memJoinDate");

    public final StringPath memJumin = createString("memJumin");

    public final StringPath memName = createString("memName");

    public final StringPath memOfficeTel = createString("memOfficeTel");

    public final StringPath memPw = createString("memPw");

    public final StringPath memQuitDate = createString("memQuitDate");

    public final StringPath memQuitReason = createString("memQuitReason");

    public final StringPath memSignUrl = createString("memSignUrl");

    public final StringPath memStatus = createString("memStatus");

    public final StringPath memTel = createString("memTel");

    public final NumberPath<Integer> memVacation = createNumber("memVacation", Integer.class);

    public final com.bitgroupware.company.vo.QRanksVo ranks;

    public final EnumPath<com.bitgroupware.member.utils.Role> role = createEnum("role", com.bitgroupware.member.utils.Role.class);

    public final com.bitgroupware.company.vo.QTeamVo team;

    public QMemberVo(String variable) {
        this(MemberVo.class, forVariable(variable), INITS);
    }

    public QMemberVo(Path<? extends MemberVo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberVo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberVo(PathMetadata metadata, PathInits inits) {
        this(MemberVo.class, metadata, inits);
    }

    public QMemberVo(Class<? extends MemberVo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.department = inits.isInitialized("department") ? new com.bitgroupware.company.vo.QDepartmentVo(forProperty("department")) : null;
        this.ranks = inits.isInitialized("ranks") ? new com.bitgroupware.company.vo.QRanksVo(forProperty("ranks")) : null;
        this.team = inits.isInitialized("team") ? new com.bitgroupware.company.vo.QTeamVo(forProperty("team"), inits.get("team")) : null;
    }

}

