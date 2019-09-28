package com.bitgroupware.etc.beans;

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
@Entity(name = "client")
public class ClientVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int clientNo;
	private String clientName;
	private String clientTel;
	private String clientCompany;
	private String clientDeptName;
	private String clientRanks;
	private String clientNote;
	
}
