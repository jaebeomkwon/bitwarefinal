package com.bitgroupware.company.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QDepartmentVo is a Querydsl query type for DepartmentVo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QDepartmentVo extends EntityPathBase<DepartmentVo> {

    private static final long serialVersionUID = -1536330275L;

    public static final QDepartmentVo departmentVo = new QDepartmentVo("departmentVo");

    public final StringPath deptName = createString("deptName");

    public final NumberPath<Integer> deptNo = createNumber("deptNo", Integer.class);

    public QDepartmentVo(String variable) {
        super(DepartmentVo.class, forVariable(variable));
    }

    public QDepartmentVo(Path<? extends DepartmentVo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDepartmentVo(PathMetadata metadata) {
        super(DepartmentVo.class, metadata);
    }

}

