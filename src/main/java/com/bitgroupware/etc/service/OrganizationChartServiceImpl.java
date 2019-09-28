package com.bitgroupware.etc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitgroupware.company.persistence.RanksRepository;
import com.bitgroupware.company.persistence.TeamRepository;
import com.bitgroupware.company.vo.DepartmentVo;
import com.bitgroupware.company.vo.RanksVo;
import com.bitgroupware.company.vo.TeamVo;
import com.bitgroupware.etc.persistence.OrganizationChartDao;
import com.bitgroupware.etc.utils.TemporaryRepetition;
import com.bitgroupware.member.persistence.MemberRepository;
import com.bitgroupware.member.vo.MemberVo;
import com.bitgroupware.project.beans.ProjectInfoDto;

@Service
public class OrganizationChartServiceImpl implements OrganizationChartService{

	@Autowired
	private MemberRepository memberRepo;
	@Autowired
	private TeamRepository teamRepo;
	@Autowired
	private RanksRepository ranksRepo;
	@Autowired
	private OrganizationChartDao organizationChartDao;

	public List<TeamVo> selectTeamListByDeptName(String deptName) {
		DepartmentVo department = new DepartmentVo();
		department.setDeptName(deptName);
		return teamRepo.findByDepartment(department);
	}

	public List<MemberVo> selectMemberListByTeam(TeamVo team) {
		return memberRepo.findByTeam(team);
	}

	public MemberVo selectHeaderBydeptName(String deptName) {
		return memberRepo.selectHeaderBydeptName(deptName);
	}

	public List<TemporaryRepetition> selectExecutiveStaffList() {
		List<RanksVo> ranksList = ranksRepo.findByRanksNoGreaterThanOrderByRanksNoDesc(3);
		List<TemporaryRepetition> repetitions = new ArrayList<TemporaryRepetition>();
		for(RanksVo ranks : ranksList) {
			TemporaryRepetition repetition = new TemporaryRepetition();
			repetition.setRanks(ranks);
			repetition.setExecutiveStaffList(memberRepo.findByRanks(ranks));
			repetitions.add(repetition);
		}
		return repetitions;
	}

	public List<ProjectInfoDto> selectProjectListByMemIdWithZero(String memId) {
		return organizationChartDao.selectProjectListByMemIdWithZero(memId);
	}

	public List<ProjectInfoDto> selectProjectListByMemIdWithOne(String memId) {
		return organizationChartDao.selectProjectListByMemIdWithOne(memId);
	}
}
