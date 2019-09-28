package com.bitgroupware.etc.service;

import java.util.List;

import com.bitgroupware.company.vo.TeamVo;
import com.bitgroupware.etc.utils.TemporaryRepetition;
import com.bitgroupware.member.vo.MemberVo;
import com.bitgroupware.project.beans.ProjectInfoDto;

public interface OrganizationChartService {

	List<TeamVo> selectTeamListByDeptName(String deptName);

	List<MemberVo> selectMemberListByTeam(TeamVo team);

	MemberVo selectHeaderBydeptName(String deptName);

	List<TemporaryRepetition> selectExecutiveStaffList();

	List<ProjectInfoDto> selectProjectListByMemIdWithZero(String memId);

	List<ProjectInfoDto> selectProjectListByMemIdWithOne(String memId);

}
