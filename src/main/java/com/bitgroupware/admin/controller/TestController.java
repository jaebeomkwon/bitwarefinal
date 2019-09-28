package com.bitgroupware.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitgroupware.community.persistence.CarRepository;
import com.bitgroupware.community.persistence.DocCenterRepository;
import com.bitgroupware.community.persistence.MeetingroomRepository;
import com.bitgroupware.community.persistence.NoticeRepository;
import com.bitgroupware.community.vo.CarVo;
import com.bitgroupware.community.vo.DocCenterVo;
import com.bitgroupware.community.vo.MeetingroomVo;
import com.bitgroupware.community.vo.NoticeVo;
import com.bitgroupware.company.persistence.DepartmentRepository;
import com.bitgroupware.company.persistence.RanksRepository;
import com.bitgroupware.company.persistence.TeamRepository;
import com.bitgroupware.company.vo.DepartmentVo;
import com.bitgroupware.company.vo.RanksVo;
import com.bitgroupware.company.vo.TeamVo;
import com.bitgroupware.member.persistence.MemberRepository;
import com.bitgroupware.member.utils.Role;
import com.bitgroupware.member.vo.MemberVo;

@Controller
public class TestController {
	
//	@Autowired
//	private MemberRepository memberRepo;
//	@Autowired
//	private NoticeRepository noticeRepo;
//	@Autowired
//	private DocCenterRepository docCenterRepo;
//	@Autowired
//	private MeetingroomRepository meetingroomRepo;
//	@Autowired
//	private DepartmentRepository departmentRepo;
//	@Autowired
//	private TeamRepository teamRepo;
//	@Autowired
//	private RanksRepository ranksRepo;
//	@Autowired
//	private CarRepository carRepo;
//	@Autowired
//	private PasswordEncoder encoder;

//	@RequestMapping("/createNotice")
//	@ResponseBody
//	public String createNotice() {
//		MemberVo admin = memberRepo.findById("admin").get();
//		for(int i=1; i<=342; i++) {
//			NoticeVo notice = new NoticeVo();
//			notice.setNtCate("일반");
//			notice.setNtTitle("글제목"+i);
//			notice.setNtContent("글내용"+i);
//			notice.setNtFileCheck("N");
//			notice.setMember(admin);
//			
//			noticeRepo.save(notice);
//		}
//		return "공지사항 생성완료";
//	}
//	
//	@RequestMapping("/createDocCenter")
//	@ResponseBody
//	public String createDocCenter() {
//		MemberVo admin = memberRepo.findById("admin").get();
//		for(int i=1; i<=342; i++) {
//			DocCenterVo docCenter = new DocCenterVo();
//			docCenter.setDocCate("일반");
//			docCenter.setDocTitle("글제목"+i);
//			docCenter.setDocContent("글내용"+i);
//			docCenter.setDocFileCheck("N");
//			docCenter.setMember(admin);
//			
//			docCenterRepo.save(docCenter);
//		}
//		return "자료실 생성완료";
//	}
//	
//	@RequestMapping("/createMeetingroom")
//	@ResponseBody
//	public String createMeetionroom() {
//		for(int i=1; i<=4; i++) {
//			MeetingroomVo mr = new MeetingroomVo();
//			mr.setMrName("회의실"+i);
//			mr.setMrMaxPerson(i*4);
//			meetingroomRepo.save(mr);
//		}
//		return "회의실 생성완료";
//	}
//	
//	@RequestMapping("/createCar")
//	@ResponseBody
//	public String createCar() {
//		for(int i=1; i<=4; i++) {
//			CarVo car = new CarVo();
//			car.setCarName("차량"+i);
//			car.setCarPlate("01하000"+i);
//			carRepo.save(car);
//		}
//		return "차량 생성완료";
//	}
	
//	@RequestMapping("/createCompany")
//	@ResponseBody
//	public String createCompany() {
//		DepartmentVo department1 = new DepartmentVo();
//		department1.setDeptName("지원부");
//		department1.setDeptNo(1);
//		departmentRepo.save(department1);
//		DepartmentVo department2 = new DepartmentVo();
//		department2.setDeptName("영업부");
//		department2.setDeptNo(2);
//		departmentRepo.save(department2);
//		DepartmentVo department3 = new DepartmentVo();
//		department3.setDeptName("개발부");
//		department3.setDeptNo(3);
//		departmentRepo.save(department3);
//		
//		DepartmentVo department4 = departmentRepo.findById("지원부").get();
//		TeamVo team1 = new TeamVo();
//		team1.setDepartment(department4);
//		team1.setTeamName("경영지원팀");
//		team1.setTeamNo(1);
//		teamRepo.save(team1);
//		TeamVo team2 = new TeamVo();
//		team2.setDepartment(department4);
//		team2.setTeamName("기술지원팀");
//		team2.setTeamNo(2);
//		teamRepo.save(team2);
//		DepartmentVo department5 = departmentRepo.findById("영업부").get();
//		TeamVo team3 = new TeamVo();
//		team3.setDepartment(department5);
//		team3.setTeamName("영업1팀");
//		team3.setTeamNo(3);
//		teamRepo.save(team3);
//		TeamVo team4 = new TeamVo();
//		team4.setDepartment(department5);
//		team4.setTeamName("영업2팀");
//		team4.setTeamNo(4);
//		teamRepo.save(team4);
//		DepartmentVo department6 = departmentRepo.findById("개발부").get();
//		TeamVo team5 = new TeamVo();
//		team5.setDepartment(department6);
//		team5.setTeamName("개발1팀");
//		team5.setTeamNo(5);
//		teamRepo.save(team5);
//		TeamVo team6 = new TeamVo();
//		team6.setDepartment(department6);
//		team6.setTeamName("개발2팀");
//		team6.setTeamNo(6);
//		teamRepo.save(team6);
//		
//		RanksVo ranks5 = new RanksVo();
//		ranks5.setRanks("사장");
//		ranks5.setRanksNo(5);
//		ranksRepo.save(ranks5);
//		RanksVo ranks4 = new RanksVo();
//		ranks4.setRanks("이사");
//		ranks4.setRanksNo(4);
//		ranksRepo.save(ranks4);
//		RanksVo ranks3 = new RanksVo();
//		ranks3.setRanks("부장");
//		ranks3.setRanksNo(3);
//		ranksRepo.save(ranks3);
//		RanksVo ranks2 = new RanksVo();
//		ranks2.setRanks("팀장");
//		ranks2.setRanksNo(2);
//		ranksRepo.save(ranks2);
//		RanksVo ranks1 = new RanksVo();
//		ranks1.setRanks("사원");
//		ranks1.setRanksNo(1);
//		ranksRepo.save(ranks1);
//		
//		return "컴퍼니 생성완료";
//	}
//	
//	@RequestMapping("/createAdmin")
//	@ResponseBody
//	public String createMember() {
//		MemberVo member = new MemberVo();
//		member.setMemId("admin");
//		member.setMemPw(encoder.encode("admin"));
//		member.setMemName("권재범");
//		member.setRole(Role.ROLE_ADMIN);
////		member.setEnabled(true);
//
//		member.setMemJoinDate("2019-03-27");
//		member.setMemTel("010-2641-2684");
//		member.setMemOfficeTel("02-1234-1234");
//
//		member.setMemJumin("840415-1000000");
//		member.setMemSignUrl("/images/840415/sign.png");
//		member.setMemVacation(15);
//		
//		member.setMemAddrCode("07777");
//		member.setMemAddr("서울시 강남구 강남대로 5");
//		member.setMemAddrDetail("101호");
//		
//		DepartmentVo department3 = departmentRepo.findById("개발부").get();
//		member.setDepartment(department3);
////		TeamVo team5 = teamRepo.findById(5).get();
////		member.setTeam(team5);
//		RanksVo ranks = ranksRepo.findById("부장").get();
//		member.setRanks(ranks);
//		
//		memberRepo.save(member);
//		
//		return "ADMIN 하나 생성완료";
//	}
//	
//	@RequestMapping("/createUser")
//	@ResponseBody
//	public String createUser() {
//		MemberVo member = new MemberVo();
//		member.setMemId("911121001");
//		member.setMemPw(encoder.encode("911121001"));
//		member.setMemName("황준우");
//		member.setRole(Role.ROLE_USER);
////		member.setEnabled(true);
//		
//		member.setMemJoinDate("2019-03-27");
//		member.setMemTel("010-2641-2684");
//		member.setMemOfficeTel("02-1234-1234");
//		
//		member.setMemJumin("840415-1000001");
//		member.setMemSignUrl("/images/840415/sign.png");
//		member.setMemVacation(15);
//		
//		member.setMemAddrCode("07776");
//		member.setMemAddr("서울시 강남구 강남대로 4");
//		member.setMemAddrDetail("102호");
//		
//		DepartmentVo department2 = departmentRepo.findById("영업부").get();
//		member.setDepartment(department2);
//		TeamVo team3 = teamRepo.findById("영업1팀").get();
//		member.setTeam(team3);
//		RanksVo ranks = ranksRepo.findById("사원").get();
//		member.setRanks(ranks);
//		
//		memberRepo.save(member);
//		
//		return "USER 하나 생성완료";
//	}
//	
//	@RequestMapping("/createPm")
//	@ResponseBody
//	public String createPm() {
//		MemberVo member = new MemberVo();
//		member.setMemId("941230002");
//		member.setMemPw(encoder.encode("941230002"));
//		member.setMemName("정동원");
//		member.setRole(Role.ROLE_PM);
////		member.setEnabled(true);
//		
//		member.setMemJoinDate("2019-03-27");
//		member.setMemTel("010-2641-2684");
//		member.setMemOfficeTel("02-1234-1234");
//		
//		member.setMemJumin("840415-1000002");
//		member.setMemSignUrl("/images/840415/sign.png");
//		member.setMemVacation(15);
//		
//		member.setMemAddrCode("07775");
//		member.setMemAddr("서울시 강남구 강남대로 3");
//		member.setMemAddrDetail("103호");
//		
//		DepartmentVo department1 = departmentRepo.findById("지원부").get();
//		member.setDepartment(department1);
////		TeamVo team1 = teamRepo.findById(1).get();
////		member.setTeam(team1);
//		RanksVo ranks = ranksRepo.findById("부장").get();
//		member.setRanks(ranks);
//		
//		memberRepo.save(member);
//		
//		return "PM 하나 생성완료";
//	}
//	@RequestMapping("/createPl")
//	@ResponseBody
//	public String createPl() {
//		MemberVo member = new MemberVo();
//		member.setMemId("910111003");
//		member.setMemPw(encoder.encode("910111003"));
//		member.setMemName("홍길동");
//		member.setRole(Role.ROLE_PL);
////		member.setEnabled(true);
//		
//		member.setMemJoinDate("2019-03-27");
//		member.setMemTel("010-2641-2684");
//		member.setMemOfficeTel("02-1234-1234");
//		
//		member.setMemJumin("840415-1000003");
//		member.setMemSignUrl("/images/840415/sign.png");
//		member.setMemVacation(15);
//		
//		member.setMemAddrCode("07774");
//		member.setMemAddr("서울시 강남구 강남대로 1");
//		member.setMemAddrDetail("107호");
//		
//		DepartmentVo department1 = departmentRepo.findById("지원부").get();
//		member.setDepartment(department1);
//		TeamVo team2 = teamRepo.findById("기술지원팀").get();
//		member.setTeam(team2);
//		RanksVo ranks = ranksRepo.findById("팀장").get();
//		member.setRanks(ranks);
//		
//		memberRepo.save(member);
//		
//		return "PL 하나 생성완료";
//	}
//	
//	@RequestMapping("/map")
//	public String map() {
//		return "map";
//	}
//	@RequestMapping("/mapmap")
//	public String mapmap() {
//		return "mapmap";
//	}
	
}
