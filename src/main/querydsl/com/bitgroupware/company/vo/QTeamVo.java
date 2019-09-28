package com.bitgroupware.company.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTeamVo is a Querydsl query type for TeamVo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTeamVo extends EntityPathBase<TeamVo> {

    private static final long serialVersionUID = -2072941656L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTeamVo teamVo = new QTeamVo("teamVo");

    public final QDepartmentVo department;

    public final StringPath teamName = createString("teamName");

    public final NumberPath<Integer> teamNo = createNumber("teamNo", Integer.class);

    public QTeamVo(String variable) {
        this(TeamVo.class, forVariable(variable), INITS);
    }

    public QTeamVo(Path<? extends TeamVo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTeamVo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTeamVo(PathMetadata metadata, PathInits inits) {
        this(TeamVo.class, metadata, inits);
    }

    public QTeamVo(Class<? extends TeamVo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.department = inits.isInitialized("department") ? new QDepartmentVo(forProperty("department")) : null;
    }

}

