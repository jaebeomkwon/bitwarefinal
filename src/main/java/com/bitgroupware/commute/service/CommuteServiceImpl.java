package com.bitgroupware.commute.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitgroupware.commute.persistence.CommuteRepository;
import com.bitgroupware.commute.vo.CommuteVo;
import com.bitgroupware.member.vo.MemberVo;

@Service
public class CommuteServiceImpl implements CommuteService {

	@Autowired
	CommuteRepository commuteRepository;

	// 근태 목록
	@Override
	public List<CommuteVo> selectCommuteList(MemberVo memberVo, String startDate, String endDate) {
		return commuteRepository.findByMemberVoAndCommuteDateBetweenOrderByCommuteDateDesc(memberVo, startDate,endDate);
	}
	// 근태 목록
	@Override
	public List<CommuteVo> selectCommuteListByPaging(MemberVo memberVo, int begin, String startDate, String endDate) {
		return commuteRepository.selectCommuteListForPaging(memberVo.getMemId(), begin, startDate,endDate);
	}

	// 근태 구분 count
	@Override
	public List<String> selectStatusCount(MemberVo memberVo) {
		List<String> selectStatusCount = new ArrayList<String>();
		String[] commuteStatus = { "정상", "지각", "조퇴", "결근", "휴가" };
		for (int i = 0; i < commuteStatus.length; i++) {
			selectStatusCount.add(String.valueOf(commuteRepository.countByMemberVoAndCommuteStatus(memberVo, commuteStatus[i])));
		}
		return selectStatusCount;
	}

	// 출근시간 기록
	@Override
	public void insertOntime(CommuteVo commuteVo) {
		commuteRepository.save(commuteVo);
	}

	// 퇴근시간 기록
	@Override
	public void updateOfftime(CommuteVo commuteVo) {
		commuteRepository.save(commuteVo);
	}

	// DB서버 날짜
	@Override
	public String selectCurdate() {
		return commuteRepository.selectCurdate();
	}

	// DB서버 시간
	@Override
	public String selectCurtime() {
		return commuteRepository.selectCurtime();
	}

	// 오늘 근태 확인
	@Override
	public CommuteVo selectTodayCommute(MemberVo memberVo, String curdate) {
		return commuteRepository.findByMemberVoAndCommuteDate(memberVo, curdate);
	}

	@Override
	public int countCommute(String startDate, String endDate) {
		return commuteRepository.countCommute(startDate, endDate);
	}

}
