package com.bitgroupware.admin.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitgroupware.company.vo.DepartmentVo;
import com.bitgroupware.company.vo.RanksVo;
import com.bitgroupware.company.vo.TeamVo;
import com.bitgroupware.member.service.MemberService;
import com.bitgroupware.member.utils.Role;
import com.bitgroupware.member.vo.MemberVo;
import com.bitgroupware.utils.Pager;
import com.bitgroupware.utils.Search;

@Controller
@RequestMapping("/admin")
public class AdminMemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private PasswordEncoder encoder;

	// 사원 리스트
	@RequestMapping("/selectMemberList")
	public String selectMemberList(Model model, @RequestParam(defaultValue = "1") int curPage, Search search) {
		int count = memberService.countNotice(search);

		Pager page = new Pager(count, curPage);
		int blockBegin = page.getBlockBegin();
		int blockEnd = page.getBlockEnd();

		List<Integer> block = new ArrayList<Integer>();
		for (int i = blockBegin; i <= blockEnd; i++) {
			block.add(i);
		}

		int begin = page.getPageBegin() - 1;

		List<MemberVo> memberList = memberService.selectMemberList(begin, search);

		model.addAttribute("memberList", memberList);
		model.addAttribute("page",page);
		model.addAttribute("block",block);
		model.addAttribute("search",search);
		
		return "admin/member/memberList";
	}

	// 사원 등록 페이지
	@RequestMapping("/insertMemberView")
	public String insertMemberView(Model model, DepartmentVo departmentVo, RanksVo ranksVo) {
		List<DepartmentVo> deptList = memberService.selectDeptList(departmentVo);
		List<RanksVo> rankList = memberService.selectRanksList(ranksVo);

		model.addAttribute("deptList", deptList);
		model.addAttribute("rankList", rankList);
		return "admin/member/memberInsert";
	}

	// 비동기로 Team명 가져오기
	@RequestMapping("/selectTeamByDept")
	@ResponseBody
	public List<String> selectTeamByDept(String deptName) {
		List<TeamVo> teamList = memberService.selectTeamList(deptName);
		List<String> teamName = new ArrayList<String>();
		for (int i = 0; i < teamList.size(); i++) {
			teamName.add(teamList.get(i).getTeamName());
		}
		return teamName;
	}

	// 사원 등록
	@RequestMapping("/insertMember")
	public String insertMember(MemberVo memberVo, HttpServletRequest request) {
		String fileName = "Empty";
		String serverPath = "";
		String localPath = "";
		if (!memberVo.getFile().isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String uid = uuid.toString();
			fileName = uid + "_" + memberVo.getFile().getOriginalFilename();
			try {
				String defaultPath = request.getSession().getServletContext().getRealPath("/");
				serverPath = defaultPath + "memSign" + File.separator + fileName;
				localPath = File.separator + "memSign" + File.separator + fileName;
				File f = new File(serverPath);
				if (!f.exists()) {
					f.mkdirs();
				}
				memberVo.getFile().transferTo(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
			memberVo.setMemSignUrl(localPath);
		}

		String memCount = memberService.selectCountMember();
		String memId = request.getParameter("memJoinDate").replace("-", "") + (String.format("%3s", memCount)).replace(" ", "0"); // 사원번호 부여

		memberVo.setMemId(memId);
		memberVo.setMemPw(encoder.encode(memId)); // 초기 비밀번호 = 사번
		switch (memberVo.getRanks().getRanksNo()) {
		case 1:
			memberVo.setRole(Role.ROLE_USER);
			break;
		case 2:
			memberVo.setRole(Role.ROLE_PL);
			break;
		case 3:
		case 4:
		case 5:
			memberVo.setRole(Role.ROLE_PM);
			break;
		default:
			memberVo.setRole(Role.ROLE_USER);
		}

		memberService.insertMember(memberVo);
		return "redirect:selectMemberList";
	}

	// 사원 수정 페이지
	@RequestMapping("/updateMemberView")
	public String updateMemberView(Model model, String memId, DepartmentVo departmentVo, RanksVo ranksVo) {
		MemberVo member = memberService.selectMember(memId);
		List<DepartmentVo> deptList = memberService.selectDeptList(departmentVo);
		try {
			// 기존 생성된 사원이지만 부서가 없는 경우 예외 발생
			List<TeamVo> teamList = memberService.selectTeamList(member.getDepartment().getDeptName());
			model.addAttribute("teamList", teamList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<RanksVo> rankList = memberService.selectRanksList(ranksVo);

		model.addAttribute("deptList", deptList);
		model.addAttribute("rankList", rankList);
		model.addAttribute("member", member);

		return "admin/member/memberUpdate";
	}

	// 사원 수정
	@RequestMapping("/updateMember")
	public String updateMember(MemberVo memberVo, HttpServletRequest request) {
		switch (memberVo.getRanks().getRanksNo()) {
		case 1:
			memberVo.setRole(Role.ROLE_USER);
			break;
		case 2:
			memberVo.setRole(Role.ROLE_PL);
			break;
		case 3:
		case 4:
		case 5:
			memberVo.setRole(Role.ROLE_PM);
			break;
		default:
			memberVo.setRole(Role.ROLE_USER);
		}

		// enabled 설정. 작동X
//		switch (memberVo.getMemStatus()) {
//		case "out":
//			memberVo.setEnabled(false);
//			break;
//		default:
//			memberVo.setEnabled(true);
//			memberVo.setMemQuitDate(null);
//			memberVo.setMemQuitReason("");
//			break;
//		}

		String fileName = "Empty";
		String serverPath = "";
		String localPath = "";
		if (!memberVo.getFile().isEmpty()) {
			UUID uuid = UUID.randomUUID();
			String uid = uuid.toString();
			fileName = uid + "_" + memberVo.getFile().getOriginalFilename();
			try {
				String defaultPath = request.getSession().getServletContext().getRealPath("/");
				serverPath = defaultPath + "memSign" + File.separator + fileName;
				localPath = File.separator + "memSign" + File.separator + fileName;
				File f = new File(serverPath);
				if (!f.exists()) {
					f.mkdirs();
				}
				memberVo.getFile().transferTo(f);
			} catch (Exception e) {
				e.printStackTrace();
			}
			memberVo.setMemSignUrl(localPath);
		}

		memberService.updateMember(memberVo);
		return "redirect:selectMemberList";
	}

	// 사원 삭제
	@RequestMapping("/deleteMember")
	public String deleteMember(MemberVo memberVo) {
		memberService.deleteMember(memberVo.getMemId());
		return "redirect:selectMemberList";
	}
	
	@RequestMapping(value = "/deleteMemberCheckBox")
	@ResponseBody
	public String deleteMember(@RequestParam(value = "checkBoxArr[]") List<String> checkBoxArr) {
		for(String checkBox: checkBoxArr) {
			memberService.deleteMember(checkBox);
		}
		return "삭제완료";
	}
	
	
}
