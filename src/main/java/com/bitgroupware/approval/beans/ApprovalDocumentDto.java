package com.bitgroupware.approval.beans;

import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
public class ApprovalDocumentDto {
	private int 	apdocNo;	// 번호
	private String	apdocName;	// 문서양식명
	private String	apdocForm;	// 문서양식내용
	private int 	finalSign;	// 최종 결재자
}
