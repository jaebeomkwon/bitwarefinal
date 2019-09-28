package com.bitgroupware.utils;

import com.bitgroupware.project.beans.ProjectInfoDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MainViewProjectList {
	public ProjectInfoDto projectInfo;
	
	public int planComplete;
	public int planIng;
	public int planUnset;
	
	public int realComplete;
	public int realIng;
	public int realUnset;
}
