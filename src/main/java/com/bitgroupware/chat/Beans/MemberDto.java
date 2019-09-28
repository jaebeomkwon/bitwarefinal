package com.bitgroupware.chat.Beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDto {

	private String memId;
	private String memName;
	private int deptNo;
	private String deptName;
	private String ranks;
	private int teamNo;
	private String teamName;
	private String content;
}
