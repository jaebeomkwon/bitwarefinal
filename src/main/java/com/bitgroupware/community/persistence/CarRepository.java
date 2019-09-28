package com.bitgroupware.community.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitgroupware.community.vo.CarVo;

public interface CarRepository extends JpaRepository<CarVo, Integer>{

}
