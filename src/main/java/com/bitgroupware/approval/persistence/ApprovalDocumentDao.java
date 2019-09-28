package com.bitgroupware.approval.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bitgroupware.approval.beans.ApprovalDocumentDto;

@Mapper
public interface ApprovalDocumentDao {
	// 모든 문서양식 불러오기
	@Select("SELECT * FROM APPROVAL_DOCUMENT")
	List<ApprovalDocumentDto> selectApprovalDocList();

	// 읽기
	@Select("SELECT * FROM APPROVAL_DOCUMENT WHERE APDOC_NO = #{apdocNo}")
	ApprovalDocumentDto selectApprovalDoc(int apdocNo);

	// 등록
	@Insert("INSERT INTO APPROVAL_DOCUMENT (APDOC_NAME,APDOC_FORM,FiNAL_SIGN) VALUES (#{apdocName}, #{apdocForm}, #{finalSign})")
	void insertApprovalDoc(ApprovalDocumentDto dto);
	
	// 수정
	@Update("UPDATE APPROVAL_DOCUMENT SET APDOC_NAME=#{apdocName}, APDOC_FORM=#{apdocForm}, FiNAL_SIGN=#{finalSign} WHERE APDOC_NO=#{apdocNo}")
	void updateApprovalDoc(ApprovalDocumentDto dto);
	
	// 삭제
	@Delete("DELETE from APPROVAL_DOCUMENT WHERE APDOC_NO = #{apdocNo}")
	void deleteApprovalDoc(ApprovalDocumentDto dto);
}
