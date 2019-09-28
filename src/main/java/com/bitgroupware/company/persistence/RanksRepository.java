package com.bitgroupware.company.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitgroupware.company.vo.RanksVo;

public interface RanksRepository extends JpaRepository<RanksVo, String>{
	
	List<RanksVo> findAllByOrderByRanksNoAsc();

	List<RanksVo> findByRanksNoGreaterThan(int i);

	List<RanksVo> findByRanksNoGreaterThanOrderByRanksNoDesc(int i);
}
