package com.bitgroupware.community.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitgroupware.community.service.MeetingroomReservationService;
import com.bitgroupware.community.vo.MeetingroomReservationVo;
import com.bitgroupware.community.vo.MeetingroomVo;
import com.bitgroupware.member.vo.MemberVo;
import com.bitgroupware.security.config.SecurityUser;

@Controller
@RequestMapping("/user")
public class MeetingroomController {

	@Autowired
	private MeetingroomReservationService meetingroomReservationService;
	
	@RequestMapping("/selectMeetingroomReservationList")
	public String selectMeetingroomReservationList(Model model, String msg, @AuthenticationPrincipal SecurityUser principal) {
		meetingroomReservationService.deleteCheck();
		List<MeetingroomVo> meetingroomList = meetingroomReservationService.selectMeetingroomList();
		MemberVo member = principal.getMember();
		List<MeetingroomReservationVo> meetingroomReservationList = meetingroomReservationService.selectMeetingroomReservationList(member);
		model.addAttribute("meetingroomList", meetingroomList);
		model.addAttribute("meetingroomReservationList", meetingroomReservationList);
		model.addAttribute("msg",msg);
		return "community/meetingroomReservationList";
	}
	
	@RequestMapping("/insertMeetingroomReservation")
	public String insertMeetingroomReservation(int mrNo, MeetingroomReservationVo meetingroomReservation, @AuthenticationPrincipal SecurityUser principal) {
		try {
			String start = meetingroomReservation.getMrResStart()+":00";
			String end = meetingroomReservation.getMrResEnd()+":00";
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = format.parse(start);
			Date endDate = format.parse(end);
			Date date = new Date();
			int check = meetingroomReservationService.checkDuplicates(mrNo, start, end); 
			if(date.compareTo(startDate)==1||startDate.compareTo(endDate)==1||check==1) {
				String msg = "Invalid date entered. Please re-enter.";
				return "redirect:/user/selectMeetingroomReservationList?msg="+msg;
			}
		} catch (ParseException e) {
			System.out.println("날짜 선택에서 오류 났어요.");
			e.printStackTrace();
		}
		MeetingroomVo meetingroom = meetingroomReservationService.selectMeetingroomByMrNo(mrNo);
		meetingroomReservation.setMeetingroom(meetingroom);
		meetingroomReservation.setMember(principal.getMember());
		meetingroomReservationService.insertMeetingroomReservation(meetingroomReservation);
		return "redirect:/user/selectMeetingroomReservationList";
	}

	@RequestMapping("/selectMeetingroomReservationListAjax")
	@ResponseBody
	public List<MeetingroomReservationVo> selectMeetingroomReservationListAjax() {
		return meetingroomReservationService.selectMeetingroomReservationListAjax();
	}
	
	@RequestMapping("/deleteMeetingroomReservation")
	public String deleteMeetingroomReservation(int mrResNo) {
		meetingroomReservationService.deleteMeetingroomReservation(mrResNo);
		return "redirect:/user/selectMeetingroomReservationList";
	}
}
