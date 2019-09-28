package com.bitgroupware.project.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitgroupware.project.beans.MemberDto;
import com.bitgroupware.project.beans.ProjectInfoDto;
import com.bitgroupware.project.beans.ProjectMembersDto;
import com.bitgroupware.project.beans.ProjectRiskDto;
import com.bitgroupware.project.beans.ProjectWbsDto;
import com.bitgroupware.project.service.ProjectService;
import com.bitgroupware.project.util.Analysis;
import com.bitgroupware.security.config.SecurityUser;
import com.bitgroupware.utils.Pager;
import com.bitgroupware.utils.Search;

@Controller
@RequestMapping("/user")
public class ProjectAjaxController {

	@Autowired
	private ProjectService projectService;
	
	/*프로젝트 참여인원 추가 */
	@RequestMapping(value="/insertProjectAttendMembersAjax", method=RequestMethod.POST)
	@ResponseBody
	public String insertProjectAttendMembersAjax(@RequestParam(value="checkBoxArr[]") List<String> checkBoxArr, int prjCode) {
		for(String checkBox : checkBoxArr) {
			projectService.insertProjectAttendMembers(checkBox, prjCode);
		}
		return "project/projectList";
	}
	
	//Project WBS List 불러오는 ajax
	@RequestMapping(value="selectProjectWbsListAjax", method=RequestMethod.POST)
	@ResponseBody
	public List<ProjectWbsDto> selectProjectWbsListAjax(int prjCode){
		
		return projectService.selectProjectWbsList(prjCode);
	}
	
	//Project WBS List 저장하는 ajax
	@RequestMapping(value="insertProjectWbsListAjax", method=RequestMethod.POST)
	@ResponseBody
	public boolean insertProjectWbsListAjax(ProjectInfoDto prjDto, HttpServletRequest req) {
		
		
		boolean isc = false;
		
		String[] prjTotalDaysSum = req.getParameterValues("inPrjTotalDays");
		
		if(prjTotalDaysSum != null) {
			String[] prjTotalDays = new Analysis(prjDto, prjTotalDaysSum).getTermsFormat();
			List<ProjectWbsDto> lists = new ArrayList<ProjectWbsDto>();
			for(int i = 0; i < prjTotalDays.length; i++) {
				ProjectWbsDto prjWbsDto = new ProjectWbsDto(prjDto.getPrjCode(),
						Integer.parseInt(req.getParameterValues("inPrjGroup")[i]),
						Integer.parseInt(req.getParameterValues("inPrjStep")[i]),
						Integer.parseInt(req.getParameterValues("inPrjDepth")[i]),
						Integer.parseInt(req.getParameterValues("workCompletion")[i]),
						req.getParameterValues("inPrjWorkName")[i],
						req.getParameterValues("planStart")[i],
						req.getParameterValues("planEnd")[i],
						req.getParameterValues("realStart")[i],
						req.getParameterValues("realEnd")[i],
						req.getParameterValues("inPrjManager")[i],
						req.getParameterValues("inPrjOutput")[i],
						prjTotalDays[i]);
				lists.add(prjWbsDto);
			}
			isc = projectService.deleteProjectWbsList(prjDto.getPrjCode());
			
			isc = projectService.insertProjectWbsList(lists);
			
		}else {
			
			//isc = projectService.deleteProjectWbsList(prjDto.getPrjCode());
		}
		return isc;
	}
	
	/*프로젝트 참여인원 삭제 */
	@RequestMapping(value="deleteProjectAttendMember", method=RequestMethod.POST)
	@ResponseBody
	public void deleteProjectAttendMember(int prjCode, String memId) {
		ProjectMembersDto prjMemDto;
		prjMemDto = projectService.selectPrjMemNo(prjCode, memId);
		projectService.deleteProjectAttendMember(prjMemDto.getPrjMemNo());
	}
	
	/*프로젝트 삭제 처리*/
	@RequestMapping(value="deleteProjectAjax", method=RequestMethod.POST)
	@ResponseBody
	public void deleteProjectAjax(@RequestParam(value="prjCodeArr[]") List<Integer> prjCodeArr) {
		for(int prjCode : prjCodeArr) {
			projectService.deleteProject(prjCode);
		}
	}
	
	/*달력에 뿌릴 WBS 데이터 불러오기*/
	@RequestMapping("/selectProjectWbsOnCalendarAjax")
	@ResponseBody
	public List<ProjectWbsDto> selectProjectWbsOnCalendarAjax(int prjCode) {
		return projectService.selectProjectWbsOnCalendar(prjCode);
	}
	
	//프로젝트 참여인원 중 부장을 추가했는지 체크
	@RequestMapping(value="/selectMemberRanksByMemId", method=RequestMethod.POST)
	@ResponseBody
	public boolean selectMemberRanksByMemId(@RequestParam(value="checkBoxArr[]") List<String> checkBoxArr, HttpServletRequest req){
		
//		String[] memArr = req.getParameterValues("memIdChk");
		MemberDto memDto;
		boolean chk = false;
		for(String memId : checkBoxArr) {
			
			memDto = projectService.selectMemberRanksByMemId(memId);
			if(memDto.getRanks().equals("부장")) {
				chk = true;
			}
		}
		if(chk) {
			return chk;
		}else {
			return false;
		}
	}
	
	/*위험관리대장 체크 삭제 처리*/
	@RequestMapping(value="deleteProjectRiskAjax", method=RequestMethod.POST)
	@ResponseBody
	public void deleteProjectRiskAjax(@RequestParam(value="rskCodeArr[]") List<Integer> rskCodeArr) {
		
		System.out.println("rskCodeArr values : "+rskCodeArr);
		for(int rskCode : rskCodeArr) {
			projectService.deleteProjectRisk(rskCode);
		}
	}
}
