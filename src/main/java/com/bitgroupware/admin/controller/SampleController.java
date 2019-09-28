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
public class SampleController {

	@Autowired
	private MemberRepository memberRepo;
	@Autowired
	private NoticeRepository noticeRepo;
	@Autowired
	private DocCenterRepository docCenterRepo;
	@Autowired
	private MeetingroomRepository meetingroomRepo;
	@Autowired
	private DepartmentRepository departmentRepo;
	@Autowired
	private TeamRepository teamRepo;
	@Autowired
	private RanksRepository ranksRepo;
	@Autowired
	private CarRepository carRepo;
	@Autowired
	private PasswordEncoder encoder;
	
	@RequestMapping("/createCompany")
	@ResponseBody
	public String createCompany() {
		DepartmentVo department1 = new DepartmentVo();
		department1.setDeptName("지원부");
		department1.setDeptNo(1);
		departmentRepo.save(department1);
		DepartmentVo department2 = new DepartmentVo();
		department2.setDeptName("영업부");
		department2.setDeptNo(2);
		departmentRepo.save(department2);
		DepartmentVo department3 = new DepartmentVo();
		department3.setDeptName("개발부");
		department3.setDeptNo(3);
		departmentRepo.save(department3);
		
		DepartmentVo department4 = departmentRepo.findById("지원부").get();
		TeamVo team1 = new TeamVo();
		team1.setDepartment(department4);
		team1.setTeamName("경영지원팀");
		team1.setTeamNo(1);
		teamRepo.save(team1);
		TeamVo team2 = new TeamVo();
		team2.setDepartment(department4);
		team2.setTeamName("기술지원팀");
		team2.setTeamNo(2);
		teamRepo.save(team2);
		DepartmentVo department5 = departmentRepo.findById("영업부").get();
		TeamVo team3 = new TeamVo();
		team3.setDepartment(department5);
		team3.setTeamName("영업1팀");
		team3.setTeamNo(3);
		teamRepo.save(team3);
		TeamVo team4 = new TeamVo();
		team4.setDepartment(department5);
		team4.setTeamName("영업2팀");
		team4.setTeamNo(4);
		teamRepo.save(team4);
		DepartmentVo department6 = departmentRepo.findById("개발부").get();
		TeamVo team5 = new TeamVo();
		team5.setDepartment(department6);
		team5.setTeamName("개발1팀");
		team5.setTeamNo(5);
		teamRepo.save(team5);
		TeamVo team6 = new TeamVo();
		team6.setDepartment(department6);
		team6.setTeamName("개발2팀");
		team6.setTeamNo(6);
		teamRepo.save(team6);
		
		RanksVo ranks5 = new RanksVo();
		ranks5.setRanks("대표");
		ranks5.setRanksNo(5);
		ranksRepo.save(ranks5);
		RanksVo ranks4 = new RanksVo();
		ranks4.setRanks("이사");
		ranks4.setRanksNo(4);
		ranksRepo.save(ranks4);
		RanksVo ranks3 = new RanksVo();
		ranks3.setRanks("부장");
		ranks3.setRanksNo(3);
		ranksRepo.save(ranks3);
		RanksVo ranks2 = new RanksVo();
		ranks2.setRanks("팀장");
		ranks2.setRanksNo(2);
		ranksRepo.save(ranks2);
		RanksVo ranks1 = new RanksVo();
		ranks1.setRanks("사원");
		ranks1.setRanksNo(1);
		ranksRepo.save(ranks1);
		
		return "Company creation completed";
	}
	
	@RequestMapping("/createMember")
	@ResponseBody
	public String createMember() {
		createAdmin();
		createBoss();
		createDirector();
		createHeader1();
		createHeader2();
		createHeader3();
		createTeamLeader11();
		createTeamLeader12();
		createTeamLeader21();
		createTeamLeader22();
		createTeamLeader31();
		createTeamLeader32();
		createUser11();
		createUser12();
		createUser21();
		createUser22();
		createUser31();
		createUser32();
		return "Member creation completed";
	}
	
	@RequestMapping("/createNotice")
	@ResponseBody
	public String createNotice() {
		MemberVo admin = memberRepo.findById("admin").get();
		for(int i=1; i<=342; i++) {
			NoticeVo notice = new NoticeVo();
			notice.setNtCate("일반");
			notice.setNtTitle("글제목"+i);
			notice.setNtContent("글내용"+i);
			notice.setNtFileCheck("N");
			notice.setMember(admin);
			
			noticeRepo.save(notice);
		}
		return "Notice creation completed";
	}
	
	@RequestMapping("/createDocCenter")
	@ResponseBody
	public String createDocCenter() {
		MemberVo admin = memberRepo.findById("admin").get();
		for(int i=1; i<=342; i++) {
			DocCenterVo docCenter = new DocCenterVo();
			docCenter.setDocCate("일반");
			docCenter.setDocTitle("글제목"+i);
			docCenter.setDocContent("글내용"+i);
			docCenter.setDocFileCheck("N");
			docCenter.setMember(admin);
			
			docCenterRepo.save(docCenter);
		}
		return "DocumentCenter creation completed";
	}
	
	@RequestMapping("/createMeetingroom")
	@ResponseBody
	public String createMeetionroom() {
		for(int i=1; i<=4; i++) {
			MeetingroomVo mr = new MeetingroomVo();
			mr.setMrName("회의실"+i);
			mr.setMrMaxPerson(i*4);
			meetingroomRepo.save(mr);
		}
		return "Meetingroom creation completed";
	}
	
	@RequestMapping("/createCar")
	@ResponseBody
	public String createCar() {
		for(int i=1; i<=4; i++) {
			CarVo car = new CarVo();
			car.setCarName("차량"+i);
			car.setCarPlate("01하000"+i);
			carRepo.save(car);
		}
		return "Vehicle creation completed";
	}
	
	private void createAdmin() {
		MemberVo member = new MemberVo();
		member.setMemId("admin");
		member.setMemPw(encoder.encode("admin"));
		member.setMemName("관리자");
		member.setRole(Role.ROLE_ADMIN);
		RanksVo ranks = ranksRepo.findById("사원").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createBoss() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327001");
		member.setMemPw(encoder.encode("20190327001"));
		member.setMemName("이성계");
		member.setRole(Role.ROLE_PM);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-2458-5845");
		member.setMemOfficeTel("9001");
		member.setMemJumin("510115-1515568");
		member.setMemSignUrl("/memSign/20190327001.png");
		member.setMemAddrCode("7777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		RanksVo ranks = ranksRepo.findById("대표").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createDirector() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327002");
		member.setMemPw(encoder.encode("20190327002"));
		member.setMemName("이준호");
		member.setRole(Role.ROLE_PM);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-5124-4584");
		member.setMemOfficeTel("9002");
		member.setMemJumin("590415-1055145");
		member.setMemSignUrl("/memSign/20190327002.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		RanksVo ranks = ranksRepo.findById("이사").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createHeader1() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327003");
		member.setMemPw(encoder.encode("20190327003"));
		member.setMemName("박태영");
		member.setRole(Role.ROLE_PM);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-4517-5247");
		member.setMemOfficeTel("1001");
		member.setMemJumin("660415-1085474");
		member.setMemSignUrl("/memSign/20190327003.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		DepartmentVo department = departmentRepo.findById("지원부").get();
		member.setDepartment(department);
		RanksVo ranks = ranksRepo.findById("부장").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createHeader2() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327004");
		member.setMemPw(encoder.encode("20190327004"));
		member.setMemName("송인한");
		member.setRole(Role.ROLE_PM);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-2548-4856");
		member.setMemOfficeTel("2001");
		member.setMemJumin("671212-1058441");
		member.setMemSignUrl("/memSign/20190327004.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		DepartmentVo department = departmentRepo.findById("영업부").get();
		member.setDepartment(department);
		RanksVo ranks = ranksRepo.findById("부장").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createHeader3() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327005");
		member.setMemPw(encoder.encode("20190327005"));
		member.setMemName("권재범");
		member.setRole(Role.ROLE_PM);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-2641-2683");
		member.setMemOfficeTel("3001");
		member.setMemJumin("840415-1047451");
		member.setMemSignUrl("/memSign/20190327005.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		DepartmentVo department = departmentRepo.findById("개발부").get();
		member.setDepartment(department);
		RanksVo ranks = ranksRepo.findById("부장").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createTeamLeader11() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327006");
		member.setMemPw(encoder.encode("20190327006"));
		member.setMemName("장진선");
		member.setRole(Role.ROLE_PL);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-5124-9565");
		member.setMemOfficeTel("1002");
		member.setMemJumin("770412-2044152");
		member.setMemSignUrl("/memSign/20190327006.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		DepartmentVo department = departmentRepo.findById("지원부").get();
		member.setDepartment(department);
		TeamVo team = teamRepo.findById("경영지원팀").get();
		member.setTeam(team);
		RanksVo ranks = ranksRepo.findById("팀장").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createTeamLeader12() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327007");
		member.setMemPw(encoder.encode("20190327007"));
		member.setMemName("이수연");
		member.setRole(Role.ROLE_PL);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-5689-8152");
		member.setMemOfficeTel("1003");
		member.setMemJumin("770419-2045852");
		member.setMemSignUrl("/memSign/20190327007.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		DepartmentVo department = departmentRepo.findById("지원부").get();
		member.setDepartment(department);
		TeamVo team = teamRepo.findById("기술지원팀").get();
		member.setTeam(team);
		RanksVo ranks = ranksRepo.findById("팀장").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createTeamLeader21() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327008");
		member.setMemPw(encoder.encode("20190327008"));
		member.setMemName("김범홍");
		member.setRole(Role.ROLE_PL);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-2458-4584");
		member.setMemOfficeTel("2002");
		member.setMemJumin("750419-1044352");
		member.setMemSignUrl("/memSign/20190327008.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		DepartmentVo department = departmentRepo.findById("영업부").get();
		member.setDepartment(department);
		TeamVo team = teamRepo.findById("영업1팀").get();
		member.setTeam(team);
		RanksVo ranks = ranksRepo.findById("팀장").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createTeamLeader22() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327009");
		member.setMemPw(encoder.encode("20190327009"));
		member.setMemName("김승하");
		member.setRole(Role.ROLE_PL);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-5124-1265");
		member.setMemOfficeTel("2003");
		member.setMemJumin("740412-1047842");
		member.setMemSignUrl("/memSign/20190327009.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		DepartmentVo department = departmentRepo.findById("영업부").get();
		member.setDepartment(department);
		TeamVo team = teamRepo.findById("영업2팀").get();
		member.setTeam(team);
		RanksVo ranks = ranksRepo.findById("팀장").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createTeamLeader31() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327010");
		member.setMemPw(encoder.encode("20190327010"));
		member.setMemName("정동원");
		member.setRole(Role.ROLE_PL);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-1234-5678");
		member.setMemOfficeTel("3002");
		member.setMemJumin("770525-1045852");
		member.setMemSignUrl("/memSign/20190327010.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		DepartmentVo department = departmentRepo.findById("개발부").get();
		member.setDepartment(department);
		TeamVo team = teamRepo.findById("개발1팀").get();
		member.setTeam(team);
		RanksVo ranks = ranksRepo.findById("팀장").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createTeamLeader32() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327011");
		member.setMemPw(encoder.encode("20190327011"));
		member.setMemName("최창국");
		member.setRole(Role.ROLE_PL);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-9518-4521");
		member.setMemOfficeTel("3003");
		member.setMemJumin("770122-1065452");
		member.setMemSignUrl("/memSign/20190327011.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		DepartmentVo department = departmentRepo.findById("개발부").get();
		member.setDepartment(department);
		TeamVo team = teamRepo.findById("개발2팀").get();
		member.setTeam(team);
		RanksVo ranks = ranksRepo.findById("팀장").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createUser11() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327012");
		member.setMemPw(encoder.encode("20190327012"));
		member.setMemName("윤태원");
		member.setRole(Role.ROLE_USER);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-9513-4521");
		member.setMemOfficeTel("1004");
		member.setMemJumin("870122-1065452");
		member.setMemSignUrl("/memSign/20190327012.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		DepartmentVo department = departmentRepo.findById("지원부").get();
		member.setDepartment(department);
		TeamVo team = teamRepo.findById("경영지원팀").get();
		member.setTeam(team);
		RanksVo ranks = ranksRepo.findById("사원").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createUser12() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327013");
		member.setMemPw(encoder.encode("20190327013"));
		member.setMemName("김진광");
		member.setRole(Role.ROLE_USER);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-9513-6521");
		member.setMemOfficeTel("1005");
		member.setMemJumin("860111-1088452");
		member.setMemSignUrl("/memSign/20190327013.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		DepartmentVo department = departmentRepo.findById("지원부").get();
		member.setDepartment(department);
		TeamVo team = teamRepo.findById("기술지원팀").get();
		member.setTeam(team);
		RanksVo ranks = ranksRepo.findById("사원").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createUser21() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327014");
		member.setMemPw(encoder.encode("20190327014"));
		member.setMemName("장한얼");
		member.setRole(Role.ROLE_USER);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-5124-1111");
		member.setMemOfficeTel("2004");
		member.setMemJumin("890111-1084452");
		member.setMemSignUrl("/memSign/20190327014.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		DepartmentVo department = departmentRepo.findById("영업부").get();
		member.setDepartment(department);
		TeamVo team = teamRepo.findById("영업1팀").get();
		member.setTeam(team);
		RanksVo ranks = ranksRepo.findById("사원").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createUser22() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327015");
		member.setMemPw(encoder.encode("20190327015"));
		member.setMemName("나영균");
		member.setRole(Role.ROLE_USER);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-5124-1548");
		member.setMemOfficeTel("2005");
		member.setMemJumin("890712-1082352");
		member.setMemSignUrl("/memSign/20190327015.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		DepartmentVo department = departmentRepo.findById("영업부").get();
		member.setDepartment(department);
		TeamVo team = teamRepo.findById("영업2팀").get();
		member.setTeam(team);
		RanksVo ranks = ranksRepo.findById("사원").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createUser31() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327016");
		member.setMemPw(encoder.encode("20190327016"));
		member.setMemName("홍한솔");
		member.setRole(Role.ROLE_USER);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-5111-1111");
		member.setMemOfficeTel("3004");
		member.setMemJumin("890919-2084452");
		member.setMemSignUrl("/memSign/20190327016.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		DepartmentVo department = departmentRepo.findById("개발부").get();
		member.setDepartment(department);
		TeamVo team = teamRepo.findById("개발1팀").get();
		member.setTeam(team);
		RanksVo ranks = ranksRepo.findById("사원").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
	private void createUser32() {
		MemberVo member = new MemberVo();
		member.setMemId("20190327017");
		member.setMemPw(encoder.encode("20190327017"));
		member.setMemName("황준우");
		member.setRole(Role.ROLE_USER);
		member.setMemJoinDate("2019-03-27");
		member.setMemTel("010-5121-1111");
		member.setMemOfficeTel("3005");
		member.setMemJumin("890317-1054852");
		member.setMemSignUrl("/memSign/20190327017.png");
		member.setMemAddrCode("07777");
		member.setMemAddr("서울시 강남구 강남대로 5");
		member.setMemAddrDetail("101호");
		DepartmentVo department = departmentRepo.findById("개발부").get();
		member.setDepartment(department);
		TeamVo team = teamRepo.findById("개발2팀").get();
		member.setTeam(team);
		RanksVo ranks = ranksRepo.findById("사원").get();
		member.setRanks(ranks);
		memberRepo.save(member);
	}
}
