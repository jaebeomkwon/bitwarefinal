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
@Entity(name = "car")
public class CarVo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int carNo;
	private String carName;
	private String carPlate;
//	private String carDeparture;
//	private String carArrival;
}
