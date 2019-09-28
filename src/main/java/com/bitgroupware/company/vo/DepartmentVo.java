package com.bitgroupware.company.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "department")
public class DepartmentVo {

	@Id
	@Column(columnDefinition = "varchar(100)")
	private String deptName;
	private int deptNo;
}
