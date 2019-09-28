package com.bitgroupware.approval.service;

import java.util.List;

import com.bitgroupware.approval.beans.ApprovalDocumentDto;

public interface ApprovalDocService {
	// 모든 문서양식 불러오기
	List<ApprovalDocumentDto> selectApprovalDocList();
	
	// 읽기
	ApprovalDocumentDto selectApprovalDoc(int apdocNo);
	
	// 등록(insert+update)
	void insertApprovalDoc(ApprovalDocumentDto dto);
	
	// 수정
	void updateApprovalDoc(ApprovalDocumentDto dto);
	
	// 삭제
	void deleteApprovalDoc(ApprovalDocumentDto dto);
}
