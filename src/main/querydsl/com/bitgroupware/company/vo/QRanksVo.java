package com.bitgroupware.company.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRanksVo is a Querydsl query type for RanksVo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRanksVo extends EntityPathBase<RanksVo> {

    private static final long serialVersionUID = -1714232466L;

    public static final QRanksVo ranksVo = new QRanksVo("ranksVo");

    public final StringPath ranks = createString("ranks");

    public final NumberPath<Integer> ranksNo = createNumber("ranksNo", Integer.class);

    public QRanksVo(String variable) {
        super(RanksVo.class, forVariable(variable));
    }

    public QRanksVo(Path<? extends RanksVo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRanksVo(PathMetadata metadata) {
        super(RanksVo.class, metadata);
    }

}

