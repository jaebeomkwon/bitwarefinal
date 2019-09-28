package com.bitgroupware.approval.service;

import java.util.List;

import com.bitgroupware.approval.beans.ApprovalDto;
import com.bitgroupware.approval.beans.ApprovalFileDto;
import com.bitgroupware.member.vo.MemberVo;
import com.bitgroupware.utils.Search;

public interface ApprovalService {
	
	// 결재 받을 문서 리스트
	List<ApprovalDto> selectApprovalListToBeByTotal(String memId,int begin,Search search);
	List<ApprovalDto> selectApprovalListToBe(String memId, String status,int begin,Search search);
	
	// 결재 할 문서 리스트
	List<ApprovalDto> selectApprovalListTo(String memId,int begin,Search search);
	List<ApprovalDto> selectApprovalListToFinish(String memId,int begin,Search search,String memName);
	
	// 읽기
	ApprovalDto selectApproval(String apNo);
	
	// 기안
	void insertApproval(ApprovalDto approval, MemberVo member);
	
	int selectRanksNo(String memId);
	
	void updateApprovalPath(ApprovalDto approval, MemberVo member);
	
	void updateApprovalCancel(ApprovalDto approval);
	
	void deleteApproval(ApprovalDto approval);
	
	void updateApproval(ApprovalDto approval);
	void updateApprovalFile(ApprovalFileDto approvalFile);
	
	void insertApprovalFile(ApprovalFileDto approvalFileDto);
	
	List<ApprovalFileDto> selectApprovalFile(String apNo);
	
	// 재범페이징
	int countApproval(String memId, Search search);
	
	int countApprovalToFinish(String memId, Search search);
	
	int countApprovalStatus(String memId, String status, Search search);
	
	int selectFirstRanksNo(String apNo);
	
	


	
}
