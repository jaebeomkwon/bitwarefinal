package com.bitgroupware.commute.service;

import java.util.List;

import com.bitgroupware.commute.vo.CommuteVo;
import com.bitgroupware.member.vo.MemberVo;

public interface CommuteService {

	void insertOntime(CommuteVo commuteVo);
	
	void updateOfftime(CommuteVo commuteVo);
	
	List<CommuteVo> selectCommuteList(MemberVo memberVo, String startDate, String endDate);
	
	List<CommuteVo> selectCommuteListByPaging(MemberVo memberVo, int begin, String startDate, String endDate);

	List<String> selectStatusCount(MemberVo memberVo);
	
	String selectCurdate();
	
	String selectCurtime();

	CommuteVo selectTodayCommute(MemberVo memberVo, String curdate);

	int countCommute(String startDate, String endDate);

}
