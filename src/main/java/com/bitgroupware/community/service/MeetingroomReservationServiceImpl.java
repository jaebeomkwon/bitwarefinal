package com.bitgroupware.community.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitgroupware.community.persistence.MeetingroomRepository;
import com.bitgroupware.community.persistence.MeetingroomReservationRepository;
import com.bitgroupware.community.vo.MeetingroomReservationVo;
import com.bitgroupware.community.vo.MeetingroomVo;
import com.bitgroupware.member.vo.MemberVo;

@Service
public class MeetingroomReservationServiceImpl implements MeetingroomReservationService{

	@Autowired
	private MeetingroomRepository meetingroomRepo;
	@Autowired
	private MeetingroomReservationRepository meetingroomReservationRepo;
	
	public List<MeetingroomVo> selectMeetingroomList() {
		return meetingroomRepo.findAll();
	}

	public List<MeetingroomReservationVo> selectMeetingroomReservationList(MemberVo member) {
		return meetingroomReservationRepo.findByMember(member);
	}

	public int checkDuplicates(int mrNo, String start, String end) {
		return meetingroomReservationRepo.checkDuplicates(mrNo, start, end);
	}
	
	public MeetingroomVo selectMeetingroomByMrNo(int mrNo) {
		return meetingroomRepo.findById(mrNo).get();
	}
	
	public void insertMeetingroomReservation(MeetingroomReservationVo meetingroomReservation) {
		meetingroomReservationRepo.save(meetingroomReservation);
	}
	
	public List<MeetingroomReservationVo> selectMeetingroomReservationListAjax() {
		return meetingroomReservationRepo.findAll();
	}
	
	public void deleteMeetingroomReservation(int mrResNo) {
		meetingroomReservationRepo.deleteById(mrResNo);
	}
	
	public void deleteCheck() {
		List<MeetingroomReservationVo> meetingroomReservationList = meetingroomReservationRepo.findAll();
		for(MeetingroomReservationVo meetingroomReservation:meetingroomReservationList) {
			try {
				String end = meetingroomReservation.getMrResEnd()+":00";
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date endDate = format.parse(end);
				Date date = new Date();
				if(date.compareTo(endDate)==1) {
					meetingroomReservationRepo.delete(meetingroomReservation);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

}
