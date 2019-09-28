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
@Entity(name = "car_reservation")
public class CarReservationVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int carResNo;
	private String carResStart;
	private String carResEnd;
	private String carDeparture;
	private String carArrival;
	@Column(updatable = false)
	private Date carResDate = new Date();
	
	@ManyToOne
	@JoinColumn(name = "car_no")
	private CarVo car;
 	
	@ManyToOne
	@JoinColumn(name = "mem_id")
	private MemberVo member;
}
