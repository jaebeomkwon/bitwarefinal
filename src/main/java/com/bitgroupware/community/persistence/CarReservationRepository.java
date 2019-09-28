package com.bitgroupware.community.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bitgroupware.community.vo.CarReservationVo;
import com.bitgroupware.member.vo.MemberVo;

public interface CarReservationRepository extends JpaRepository<CarReservationVo, Integer>{

	List<CarReservationVo> findByMember(MemberVo member);

	@Query(value = "select count(*) from car_reservation where car_no = ?1 and car_res_start < ?3 and car_res_end > ?2", nativeQuery = true)
	int checkDuplicates(int carNo, String start, String end);
}
