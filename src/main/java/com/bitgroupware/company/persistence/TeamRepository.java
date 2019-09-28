package com.bitgroupware.company.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitgroupware.company.vo.DepartmentVo;
import com.bitgroupware.company.vo.TeamVo;

public interface TeamRepository extends JpaRepository<TeamVo, String>{

	List<TeamVo> findByDepartment(DepartmentVo departmentVo);

}
