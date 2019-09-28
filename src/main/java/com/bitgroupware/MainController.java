package com.bitgroupware;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitgroupware.approval.beans.ApprovalDto;
import com.bitgroupware.approval.persistence.ApprovalDao;
import com.bitgroupware.community.service.NoticeService;
import com.bitgroupware.community.vo.NoticeVo;
import com.bitgroupware.commute.persistence.CommuteRepository;
import com.bitgroupware.commute.service.CommuteService;
import com.bitgroupware.commute.vo.CommuteVo;
import com.bitgroupware.member.utils.Role;
import com.bitgroupware.project.beans.ProjectInfoDto;
import com.bitgroupware.project.beans.ProjectWbsDto;
import com.bitgroupware.project.persistence.ProjectDao;
import com.bitgroupware.security.config.SecurityUser;
import com.bitgroupware.utils.MainViewProjectList;

@Controller
@RequestMapping("/user")
public class MainController {

	@Autowired
	NoticeService noticeService;
	
	@Autowired
	ApprovalDao approvalDao;
	
	@Autowired
	ProjectDao projectDao;
	
	@Autowired
	CommuteService commuteService;
	
	@Autowired
	CommuteRepository commuteRepository;
	

	@RequestMapping("/")
	public String index(Model model, @AuthenticationPrincipal SecurityUser principal) {
		
		if (principal.getMember().getRole()==(Role.ROLE_ADMIN)) {
			return "admin";
		} else {
			
			// 공지사항
			List<NoticeVo> noticeList = noticeService.selectMainNoticeList();
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String today = format.format(date);
			
			// 결재 할 문서
			List<ApprovalDto> approvalList = approvalDao.selectMainApprovalListTo(principal.getMember().getMemId());
			
			// 참여중인 프로젝트
			List<ProjectInfoDto> projectInfoList = projectDao.selectMainAttendProjectList(principal.getMember().getMemId());
			List<MainViewProjectList> projectList = new ArrayList<MainViewProjectList>();
			for (ProjectInfoDto projectInfo : projectInfoList) {
				MainViewProjectList mainViewProject = new MainViewProjectList();
				mainViewProject.setProjectInfo(projectInfo);
				projectList.add(mainViewProject);
			}
			
			// 근태
			String startDate = "1990-01-01";
			String endDate = "2099-12-31";
			List<CommuteVo> commuteTotalCount = commuteService.selectCommuteList(principal.getMember(), startDate, endDate);
			List<String> commuteStatusCount = commuteService.selectStatusCount(principal.getMember());
			int totalVacation = principal.getMember().getMemVacation();
			long usedVacation = commuteRepository.countByMemberVoAndCommuteStatus(principal.getMember(), "휴가");
			

			model.addAttribute("noticeList", noticeList);
			model.addAttribute("today", today);
			
			model.addAttribute("approvalList", approvalList);
			
			model.addAttribute("projectList", projectList);
			model.addAttribute("projectListSize", projectList.size()-1);
			
			model.addAttribute("commuteTotalCount", commuteTotalCount);
			model.addAttribute("commuteStatusCount", commuteStatusCount);
			model.addAttribute("totalVacation", totalVacation);
			model.addAttribute("usedVacation", usedVacation);
			
			return "index";
		}
	}
	
	@RequestMapping("/calcWorks")
	@ResponseBody
	public MainViewProjectList arrayTest(@AuthenticationPrincipal SecurityUser principal, int prjListNum) {
		List<ProjectInfoDto> projectInfoList = projectDao.selectMainAttendProjectList(principal.getMember().getMemId());
		
		int curdate = Integer.parseInt(commuteRepository.selectCurdate().replace("-", ""));
		
		int planComplete = 0;
		int planIng = 0;
		int planUnset = 0;
		int realComplete = 0;
		int realIng = 0;
		int realUnset = 0;
		
		for(ProjectWbsDto projectWbs : projectDao.selectProjectWbsList(projectInfoList.get(prjListNum).getPrjCode())) {
			/*
			 계획 작업 종료일 < 오늘 -> 작업 완료
			 계획 작업 시작일 <= 오늘 -> 작업 진행
			 계획 작업 시작일 > 오늘 -> 작업 미시작
			 */
			if (Integer.parseInt(projectWbs.getPrjPlanEnd().replace("-", "")) < curdate) planComplete++;
			else if (Integer.parseInt(projectWbs.getPrjPlanStart().replace("-", "")) <= curdate) planIng++;
			else planUnset++;
			/*
			실제 작업 시작일 입력 / 실제 작업 종료일(오늘보다 전) 입력 -> 작업 완료
			실제 작업 시작일 입력 / 실제 작업 종료일(오늘보다 후or오늘) 입력 -> 작업 진행
			
			실제 작업 시작일(오늘보다 전or오늘) 입력 / 실제 작업 종료일 미입력 - > 작업 진행
			실제 작업 시작일(오늘보다 후) 입력 / 실제 작업 종료일 미입력 -> 작업 미시작
			실제 작업 시작일 미입력 / 실제 작업 종료일 미입력 -> 작업 미시작
			 */
			try {
				if (Integer.parseInt(projectWbs.getPrjRealEnd().replace("-", "")) < curdate) realComplete++;
				else realIng++;
			} catch (NullPointerException realEndIsNull) {
				System.out.println("RealEnd가 입력되지 않음");
				try {
					if (Integer.parseInt(projectWbs.getPrjRealStart().replace("-", "")) <= curdate) realIng++;
					else realUnset++;
				} catch (NullPointerException realStartIsNull) {
					System.out.println("RealStart가 입력되지않음");
					realUnset++;
				}
			}
		}
		
		MainViewProjectList mainViewProjectList = new MainViewProjectList();
		
		mainViewProjectList.setPlanComplete(planComplete);
		mainViewProjectList.setPlanIng(planIng);
		mainViewProjectList.setPlanUnset(planUnset);
		mainViewProjectList.setRealComplete(realComplete);
		mainViewProjectList.setRealIng(realIng);
		mainViewProjectList.setRealUnset(realUnset);
		
		return mainViewProjectList;
	}

}
