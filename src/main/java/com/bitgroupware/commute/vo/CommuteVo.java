package com.bitgroupware.commute.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.bitgroupware.member.vo.MemberVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "commute")
public class CommuteVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int commuteNo;
	@ManyToOne
	@JoinColumn(name = "mem_id")
	private MemberVo memberVo;
	private String commuteDate;
	private String commuteOntime;
	private String commuteOfftime;
	private String commuteStatus;
}
