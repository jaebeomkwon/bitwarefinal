package com.bitgroupware.etc.utils;

import java.util.List;

import com.bitgroupware.company.vo.RanksVo;
import com.bitgroupware.company.vo.TeamVo;
import com.bitgroupware.member.vo.MemberVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TemporaryRepetition {

	public TeamVo team;
	public MemberVo leader;
	public List<MemberVo> teamMemberList;
	
	public RanksVo ranks;
	public List<MemberVo> executiveStaffList;
}
