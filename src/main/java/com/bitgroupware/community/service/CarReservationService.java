package com.bitgroupware.community.service;

import java.util.List;

import com.bitgroupware.community.vo.CarReservationVo;
import com.bitgroupware.community.vo.CarVo;
import com.bitgroupware.member.vo.MemberVo;

public interface CarReservationService {

	List<CarVo> selectCarList();

	List<CarReservationVo> selectCarReservationList(MemberVo member);

	int checkDuplicates(int carNo, String start, String end);
	
	CarVo selectCarByCarNo(int carNo);
	
	void insertCarReservation(CarReservationVo carReservation);
	
	List<CarReservationVo> selectCarReservationListAjax();
	
	void deleteCarReservation(int carResNo);
	
	void deleteCheck();

}
