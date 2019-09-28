package com.bitgroupware.approval.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.jpa.repository.Query;

import com.bitgroupware.approval.beans.ApprovalDto;
import com.bitgroupware.approval.beans.ApprovalFileDto;
import com.bitgroupware.community.vo.NoticeVo;

@Mapper
public interface ApprovalDao {
	// 결재 받을 문서 리스트
	@Select("select r1.* from (select * from approval where mem_id = #{memId} and ap_deleteflag='N' and ap_title like #{searchKeyword} order by ap_no desc) r1 limit 10 offset #{begin}")
	List<ApprovalDto> selectApprovalListToBeByTotalApTilte(String memId,int begin,String searchKeyword);
	
	@Select("select r1.* from (select * from approval where mem_id = #{memId} and ap_deleteflag='N' and ap_content like #{searchKeyword} order by ap_no desc) r1 limit 10 offset #{begin}")
	List<ApprovalDto> selectApprovalListToBeByTotalApContent(String memId,int begin,String searchKeyword);
	
	@Select("select r1.* from (select * from approval where mem_id = #{memId} and ap_deleteflag='N' and ap_docstatus=#{status} and ap_title like #{searchKeyword} order by ap_no desc) r1 limit 10 offset #{begin}")
	List<ApprovalDto> selectApprovalListToBeApTilte(String memId,String status,int begin,String searchKeyword);
	
	@Select("select r1.* from (select * from approval where mem_id = #{memId} and ap_deleteflag='N' and ap_docstatus=#{status} and ap_content like #{searchKeyword} order by ap_no desc) r1 limit 10 offset #{begin}")
	List<ApprovalDto> selectApprovalListToBeApContent(String memId,String status,int begin,String searchKeyword);

	@Select("select * from approval where mem_id = #{memId} and ap_deleteflag='N' and ap_docstatus = #{status}")
	List<ApprovalDto> selectApprovalListToBe(String memId, String status,int begin,String searchKeyword);
	
	// 페이징
	@Select("select count(*) from approval where ap_deleteflag = 'N' and ap_title like #{searchKeyword} and mem_id = #{memId}")
	int countByApTitle(String memId, String searchKeyword);

	@Select("select count(*) from approval where ap_deleteflag = 'N' and ap_content like #{searchKeyword} and mem_id = #{memId}")
	int countByApContent(String memId, String searchKeyword);

	@Select("select count(*) from approval where ap_deleteflag = 'N' and mem_id = #{memId} and ap_docstatus=#{status} and ap_title like #{searchKeyword} ")
	int countByApTitleStatus(String memId, String status, String searchKeyword);

	@Select("select count(*) from approval where ap_deleteflag = 'N' and mem_id = #{memId} and ap_docstatus = #{status} and ap_content like #{searchKeyword} ")
	int countByApContentStatus(String memId, String status, String searchKeyword);

	
	
	
	// 결재 할 문서 리스트
	@Select("select * from approval where ap_signpath = #{memId} and ap_docstatus in (1,2) and ap_deleteflag = 'N'")
	List<ApprovalDto> selectApprovalListTo(String memId,int begin,String searchKeyword);
	
	@Select("select * from approval where ap_signpath = #{memId} and ap_docstatus in (1,2) and ap_deleteflag = 'N' limit 5")
	List<ApprovalDto> selectMainApprovalListTo(String memId);

	@Select("select * from approval where ap_no = #{apNo} and ap_deleteflag = 'N'")
	ApprovalDto selectApproval(String apNo);
	
	@Select("select r1.* from (select * from approval where ap_signpath = #{memId} and ap_docstatus in (1,2) and ap_deleteflag = 'N' and ap_title like #{searchKeyword} order by ap_no desc) r1 limit 10 offset #{begin}")
	List<ApprovalDto> selectApprovalListToApTilte(String memId, int begin, String searchKeyword);

	@Select("select r1.* from (select * from approval where ap_signpath = #{memId} and ap_docstatus in (1,2) and ap_deleteflag = 'N' and ap_content like #{searchKeyword} order by ap_no desc) r1 limit 10 offset #{begin}")
	List<ApprovalDto> selectApprovalListToApContent(String memId, int begin, String searchKeyword);
	
//	@Select("select r1.* from (select DISTINCT ap_no,ap_title,ap_docstatus,approval.mem_id,ap_insertdate,approval.mem_name from approval join member on (approval.ap_sign_name2 = member.mem_name or approval.ap_sign_name3 = member.mem_name or approval.ap_sign_name4 = member.mem_name or approval.ap_sign_name5 = member.mem_name) where not approval.mem_id in (#{memId}) and ap_docstatus in (2,3,4) and ap_deleteflag = 'N' and ap_content like #{searchKeyword} order by ap_no desc) r1 limit 10 offset #{begin}")
//	List<ApprovalDto> selectApprovalListToFinishApTilte(String memId, int begin, String searchKeyword);
//
//	@Select("select r1.* from (select DISTINCT ap_no,ap_title,ap_docstatus,approval.mem_id,ap_insertdate,approval.mem_name from approval join member on (approval.ap_sign_name2 = member.mem_name or approval.ap_sign_name3 = member.mem_name or approval.ap_sign_name4 = member.mem_name or approval.ap_sign_name5 = member.mem_name) where not approval.mem_id in (#{memId}) and ap_docstatus in (2,3,4) and ap_deleteflag = 'N' and ap_content like #{searchKeyword} order by ap_no desc) r1 limit 10 offset #{begin}")
//	List<ApprovalDto> selectApprovalListToFinishApContent(String memId, int begin, String searchKeyword);
	
	@Select("select r1.* from (select DISTINCT ap_no,ap_title,ap_docstatus,approval.mem_id,ap_insertdate,mem_name from approval where( ap_sign_name1=#{memName} or ap_sign_name2=#{memName} or ap_sign_name3=#{memName} or ap_sign_name4=#{memName} or ap_sign_name5=#{memName} ) and not mem_id = #{memId} and ap_deleteflag = 'N' and ap_content like #{searchKeyword} order by ap_no desc) r1 limit 10 offset #{begin}")
	List<ApprovalDto> selectApprovalListToFinishApTilte(String memId, String memName,int begin, String searchKeyword);

	@Select("select r1.* from (select DISTINCT ap_no,ap_title,ap_docstatus,approval.mem_id,ap_insertdate,mem_name from approval where( ap_sign_name1=#{memName} or ap_sign_name2=#{memName} or ap_sign_name3=#{memName} or ap_sign_name4=#{memName} or ap_sign_name5=#{memName} ) and not mem_id = #{memId} and ap_deleteflag = 'N' and ap_content like #{searchKeyword} order by ap_no desc) r1 limit 10 offset #{begin}")
	List<ApprovalDto> selectApprovalListToFinishApContent(String memId, String memName,int begin, String searchKeyword);
	

	// 기안
	@Insert("insert into approval(ap_title,ap_content,ap_deleteflag,ap_docstatus,apdoc_no,ap_insertdate,mem_id,ap_signpath,ap_sign_url1,ap_sign_url2,ap_sign_url3,ap_sign_url4,ap_sign_url5,ap_sign_name1,ap_sign_name2,ap_sign_name3,ap_sign_name4,ap_sign_name5,final_sign,mem_name) values(#{apTitle},#{apContent},#{apDeleteflag},#{apDocstatus},#{apdocNo},now(),#{memId},#{apSignpath},#{apSignUrl1},#{apSignUrl2},#{apSignUrl3},#{apSignUrl4},#{apSignUrl5},#{apSignName1},#{apSignName2},#{apSignName3},#{apSignName4},#{apSignName5},#{finalSign},#{memName})")
	void insertApproval(ApprovalDto approval);

	@Select("select mem_id from member where team_name=#{teamName} and ranks='팀장'")
	String selectTeamLeader(String teamName);

	@Select("select mem_id from member where dept_name=#{deptName} and ranks='부장'")
	String selectHeader(String deptName);

	@Select("select * from member join ranks on member.ranks = ranks.ranks where ranks_no = #{ranksNo}")
	String selectDirector(int ranksNo);

	@Select("select * from member join ranks on member.ranks = ranks.ranks where ranks_no = #{ranksNo}")
	String selectBoss(int ranksNo);

	@Select ("select ranks.ranks_no from member join ranks on member.ranks = ranks.ranks where member.mem_id = #{memId}")
	int selectRanksNo(String memId);
	
	@Select("select ranks.ranks_no from member join ranks on member.ranks = ranks.ranks where member.mem_id = (select mem_id from approval where ap_no=#{apNo});")
	int seletFirstRanksNo(String apNo);

	@Update("update approval set ap_docstatus = #{apDocstatus}, ap_signpath = #{apSignpath}, ap_sign_url1 = #{apSignUrl1}, ap_sign_url2 = #{apSignUrl2}, ap_sign_url3 = #{apSignUrl3}, ap_sign_url4 = #{apSignUrl4}, ap_sign_url5 = #{apSignUrl5}, ap_sign_name1 = #{apSignName1}, ap_sign_name2 = #{apSignName2}, ap_sign_name3 = #{apSignName3}, ap_sign_name4 = #{apSignName4}, ap_sign_name5 = #{apSignName5} where ap_no = #{apNo}")
	void updateApprovalPath(ApprovalDto approval);

	@Update("update approval set ap_docstatus = #{apDocstatus}, ap_signpath = #{apSignpath}, ap_comment = #{apComment}, ap_sign_url1 = #{apSignUrl1}, ap_sign_url2 = #{apSignUrl2}, ap_sign_url3 = #{apSignUrl3}, ap_sign_url4 = #{apSignUrl4}, ap_sign_url5 = #{apSignUrl5}, ap_sign_name1 = #{apSignName1}, ap_sign_name2 = #{apSignName2}, ap_sign_name3 = #{apSignName3}, ap_sign_name4 = #{apSignName4}, ap_sign_name5 = #{apSignName5} where ap_no = #{apNo}")
	void updateApprovalCancel(ApprovalDto approval);

	@Update("update approval set ap_deleteflag ='Y' where ap_no = #{apNo}")
	void deleteApproval(ApprovalDto approval);
	
	@Update("update approval set ap_title = #{apTitle} ,ap_content = #{apContent} where ap_no = #{apNo}")
	void updateApproval(ApprovalDto approval);
	
	@Update("update approval_file set ap_filename=#{apFilename}, ap_fileurl=#{apFileurl} where ap_fileno=#{apFileno}")
	void updateApprovalFile(ApprovalFileDto approvalFile);
	
	@Select("SELECT MAX(AP_NO) FROM APPROVAL")
	String selectMaxApNo();
	
	@Insert("INSERT INTO APPROVAL_FILE (AP_NO,AP_FILENAME,AP_FILEURL) VALUES (#{apNo}, #{apFilename}, #{apFileurl})")
	void insertApprovalFile(ApprovalFileDto approvalFileDto);
	
	// 
	@Select("select * from approval_file where ap_no = #{apNo}")
	List<ApprovalFileDto> selectApprovalFile(String apNo);
	
	// 파일체크
	@Select("update approval set file_check ='Y' where ap_no=#{apNo}")
	void updateApprovalFileCheck(String apNo);

	
	@Select("select count( DISTINCT  ap_no,ap_title,ap_docstatus,approval.mem_id,ap_insertdate) from approval join member on (approval.ap_sign_name2 = member.mem_name or approval.ap_sign_name3 = member.mem_name or approval.ap_sign_name4 = member.mem_name or approval.ap_sign_name5 = member.mem_name) where not approval.mem_id in (#{memId}) and ap_docstatus in (2,3,4) and ap_deleteflag = 'N' and ap_content like #{searchKeyword}")
	int countByToApTitle(String memId, String searchKeyword);

	@Select("select count( DISTINCT  ap_no,ap_title,ap_docstatus,approval.mem_id,ap_insertdate) from approval join member on (approval.ap_sign_name2 = member.mem_name or approval.ap_sign_name3 = member.mem_name or approval.ap_sign_name4 = member.mem_name or approval.ap_sign_name5 = member.mem_name) where not approval.mem_id in (#{memId}) and ap_docstatus in (2,3,4) and ap_deleteflag = 'N' and ap_content like #{searchKeyword}")
	int countByToApContent(String memId, String searchKeyword);

}
