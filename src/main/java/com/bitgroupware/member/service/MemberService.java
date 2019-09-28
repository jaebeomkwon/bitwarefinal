package com.bitgroupware.member.service;

import java.util.List;

import com.bitgroupware.company.vo.DepartmentVo;
import com.bitgroupware.company.vo.RanksVo;
import com.bitgroupware.company.vo.TeamVo;
import com.bitgroupware.member.vo.MemberVo;
import com.bitgroupware.utils.Search;

public interface MemberService {
	
	List<MemberVo> selectMemberList(int begin, Search search);
	
	List<DepartmentVo> selectDeptList(DepartmentVo departmentVo);

	List<TeamVo> selectTeamList(String deptName);
	
	List<RanksVo> selectRanksList(RanksVo ranksVo);
	
	void insertMember(MemberVo memverVo);
	
	String selectCountMember();

	MemberVo selectMember(String memId);
	
	void updateMember(MemberVo memberVo);
	
	void deleteMember(String memId);

	int countNotice(Search search);
	
	void updateMyInfo(MemberVo memberVo);

}
