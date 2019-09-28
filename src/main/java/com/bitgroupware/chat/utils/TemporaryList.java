package com.bitgroupware.chat.utils;

import com.bitgroupware.chat.Beans.DepartmentDto;
import com.bitgroupware.chat.Beans.MemberDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TemporaryList {

	public DepartmentDto department;
	public MemberDto member;
	public String content;
	public int count;
	public boolean check;
	
}
