package com.bitgroupware.approval.beans;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApprovalDto {
	private int apNo;			// 문서번호
	private String apTitle;			// 제목
	private String apContent;		// 문서내용
	private String apDeleteflag;	// 삭제여부
	private String apDocstatus;		// 문서상태
	private int apdocNo;			// 문서양식번호
	private Date apInsertdate;	// 작성일자
	private String memId;			// 사용자번호
	private String apSignpath;		// 다음결제자
    private String apSignUrl1;
    private String apSignUrl2;
    private String apSignUrl3;
    private String apSignUrl4;
    private String apSignUrl5;
    private String apSignName1;
    private String apSignName2;
    private String apSignName3;
    private String apSignName4;
    private String apSignName5;
	private int finalSign;
	private String apComment;
	private String fileCheck; // 파일체크
	private String memName;
}
