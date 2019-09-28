package com.bitgroupware.commute.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bitgroupware.commute.vo.CommuteVo;
import com.bitgroupware.member.vo.MemberVo;

public interface CommuteRepository extends JpaRepository<CommuteVo, Integer>{
	
	List<CommuteVo> findByMemberVoAndCommuteDateBetweenOrderByCommuteDateDesc(MemberVo member, String startDate, String endDate);

	long countByMemberVoAndCommuteStatus(MemberVo memberVo, String string);
	
	@Query(value = "select curdate()", nativeQuery = true)
	String selectCurdate();

	@Query(value = "select curtime()", nativeQuery = true)
	String selectCurtime();

	CommuteVo findByMemberVoAndCommuteDate(MemberVo memberVo, String curdate);

	@Query(value="select count(*) from commute where commute_date >= ?1 and commute_date <= ?2", nativeQuery = true)
	int countCommute(String startDate, String endDate);

	@Query(value="select r1.* from (select * from commute where mem_id = ?1 and commute_date >= ?3 and commute_date <= ?4 order by commute_date desc) r1 limit 10 offset ?2", nativeQuery=true)
	List<CommuteVo> selectCommuteListForPaging(String memId, int begin, String startDate, String endDate);
	
}
