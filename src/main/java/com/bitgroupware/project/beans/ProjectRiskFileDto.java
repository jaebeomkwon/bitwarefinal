package com.bitgroupware.project.beans;



import org.apache.ibatis.annotations.Update;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProjectRiskFileDto {
	
	private String	rskFileNo;
	private int	rskCode;
	private String	rskFileName;
	private String	rskFileUrl;
	private MultipartFile file;
	
}
