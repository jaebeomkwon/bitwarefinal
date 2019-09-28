package com.bitgroupware.community.vo;

import java.util.Date;

import javax.persistence.Column;
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
@Entity(name = "meetingroom_reservation")
public class MeetingroomReservationVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int mrResNo;
	private String mrResStart;
	private String mrResEnd;
	@Column(updatable = false)
	private Date mrResDate = new Date();
	
	@ManyToOne
	@JoinColumn(name = "mr_no")
	private MeetingroomVo meetingroom;
 	
	@ManyToOne
	@JoinColumn(name = "mem_id")
	private MemberVo member;
}
