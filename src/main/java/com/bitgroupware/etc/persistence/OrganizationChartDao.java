package com.bitgroupware.etc.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.bitgroupware.project.beans.ProjectInfoDto;

@Mapper
public interface OrganizationChartDao {

	@Select("select * from project_info where prj_code in (select prj_code from project_members where mem_id=#{memId}) and prj_completion =0")
	List<ProjectInfoDto> selectProjectListByMemIdWithZero(String memId);

	@Select("select * from project_info where prj_code in (select prj_code from project_members where mem_id=#{memId}) and prj_completion =1")
	List<ProjectInfoDto> selectProjectListByMemIdWithOne(String memId);

}
