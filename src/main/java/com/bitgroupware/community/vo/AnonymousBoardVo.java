package com.bitgroupware.community.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "anonymous_board")
public class AnonymousBoardVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int bno;
	private String bpw;
	private String btitle;
	private String bcontent;
	@Column(updatable = false)
	private Date bdate = new Date();
	private int bgroup;
	private int bstep;
	private int bindent;
	@Column(insertable = false, columnDefinition = "int(10) default 0")
	private int bcnt;

	@Transient
	private List<Integer> bindentcnt = new ArrayList<Integer>();
}