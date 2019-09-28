package com.bitgroupware.company.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "team")
public class TeamVo {
	
	@Id
	@Column(columnDefinition = "varchar(100)")
	private String teamName;
	private int teamNo;
	
	@ManyToOne
	@JoinColumn(name = "dept_name")
	private DepartmentVo department;

}
