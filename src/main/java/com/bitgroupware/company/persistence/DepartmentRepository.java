package com.bitgroupware.company.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitgroupware.company.vo.DepartmentVo;

public interface DepartmentRepository extends JpaRepository<DepartmentVo, String>{

}