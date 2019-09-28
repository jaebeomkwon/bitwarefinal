package com.bitgroupware.community.vo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "meetingroom")
public class MeetingroomVo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int mrNo;
	private String mrName;
	private int mrMaxPerson;

}
