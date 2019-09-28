package com.bitgroupware.etc.beans;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QClientVo is a Querydsl query type for ClientVo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QClientVo extends EntityPathBase<ClientVo> {

    private static final long serialVersionUID = 731066171L;

    public static final QClientVo clientVo = new QClientVo("clientVo");

    public final StringPath clientCompany = createString("clientCompany");

    public final StringPath clientDeptName = createString("clientDeptName");

    public final StringPath clientName = createString("clientName");

    public final NumberPath<Integer> clientNo = createNumber("clientNo", Integer.class);

    public final StringPath clientNote = createString("clientNote");

    public final StringPath clientRanks = createString("clientRanks");

    public final StringPath clientTel = createString("clientTel");

    public QClientVo(String variable) {
        super(ClientVo.class, forVariable(variable));
    }

    public QClientVo(Path<? extends ClientVo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QClientVo(PathMetadata metadata) {
        super(ClientVo.class, metadata);
    }

}

