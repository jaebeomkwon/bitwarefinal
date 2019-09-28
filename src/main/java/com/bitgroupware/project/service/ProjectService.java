package com.bitgroupware.project.service;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.bitgroupware.project.beans.MemberDto;
import com.bitgroupware.project.beans.ProjectInfoDto;
import com.bitgroupware.project.beans.ProjectMembersDto;
import com.bitgroupware.project.beans.ProjectRiskDto;
import com.bitgroupware.project.beans.ProjectRiskFileDto;
import com.bitgroupware.project.beans.ProjectWbsDto;
import com.bitgroupware.utils.Search;

public interface ProjectService {
	
	//프로젝트 카운트 (페이징을 위한 값)
	public int countProject(Search search);
	
	//프로젝트 카운트 (페이징을 위한 값)
	public int countCompletedProject(Search search);
	
//	//전체 프로젝트 조회(진행중인 프로젝트)
//	public List<ProjectInfoDto> selectProjectList();
	
	//전체 프로젝트 조회(진행중인 프로젝트+ 페이징, 검색)
	public List<ProjectInfoDto> selectProjectList(int begin, Search search);
	
	//완료된 프로젝트 조회
	public List<ProjectInfoDto> selectEndProjectList(int begin, Search search);
	
	//참여중인 프로젝트 조회
	public List<ProjectInfoDto> selectAttendProjectList(int begin, Search search, String memId);
	
	//프로젝트 상세페이지 조회
	public ProjectInfoDto selectProject(int prjCode);
	
	/*프로젝트 정보 수정 */
	public void updateProject(ProjectInfoDto prjDto);
	
	/*프로젝트 정보 생성 */
	public boolean insertProject(ProjectInfoDto prjInfoDto);
	
	/*프로젝트 삭제 on delete cascade로 참여인원 테이블에서 참여인원도 같이 삭제 됨.*/
	public void deleteProject(int prjCode);
	
	/*프로젝트 참여인원 기본 리스트 출력*/
	public List<MemberDto> selectProjectMemberList();
	
	/*프로젝트 참여인원 추가(생성)*/
	public void insertProjectAttendMembers(String memId, int prjCode);
	
	/*특정 프로젝트 참여인원 리스트 출력 */
	public List<MemberDto> selectProjectAttendMemberList(int prjCode);
	
	/*프로젝트 WBS 정보 불러오기*/
	public List<ProjectWbsDto> selectProjectWbsList(int prjCode);
	
	/*프로젝트 WBS 삭제 */
	public boolean deleteProjectWbsList(int prjCode);
	
	/*프로젝트 WBS 생성 */
	public boolean insertProjectWbsList(List<ProjectWbsDto> prjWbsDto);
	
	/*prjCode만 가져오기(완료되지 않은 프로젝트) */
	public ProjectInfoDto selectPrjCode();
	
	/*프로젝트 참여인원 삭제를 위한 주키 추출 */
	public ProjectMembersDto selectPrjMemNo(int prjCode, String memId);
	
	/*프로젝트 참여인원 삭제 */
	public void deleteProjectAttendMember(int prjMemNo);
	
	/*프로젝트 완료 처리*/
	public void completeProject(int prjCode);
	
	/*멤버 아이디로 멤버 정보 뽑아오기*/
	public MemberDto selectMemberInfos(String memId);
	
	/*달력에 wbs List 뿌리기*/
	public List<ProjectWbsDto> selectProjectWbsOnCalendar(int prjCode);
	
	/*memId로 Ranks(직급명) 가져오기*/
	public MemberDto selectMemberRanksByMemId(String memId);
	
	/*위험관리대장*/
	
	/*위험관리대장 조회*/
	public List<ProjectRiskDto> selectProjectRiskList(int begin, Search search, int prjCode);
	
	/*위험관리대장 상세페이지 데이터 추출*/
	public ProjectRiskDto selectProjectRiskDetail(int rskCode);
	
	/*위험관리대장 작성*/
	public void insertProjectRisk(ProjectRiskDto rskDto);

	/*위험관리대장 수정*/
	public void updateProjectRisk(ProjectRiskDto rskDto);
	
	/*위험관리대장 삭제*/
	public void deleteProjectRisk(int rskCode);
	
	//위험관리대장 카운트 (페이징을 위한 값)
	public int countProjectRisk(Search search, int prjCode);
	
	//최근 프로젝트 코드 1개 추출
	public int selectRecentPrjCode();
	
	/*위험관리대장 프로젝트 리스트 추출*/
	public List<ProjectInfoDto> selectProjectNameList();
	
	/*위험관리대장 파일첨부*/
	public void insertProjectRiskFile(ProjectRiskFileDto rskFileDto);
	
	/*위험관리대장 파일 체크*/
	public void updateProjectRiskFileCheck(int rskCode);
	
	/*위험관리대장 파일 불러오기*/
	public List<ProjectRiskFileDto> selectProjectRiskFile(int rskCode);
	
	/*위험관리대장 파일 수정*/
	public void updateProjectRiskFile(ProjectRiskFileDto rskFileDto);
}
