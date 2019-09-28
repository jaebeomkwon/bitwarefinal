package com.bitgroupware.approval.beans;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApprovalFileDto {
	private String	apFileno;		// 파일번호
	private String	apNo;		// 문서양식번호
	
	private String	apFilename;		// 파일명
	private String	apFileurl;		// 첨부파일URL
	private String	apFileType;		// 파일 타입
	
	private MultipartFile file;	// 업로드 파일들
}
