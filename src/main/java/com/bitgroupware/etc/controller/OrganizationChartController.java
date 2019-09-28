package com.bitgroupware.etc.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitgroupware.company.vo.TeamVo;
import com.bitgroupware.etc.service.OrganizationChartService;
import com.bitgroupware.etc.utils.TemporaryRepetition;
import com.bitgroupware.member.vo.MemberVo;
import com.bitgroupware.project.beans.ProjectInfoDto;

@Controller
@RequestMapping("/user")
public class OrganizationChartController {

	@Autowired
	private OrganizationChartService organizationChartService;

	@RequestMapping("/selectOrganizationChartList")
	public String selectOrganizationChartList(Model model, String deptName) {
		if (deptName.equals("임원진")) {
			List<TemporaryRepetition> repetitions = organizationChartService.selectExecutiveStaffList();
			model.addAttribute("repetitions", repetitions);
			model.addAttribute("deptName", deptName);
			return "etc/organizationChartExecutiveStaffList";
		} else {
			List<TemporaryRepetition> repetitions = new ArrayList<TemporaryRepetition>();
			MemberVo header = organizationChartService.selectHeaderBydeptName(deptName);
			List<TeamVo> teamList = organizationChartService.selectTeamListByDeptName(deptName);
			for (int i = 0; i < teamList.size(); i++) {
				List<MemberVo> teamMemberList = organizationChartService.selectMemberListByTeam(teamList.get(i));
				for (int j = 0; j < teamMemberList.size(); j++) {
					if (teamMemberList.get(j).getRanks().getRanks().equals("팀장")) {
						TemporaryRepetition repetition = new TemporaryRepetition();
						repetition.setTeam(teamList.get(i));
						repetition.setLeader(teamMemberList.get(j));
						teamMemberList.remove(j);
						repetition.setTeamMemberList(teamMemberList);
						repetitions.add(repetition);
					}
				}
			}
			model.addAttribute("repetitions", repetitions);
			model.addAttribute("deptName", deptName);
			model.addAttribute("header", header);
			return "etc/organizationChartList";
		}
	}
	
	@RequestMapping("/selectProjectListForOrganizationChartByMemIdWithZero")
	@ResponseBody
	public List<ProjectInfoDto> selectProjectListForOrganizationChartByMemIdWithZero(String memId) {
		return organizationChartService.selectProjectListByMemIdWithZero(memId);
		
	}

	@RequestMapping("/selectProjectListForOrganizationChartByMemIdWithOne")
	@ResponseBody
	public List<ProjectInfoDto> selectProjectListForOrganizationChartByMemIdWithOne(String memId) {
		return organizationChartService.selectProjectListByMemIdWithOne(memId);
		
	}
}
