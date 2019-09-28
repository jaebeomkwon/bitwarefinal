package com.bitgroupware.community.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitgroupware.community.persistence.CarRepository;
import com.bitgroupware.community.persistence.CarReservationRepository;
import com.bitgroupware.community.vo.CarReservationVo;
import com.bitgroupware.community.vo.CarVo;
import com.bitgroupware.member.vo.MemberVo;

@Service
public class CarReservationServiceImpl implements CarReservationService{

	@Autowired
	private CarRepository carRepo;
	@Autowired
	private CarReservationRepository carReservationRepo;
	
	public List<CarVo> selectCarList() {
		return carRepo.findAll();
	}

	public List<CarReservationVo> selectCarReservationList(MemberVo member) {
		return carReservationRepo.findByMember(member);
	}
	
	public int checkDuplicates(int carNo, String start, String end) {
		return carReservationRepo.checkDuplicates(carNo, start, end);
	}
	
	public CarVo selectCarByCarNo(int carNo) {
		return carRepo.findById(carNo).get();
	}
	
	public void insertCarReservation(CarReservationVo carReservation) {
		carReservationRepo.save(carReservation);
	}
	
	public List<CarReservationVo> selectCarReservationListAjax() {
		return carReservationRepo.findAll();
	}
	
	public void deleteCarReservation(int carResNo) {
		carReservationRepo.deleteById(carResNo);
	}
	
	public void deleteCheck() {
		List<CarReservationVo> carReservationList = carReservationRepo.findAll();
		for(CarReservationVo carReservation:carReservationList) {
			try {
				String end = carReservation.getCarResEnd()+":00";
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date endDate = format.parse(end);
				Date date = new Date();
				if(date.compareTo(endDate)==1) {
					carReservationRepo.delete(carReservation);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

}
