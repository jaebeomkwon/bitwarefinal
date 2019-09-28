package com.bitgroupware.project.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bitgroupware.project.beans.MemberDto;
import com.bitgroupware.project.beans.ProjectInfoDto;
import com.bitgroupware.project.beans.ProjectMembersDto;
import com.bitgroupware.project.beans.ProjectRiskDto;
import com.bitgroupware.project.beans.ProjectRiskFileDto;
import com.bitgroupware.project.beans.ProjectWbsDto;
import com.bitgroupware.utils.Search;

@Mapper
public interface ProjectDao {
	
	//전체 프로젝트 조회(진행중인 프로젝트+ 페이징, 검색) PRJ_NAME
	@Select("select r1.* from (SELECT * FROM PROJECT_INFO WHERE PRJ_COMPLETION = 0 AND PRJ_NAME LIKE #{searchKeyword} ORDER BY PRJ_CODE DESC) r1 limit 10 offset #{begin}")
	public List<ProjectInfoDto> selectProjectListToPrjName(int begin, String searchKeyword);
	
	//전체 프로젝트 조회(진행중인 프로젝트+ 페이징, 검색) PRJ_MOTHERCOMPANY
	@Select("select r1.* from (SELECT * FROM PROJECT_INFO WHERE PRJ_COMPLETION = 0 AND PRJ_MOTHERCOMPANY LIKE #{searchKeyword} ORDER BY PRJ_CODE DESC) r1 limit 10 offset #{begin}")
	public List<ProjectInfoDto> selectProjectListToPrjMothercompany(int begin, String searchKeyword);
	
	//완료된 프로젝트 조회(페이징, 검색) PRJ_NAME
	@Select("select r1.* from (SELECT * FROM PROJECT_INFO WHERE PRJ_COMPLETION = 1 AND PRJ_NAME LIKE #{searchKeyword} ORDER BY PRJ_CODE DESC) r1 limit 10 offset #{begin}")
	public List<ProjectInfoDto> selectEndProjectListToPrjName(int begin, String searchKeyword);
	
	//완료된 프로젝트 조회(페이징, 검색) PRJ_MOTHERCOMPANY
	@Select("select r1.* from (SELECT * FROM PROJECT_INFO WHERE PRJ_COMPLETION = 1 AND PRJ_MOTHERCOMPANY LIKE #{searchKeyword} ORDER BY PRJ_CODE DESC) r1 limit 10 offset #{begin}")
	public List<ProjectInfoDto> selectEndProjectListToPrjMothercompany(int begin, String searchKeyword);
	
	//참여중인 프로젝트 조회(페이징, 검색) PRJ_NAME
	@Select("select r1.* from (SELECT * FROM PROJECT_INFO WHERE PRJ_COMPLETION = 0 AND PRJ_NAME LIKE #{searchKeyword} AND PRJ_CODE = ANY(SELECT PRJ_CODE FROM PROJECT_MEMBERS WHERE MEM_ID = #{memId}) ORDER BY PRJ_CODE DESC) r1 limit 10 offset #{begin}")
	public List<ProjectInfoDto> selectAttendProjectListToPrjName(int begin, String searchKeyword, String memId);
	
	//참여중인 프로젝트 조회(페이징, 검색) PRJ_MOTHERCOMPANY
	@Select("select r1.* from (SELECT * FROM PROJECT_INFO WHERE PRJ_COMPLETION = 0 AND PRJ_MOTHERCOMPANY LIKE #{searchKeyword} AND PRJ_CODE = ANY(SELECT PRJ_CODE FROM PROJECT_MEMBERS WHERE MEM_ID = #{memId}) ORDER BY PRJ_CODE DESC) r1 limit 10 offset #{begin}")
	public List<ProjectInfoDto> selectAttendProjectListToPrjMothercompany(int begin, String searchKeyword, String memId);
	
	//프로젝트 상세페이지 조회
	@Select("SELECT * FROM PROJECT_INFO WHERE PRJ_CODE = #{prjCode}")
	public ProjectInfoDto selectProject(int prjCode);
	
	/*프로젝트 정보 수정 */
	@Update("UPDATE PROJECT_INFO SET PRJ_NAME=#{prjName}, PRJ_DEPOSIT=#{prjDeposit}, PRJ_WORKING_EXPENSES=#{prjWorkingExpenses}, PRJ_START=#{prjStart}, PRJ_END=#{prjEnd}, PRJ_MOTHERCOMPANY=#{prjMothercompany} WHERE PRJ_CODE=#{prjCode}")
	public void updateProject(ProjectInfoDto prjDto);
	
	/*프로젝트 정보 생성 */
	@Insert("INSERT INTO PROJECT_INFO (PRJ_NAME, PRJ_DEPOSIT, PRJ_WORKING_EXPENSES, PRJ_START, PRJ_END, PRJ_MOTHERCOMPANY, PRJ_PM) VALUES (#{prjName}, #{prjDeposit}, #{prjWorkingExpenses}, #{prjStart}, #{prjEnd}, #{prjMothercompany}, #{prjPm})")
	public boolean insertProject(ProjectInfoDto prjDto);
	
	/*프로젝트 삭제 on delete cascade로 참여인원 테이블에서 참여인원도 같이 삭제 됨.*/
	@Delete("DELETE FROM PROJECT_INFO WHERE PRJ_CODE = #{prjCode}")
	public void deleteProject(int prjCode);
	
	/*프로젝트 참여인원 기본 리스트 출력*/
	@Select("SELECT MEM_ID, MEM_NAME, DEPT_NAME, TEAM_NAME, RANKS FROM MEMBER WHERE DEPT_NAME='개발부' "
			+ " ORDER BY (CASE RANKS WHEN '부장' THEN 1 WHEN '팀장' THEN 2 WHEN '사원' THEN 3 END); ")
	public List<MemberDto> selectProjectMemberList();
	
	/*프로젝트 참여인원 추가(생성)*/
	@Insert("INSERT INTO PROJECT_MEMBERS (PRJ_CODE, MEM_ID) VALUES (#{prjCode}, #{memId})")
	public void insertProjectAttendMembers(String memId, int prjCode);
	
	/*특정 프로젝트 참여인원 리스트 출력 */
	@Select("SELECT * FROM MEMBER WHERE MEM_ID = ANY(SELECT MEM_ID FROM PROJECT_MEMBERS WHERE PRJ_CODE = #{prjCode}) ORDER BY (CASE RANKS WHEN '부장' THEN 1 WHEN '팀장' THEN 2 WHEN '사원' THEN 3 END)")
	public List<MemberDto> selectProjectAttendMemberList(int prjCode);

	/*프로젝트 WBS 정보 불러오기*/
	@Select("SELECT PRJ_CODE, PRJ_WORK_NAME, PRJ_GROUP, PRJ_STEP, PRJ_DEPTH, PRJ_MANAGER, PRJ_OUTPUT, PRJ_PLAN_START,"
			+ "PRJ_PLAN_END, PRJ_REAL_START, PRJ_REAL_END, PRJ_WORK_COMPLETION, PRJ_TOTAL_DAYS FROM PROJECT_WBS "
			+ "WHERE PRJ_CODE = #{prjCode} ORDER BY PRJ_GROUP, PRJ_STEP ASC")
	public List<ProjectWbsDto> selectProjectWbsList(int prjCode);
	
	/*프로젝트 WBS 삭제 */
	@Delete("DELETE FROM PROJECT_WBS WHERE PRJ_CODE = #{prjCode}")
	public int deleteProjectWbsList(int prjCode);
	
	/*프로젝트 WBS 생성 */
	public int insertProjectWbsList(ProjectWbsDto prjWbsDto);
	
	/*prjCode만 가져오기(완료되지 않은 프로젝트) */
	@Select("SELECT PRJ_CODE FROM PROJECT_INFO WHERE PRJ_COMPLETION = 0 ORDER BY PRJ_CODE DESC LIMIT 1")
	public ProjectInfoDto selectPrjCode();
	
	/*프로젝트 참여인원 삭제를 위한 주키 추출 */
	@Select("SELECT PRJ_MEM_NO FROM PROJECT_MEMBERS WHERE PRJ_CODE = #{prjCode} AND MEM_ID = #{memId}")
	public ProjectMembersDto selectPrjMemNo(int prjCode, String memId);
	
	/*프로젝트 참여인원 삭제 */
	@Delete("DELETE FROM PROJECT_MEMBERS WHERE PRJ_MEM_NO = #{prjMemNo}")
	public void deleteProjectAttendMember(int prjMemNo);
	
	/*프로젝트 완료 처리*/
	@Update("UPDATE PROJECT_INFO SET PRJ_COMPLETION = 1 WHERE PRJ_CODE = #{prjCode}")
	public void completeProject(int prjCode);
	
	/*멤버 아이디로 멤버 정보 뽑아오기*/
	@Select("SELECT MEM_NAME, DEPT_NAME, TEAM_NAME, RANKS FROM MEMBER WHERE MEM_ID = #{memId}")
	public MemberDto selectMemberInfos(String memId);
	
	/*달력에 wbs List 뿌리기*/
	@Select("SELECT * FROM PROJECT_WBS WHERE PRJ_CODE = #{prjCode}")
	public List<ProjectWbsDto> selectProjectWbsOnCalendar(int prjCode);
	
	/*프로젝트 카운트(페이징을 위한 카운트 값)*/
	//진행중인 프로젝트 이름 기준 데이터 카운트
	@Select("SELECT COUNT(*) FROM PROJECT_INFO WHERE PRJ_COMPLETION = 0 AND PRJ_NAME LIKE #{searchKeyword}")
	public int countByPrjName(String searchKeyword);
	
	//진행중인 프로젝트의 마더업체 이름 기준 데이터 카운트
	@Select("SELECT COUNT(*) FROM PROJECT_INFO WHERE PRJ_COMPLETION = 0 AND PRJ_MOTHERCOMPANY LIKE #{searchKeyword}")
	public int countByPrjMothercompany(String searchKeyword);
	
	//완료된 프로젝트 이름 기준 데이터 카운트
	@Select("SELECT COUNT(*) FROM PROJECT_INFO WHERE PRJ_COMPLETION = 1 AND PRJ_NAME LIKE #{searchKeyword}")
	public int countByCompletedPrjName(String searchKeyword);
	
	//완료된 프로젝트의 마더업체 이름 기준 데이터 카운트
	@Select("SELECT COUNT(*) FROM PROJECT_INFO WHERE PRJ_COMPLETION = 1 AND PRJ_MOTHERCOMPANY LIKE #{searchKeyword}")
	public int countByCompletedPrjMothercompany(String searchKeyword);
	
	//memId로 Ranks(직급명) 가져오기
	@Select("SELECT RANKS FROM MEMBER WHERE MEM_ID = #{memId}")
	public MemberDto selectMemberRanksByMemId(String memId);
	
	//참여중인 프로젝트 조회 (메인 사용)
	@Select("select r1.* from (SELECT * FROM PROJECT_INFO WHERE PRJ_COMPLETION = 0 AND PRJ_CODE = ANY(SELECT PRJ_CODE FROM PROJECT_MEMBERS WHERE MEM_ID = #{memId}) ORDER BY PRJ_CODE DESC) r1 limit 10")
	public List<ProjectInfoDto> selectMainAttendProjectList(String memId);
	
	/*위험관리대장*/
	
	/*위험관리대장 제목 기준 조회*/
	@Select("select r1.* from (SELECT * FROM PROJECT_RISK WHERE PRJ_CODE = #{prjCode} AND RSK_TITLE LIKE #{searchKeyword} ORDER BY RSK_CODE DESC) r1 limit 10 offset #{begin}")
	public List<ProjectRiskDto> selectProjectRiskListToRskTitle(int begin, String searchKeyword, int prjCode);

	/*위험관리대장 내용 기준 조회*/
	@Select("select r1.* from (SELECT * FROM PROJECT_RISK WHERE PRJ_CODE = #{prjCode} AND RSK_CONTENT LIKE #{searchKeyword} ORDER BY RSK_CODE DESC) r1 limit 10 offset #{begin}")
	public List<ProjectRiskDto> selectProjectRiskListToRskContent(int begin, String searchKeyword, int prjCode);
	
	/*위험관리대장 상세페이지 데이터 추출 (수정 페이지 이동에도 사용)*/
	@Select("SELECT * FROM PROJECT_RISK WHERE RSK_CODE = #{rskCode}")
	public ProjectRiskDto selectProjectRiskDetail(int rskCode);
	
	/*위험관리대장 작성*/
	@Insert("INSERT INTO PROJECT_RISK (RSK_TITLE, RSK_CONTENT, RSK_WRITER, MEM_NAME, RSK_SOLUTION, RSK_RESULT, PRJ_CODE, RSK_REG) "
			+ " VALUES (#{rskTitle}, #{rskContent}, #{rskWriter}, #{memName}, #{rskSolution}, #{rskResult}, #{prjCode}, now())")
	public void insertProjectRisk(ProjectRiskDto rskDto);

	/*위험관리대장 수정*/
	@Update("UPDATE PROJECT_RISK SET RSK_TITLE=#{rskTitle}, RSK_CONTENT=#{rskContent}, RSK_SOLUTION=#{rskSolution}, RSK_RESULT=#{rskResult} WHERE RSK_CODE = #{rskCode}")
	public void updateProjectRisk(ProjectRiskDto rskDto);
	
	/*위험관리대장 삭제*/
	@Delete("DELETE FROM PROJECT_RISK WHERE RSK_CODE = #{rskCode}")
	public void deleteProjectRisk(int rskCode);
	
	//위험관리대장 제목 기준 데이터 카운트
	@Select("SELECT COUNT(*) FROM PROJECT_RISK WHERE PRJ_CODE = #{prjCode} AND RSK_TITLE LIKE #{searchKeyword}")
	public int countByRskTitle(int prjCode, String searchKeyword);
	
	//위험관리대장 내용 기준 데이터 카운트
	@Select("SELECT COUNT(*) FROM PROJECT_RISK WHERE PRJ_CODE = #{prjCode} AND RSK_TITLE LIKE #{searchKeyword}")
	public int countByRskContent(int prjCode, String searchKeyword);
	
	//최근 프로젝트 코드 1개 추출
	@Select("SELECT PRJ_CODE FROM PROJECT_INFO ORDER BY PRJ_CODE DESC LIMIT 1")
	public int selectRecentPrjCode();
	
	/*위험관리대장 프로젝트 리스트 추출*/
	@Select("select r1.* from (SELECT * FROM PROJECT_INFO WHERE PRJ_COMPLETION = 0 ORDER BY PRJ_CODE DESC) r1")
	public List<ProjectInfoDto> selectProjectNameList();
	
	/*위험관리대장 파일첨부*/
	@Insert("INSERT INTO PROJECT_RISK_FILE (RSK_CODE, RSK_FILE_NAME, RSK_FILE_URL) VALUES (#{rskCode}, #{rskFileName}, #{rskFileUrl})")
	public void insertProjectRiskFile(ProjectRiskFileDto rskFileDto);
	
	/*위험관리대장 파일 체크*/
	@Update("UPDATE PROJECT_RISK SET FILE_CHECK ='Y' WHERE RSK_CODE=#{rskCode}")
	public void updateProjectRiskFileCheck(int rskCode);
	
	/*위험관리대장 파일 불러오기*/
	@Select("SELECT * FROM PROJECT_RISK_FILE WHERE RSK_CODE=#{rskCode}")
	public List<ProjectRiskFileDto> selectProjectRiskFile(int rskCode);
	
	/*위험관리대장 파일 수정*/
	@Update("UPDATE PROJECT_RISK_FILE SET RSK_FILE_NAME=#{rskFileName}, RSK_FILE_URL=#{rskFileUrl}, WHERE RSK_FILE_NO=#{rskFileNo}")
	public void updateProjectRiskFile(ProjectRiskFileDto rskFileDto);
	
	/*위험관리대장 최근 코드 추출*/
	@Select("SELECT MAX(RSK_CODE) FROM PROJECT_RISK")
	public int selectMaxRskCode();
	
	/*프로젝트 샘플 데이터 생성 (300개)*/
	@Insert("INSERT INTO PROJECT_INFO (PRJ_NAME, PRJ_MOTHERCOMPANY, PRJ_DEPOSIT, PRJ_WORKING_EXPENSES, PRJ_START, PRJ_END, PRJ_PM) VALUES (#{prjName}, #{prjMothercompany}, #{prjDeposit}, #{prjWorkingExpenses}, #{prjStart}, #{prjEnd}, #{prjPm})")
	public void insertProjectSamples(ProjectInfoDto prj);
	
	/*프로젝트 참여인원 샘플 데이터 생성(프로젝트 300개에 2팀 나눠서)*/
	@Insert("INSERT INTO PROJECT_MEMBERS (MEM_ID, PRJ_CODE) VALUES (#{memId}, #{prjCode})")
	public void insertProjectMemberSamples(ProjectMembersDto mem);
	
	/*위험관리대장 샘플 데이터 생성 (300개)*/
	@Insert("INSERT INTO PROJECT_RISK (RSK_TITLE, RSK_CONTENT, RSK_REG, RSK_WRITER, MEM_NAME, RSK_SOLUTION, RSK_RESULT, FILE_CHECK, PRJ_CODE) VALUES "
			+ " (#{rskTitle}, #{rskContent}, #{rskReg}, #{rskWriter}, #{memName}, #{rskSolution}, #{rskResult}, #{fileCheck}, #{prjCode})")
	public void insertProjectRiskSamples(ProjectRiskDto rsk);
	
	/*프로젝트 WBS 샘플데이터  생성*/
	@Insert("INSERT INTO PROJECT_WBS (PRJ_CODE, PRJ_WORK_NAME, PRJ_GROUP, PRJ_STEP, PRJ_DEPTH, PRJ_MANAGER, PRJ_OUTPUT, PRJ_PLAN_START, PRJ_PLAN_END, PRJ_REAL_START, PRJ_REAL_END, PRJ_TOTAL_DAYS) VALUES "
			+ " (#{prjCode}, #{prjWorkName}, #{prjGroup}, #{prjStep}, #{prjDepth}, #{prjManager}, #{prjOutput}, #{prjPlanStart}, #{prjPlanEnd}, #{prjRealStart}, #{prjRealEnd}, #{prjTotalDays})")
	public void insertProjectWbs(ProjectWbsDto wbs);
	
}
