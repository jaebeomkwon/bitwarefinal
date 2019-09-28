package com.bitgroupware.project.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitgroupware.project.beans.MemberDto;
import com.bitgroupware.project.beans.ProjectInfoDto;
import com.bitgroupware.project.beans.ProjectMembersDto;
import com.bitgroupware.project.beans.ProjectRiskDto;
import com.bitgroupware.project.beans.ProjectRiskFileDto;
import com.bitgroupware.project.beans.ProjectWbsDto;
import com.bitgroupware.project.persistence.ProjectDao;
import com.bitgroupware.utils.Search;

@Service("ProjectService")
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	private ProjectDao dao;
	
	//진행중인 프로젝트 카운트 (페이징을 위한 값)
	@Override
	public int countProject(Search search) {
		if(search.getSearchCondition() == null)
			search.setSearchCondition("prjName");
		if(search.getSearchKeyword() == null)
			search.setSearchKeyword("");
		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		int count = 0;
		switch(searchCondition) {
		case "prjName":
			count = dao.countByPrjName(searchKeyword); //
			break;
		case "prjMothercompany":
			count = dao.countByPrjMothercompany(searchKeyword);
			break;
		}
		return count;
	}
	
	//완료된 프로젝트 카운트 (페이징을 위한 값)
	@Override
	public int countCompletedProject(Search search) {
		if(search.getSearchCondition() == null)
			search.setSearchCondition("prjName");
		if(search.getSearchKeyword() == null)
			search.setSearchKeyword("");
		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		int count = 0;
		switch(searchCondition) {
		case "prjName":
			count = dao.countByCompletedPrjName(searchKeyword); //
			break;
		case "prjMothercompany":
			count = dao.countByCompletedPrjMothercompany(searchKeyword);
			break;
		}
		return count;
	}
	
//	//전체 프로젝트 조회 (진행중인 프로젝트)
//	@Override
//	public List<ProjectInfoDto> selectProjectList() {
//		return dao.selectProjectList();
//	}
	
	//전체 프로젝트 조회 (진행중인 프로젝트+ 페이징, 검색) PRJ_NAME OR PRJ_MOTHERCOMPNAY
	@Override
	public List<ProjectInfoDto> selectProjectList(int begin, Search search) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("prjName");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");
		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		
		List<ProjectInfoDto> prjInfoDto = null;
		switch(searchCondition) {
		case "prjName":
			prjInfoDto = dao.selectProjectListToPrjName(begin, searchKeyword);
			break;
		case "prjMothercompany":
			prjInfoDto = dao.selectProjectListToPrjMothercompany(begin, searchKeyword);
			break;
		}
		return prjInfoDto;
	}
	
	//완료된 프로젝트 조회 (페이징, 검색) PRJ_NAME OR PRJ_MOTHERCOMPNAY
	@Override
	public List<ProjectInfoDto> selectEndProjectList(int begin, Search search) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("prjName");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");
		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		
		List<ProjectInfoDto> prjInfoDto = null;
		switch(searchCondition) {
		case "prjName":
			prjInfoDto = dao.selectEndProjectListToPrjName(begin, searchKeyword);
			break;
		case "prjMothercompany":
			prjInfoDto = dao.selectEndProjectListToPrjMothercompany(begin, searchKeyword);
			break;
		}
		return prjInfoDto;
	}

	//참여중인 프로젝트 조회 (페이징, 검색) PRJ_NAME OR PRJ_MOTHERCOMPNAY
	@Override
	public List<ProjectInfoDto> selectAttendProjectList(int begin, Search search, String memId) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("prjName");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");
		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		
		List<ProjectInfoDto> prjInfoDto = null;
		switch(searchCondition) {
		case "prjName":
			prjInfoDto = dao.selectAttendProjectListToPrjName(begin, searchKeyword, memId);
			break;
		case "prjMothercompany":
			prjInfoDto = dao.selectAttendProjectListToPrjMothercompany(begin, searchKeyword, memId);
			break;
		}
		return prjInfoDto;
	}

	//프로젝트 상세페이지 조회
	@Override
	public ProjectInfoDto selectProject(int prjCode) {
		return dao.selectProject(prjCode);
	}
	
	/*프로젝트 정보 수정*/
	@Override
	public void updateProject(ProjectInfoDto prjDto) {
		dao.updateProject(prjDto);
	}

	/*프로젝트 정보 생성 */
	@Override
	public boolean insertProject(ProjectInfoDto prjInfoDto) {
		return dao.insertProject(prjInfoDto);
	}
	
	/*프로젝트 참여인원 기본 리스트 출력*/
	@Override
	public List<MemberDto> selectProjectMemberList() {
		return dao.selectProjectMemberList();
	}
	
	/*프로젝트 참여인원 추가(생성)*/
	@Override
	public void insertProjectAttendMembers(String memId, int prjCode) {
		dao.insertProjectAttendMembers(memId, prjCode);
	}

	/*특정 프로젝트 참여인원 리스트 출력 */
	@Override
	public List<MemberDto> selectProjectAttendMemberList(int prjCode) {
		return dao.selectProjectAttendMemberList(prjCode);
	}
	
	/*프로젝트 WBS 정보 불러오기*/
	@Override
	public List<ProjectWbsDto> selectProjectWbsList(int prjCode) {
		return dao.selectProjectWbsList(prjCode);
	}
	
	/*프로젝트 WBS 삭제 */
	@Override
	public boolean deleteProjectWbsList(int prjCode) {
		return dao.deleteProjectWbsList(prjCode) == 1 ? true : false;
	}
	
	/*프로젝트 WBS 생성 */
	@Override
	public boolean insertProjectWbsList(List<ProjectWbsDto> prjWbsDto) {
		
		boolean chk = false;
		for(ProjectWbsDto prjWbsDtos : prjWbsDto) {
		chk = dao.insertProjectWbsList(prjWbsDtos) == 1 ? true : false;
		}
		return chk;
	}
	
	/*프로젝트 삭제 on delete cascade로 참여인원 테이블에서 참여인원도 같이 삭제 됨.*/
	@Override
	public void deleteProject(int prjCode) {
		dao.deleteProject(prjCode);
	}
	
	/*prjCode만 가져오기(완료되지 않은 프로젝트) */
	@Override
	public ProjectInfoDto selectPrjCode() {
		return dao.selectPrjCode();
	}
	

	/*프로젝트 참여인원 삭제 */
	@Override
	public void deleteProjectAttendMember(int prjMemNo) {
		dao.deleteProjectAttendMember(prjMemNo);
	}
	
	/*프로젝트 참여인원 삭제를 위한 주키 추출 */
	@Override
	public ProjectMembersDto selectPrjMemNo(int prjCode, String memId) {
		return dao.selectPrjMemNo(prjCode, memId);
	}
	
	/*프로젝트 완료 처리*/
	@Override
	public void completeProject(int prjCode) {
		dao.completeProject(prjCode);
	}

	/*멤버 아이디로 멤버 정보 뽑아오기*/
	@Override
	public MemberDto selectMemberInfos(String memId) {
		return dao.selectMemberInfos(memId);
	}
	
	/*달력에 wbs List 뿌리기*/
	@Override
	public List<ProjectWbsDto> selectProjectWbsOnCalendar(int prjCode) {
		return dao.selectProjectWbsOnCalendar(prjCode);
	}

	//memId로 Ranks(직급명) 가져오기
	@Override
	public MemberDto selectMemberRanksByMemId(String memId) {
		return dao.selectMemberRanksByMemId(memId);
	}
	
	/*위험관리대장*/
	
	/*위험관리대장 조회*/
	@Override
	public List<ProjectRiskDto> selectProjectRiskList(int begin, Search search, int prjCode) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("rskTitle");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");
		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		
		List<ProjectRiskDto> rskDto = null;
		switch(searchCondition) {
		case "rskTitle":
			rskDto = dao.selectProjectRiskListToRskTitle(begin, searchKeyword, prjCode);
			break;
		case "rskContent":
			rskDto = dao.selectProjectRiskListToRskContent(begin, searchKeyword, prjCode);
			break;
		}
		return rskDto;
	}
	
	/*위험관리대장 작성*/
	@Override
	public void insertProjectRisk(ProjectRiskDto rskDto) {
		dao.insertProjectRisk(rskDto);
	}
	
	/*위험관리대장 수정*/
	@Override
	public void updateProjectRisk(ProjectRiskDto rskDto) {
		dao.updateProjectRisk(rskDto);
	}
	
	/*위험관리대장 삭제*/
	@Override
	public void deleteProjectRisk(int rskCode) {
		dao.deleteProjectRisk(rskCode);
	}
	
	//위험관리대장 카운트 (페이징을 위한 값)
	@Override
	public int countProjectRisk(Search search, int prjCode) {
		if(search.getSearchCondition() == null)
			search.setSearchCondition("rskTitle");
		if(search.getSearchKeyword() == null)
			search.setSearchKeyword("");
		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		int count = 0;
		switch(searchCondition) {
		case "rskTitle":
			count = dao.countByRskTitle(prjCode, searchKeyword); //
			break;
		case "rskContent":
			count = dao.countByRskContent(prjCode, searchKeyword);
			break;
		}
		return count;
	}
		
	//최근 프로젝트 코드 1개 추출
	@Override
	public int selectRecentPrjCode() {
		return dao.selectRecentPrjCode();
	}

	/*위험관리대장 상세페이지 데이터 추출*/
	@Override
	public ProjectRiskDto selectProjectRiskDetail(int rskCode) {
		
		return dao.selectProjectRiskDetail(rskCode);
	}

	/*위험관리대장 프로젝트 리스트 추출*/
	@Override
	public List<ProjectInfoDto> selectProjectNameList() {
		return dao.selectProjectNameList();
	}

	/*위험관리대장 파일첨부*/
	@Override
	public void insertProjectRiskFile(ProjectRiskFileDto rskFileDto) {
		dao.insertProjectRiskFile(rskFileDto);
		
	}

	/*위험관리대장 파일 체크*/
	@Override
	public void updateProjectRiskFileCheck(int rskCode) {
		dao.updateProjectRiskFileCheck(rskCode);
	}

	/*위험관리대장 파일 불러오기*/
	@Override
	public List<ProjectRiskFileDto> selectProjectRiskFile(int rskCode) {
		return dao.selectProjectRiskFile(rskCode);
	}

	/*위험관리대장 파일 수정*/
	@Override
	public void updateProjectRiskFile(ProjectRiskFileDto rskFileDto) {
		dao.updateProjectRiskFile(rskFileDto); 
	}
	
}
