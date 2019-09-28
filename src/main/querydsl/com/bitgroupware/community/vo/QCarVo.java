package com.bitgroupware.community.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QCarVo is a Querydsl query type for CarVo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QCarVo extends EntityPathBase<CarVo> {

    private static final long serialVersionUID = -2139177137L;

    public static final QCarVo carVo = new QCarVo("carVo");

    public final StringPath carName = createString("carName");

    public final NumberPath<Integer> carNo = createNumber("carNo", Integer.class);

    public final StringPath carPlate = createString("carPlate");

    public QCarVo(String variable) {
        super(CarVo.class, forVariable(variable));
    }

    public QCarVo(Path<? extends CarVo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCarVo(PathMetadata metadata) {
        super(CarVo.class, metadata);
    }

}

