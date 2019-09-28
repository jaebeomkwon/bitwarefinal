package com.bitgroupware.community.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitgroupware.community.vo.MeetingroomVo;

public interface MeetingroomRepository extends JpaRepository<MeetingroomVo, Integer>{

}
