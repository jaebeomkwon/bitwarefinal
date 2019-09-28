package com.bitgroupware.project.beans;


import java.util.Date;

import lombok.Data;

@Data
public class ProjectRiskDto {
	
	private int rskCode;
	private String rskTitle;
	private String rskContent;
	private Date rskReg;
	private String rskWriter;
	private String memName;
	private String rskSolution;
	private String rskResult;
	private int prjCode;
	private String fileCheck;
	
}
