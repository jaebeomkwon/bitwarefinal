package com.bitgroupware.community.service;

import java.util.List;

import com.bitgroupware.community.vo.MeetingroomReservationVo;
import com.bitgroupware.community.vo.MeetingroomVo;
import com.bitgroupware.member.vo.MemberVo;

public interface MeetingroomReservationService {

	List<MeetingroomVo> selectMeetingroomList();

	List<MeetingroomReservationVo> selectMeetingroomReservationList(MemberVo member);

	int checkDuplicates(int mrNo, String start, String end);
	
	MeetingroomVo selectMeetingroomByMrNo(int mrNo);
	
	void insertMeetingroomReservation(MeetingroomReservationVo meetingroomReservation);

	List<MeetingroomReservationVo> selectMeetingroomReservationListAjax();
	
	void deleteMeetingroomReservation(int mrResNo);
	
	void deleteCheck();

}
