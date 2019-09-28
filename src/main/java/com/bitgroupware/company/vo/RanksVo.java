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
@Entity(name = "ranks")
public class RanksVo {

	@Id
	@Column(columnDefinition = "varchar(100)")
	private String ranks;
	private int ranksNo;
	
}
