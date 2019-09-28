package com.bitgroupware.community.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bitgroupware.community.vo.MeetingroomReservationVo;
import com.bitgroupware.member.vo.MemberVo;

public interface MeetingroomReservationRepository extends JpaRepository<MeetingroomReservationVo, Integer>{

	List<MeetingroomReservationVo> findByMember(MemberVo member);

	@Query(value = "select count(*) from meetingroom_reservation where mr_no = ?1 and mr_res_start < ?3 and mr_res_end > ?2", nativeQuery = true)
	int checkDuplicates(int mrNo, String start, String end);
}
