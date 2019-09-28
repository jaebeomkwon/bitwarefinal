package com.bitgroupware.member.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bitgroupware.company.vo.RanksVo;
import com.bitgroupware.company.vo.TeamVo;
import com.bitgroupware.member.vo.MemberVo;

public interface MemberRepository extends JpaRepository<MemberVo, String>{
	List<MemberVo> findAllByOrderByMemNameAsc();
	
	@Query(value = "select curdate()", nativeQuery = true)
	String selectCurdate();
	
	@Query(value = "select count(*) from member", nativeQuery = true)
	String selectCountMember();

///////////////////////////조직도 관련///////////////////////////////////////
	
	List<MemberVo> findByTeam(TeamVo team);

	@Query(value = "select * from member where ranks = '부장' and dept_name = ?", nativeQuery = true)
	MemberVo selectHeaderBydeptName(String deptName);

	List<MemberVo> findByRanks(RanksVo ranks);

	
///////////////////////////////////////////////////////////////
	
	@Query(value = "select count(*) from member where mem_name like ?", nativeQuery = true)
	int countByMemName(String searchKeyword);

	@Query(value = "select count(*) from member where mem_id like ?", nativeQuery = true)
	int countByMemId(String searchKeyword);

	@Query(value = "select r1.* from (select * from member where mem_name like ?2 order by mem_name asc) r1 limit 10 offset ?1", nativeQuery = true)
	List<MemberVo> findAllByPagingAndMemName(int begin, String searchKeyword);
	
	@Query(value = "select r1.* from (select * from member where mem_id like ?2 order by mem_name asc) r1 limit 10 offset ?1", nativeQuery = true)
	List<MemberVo> findAllByPagingAndMemId(int begin, String searchKeyword);
}
