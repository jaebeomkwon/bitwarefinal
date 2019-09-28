package com.bitgroupware.approval.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitgroupware.approval.beans.ApprovalDocumentDto;
import com.bitgroupware.approval.persistence.ApprovalDocumentDao;

@Service
public class ApprovalDocServiceImpl implements ApprovalDocService {

	@Autowired
	private ApprovalDocumentDao apDao;

	static final Logger LOGGER = LoggerFactory.getLogger(ApprovalDocServiceImpl.class);

	// 모든 문서양식 불러오기
	@Override
	public List<ApprovalDocumentDto> selectApprovalDocList() {
		return apDao.selectApprovalDocList();
	}

	// 읽기
	@Override
	public ApprovalDocumentDto selectApprovalDoc(int apdocNo) {
		return apDao.selectApprovalDoc(apdocNo);
	}

	// 등록(insert+update)
	@Transactional
	@Override
	public void insertApprovalDoc(ApprovalDocumentDto dto) {
		if (dto.getApdocNo() == 0) {
			apDao.insertApprovalDoc(dto);
			LOGGER.error("insertApprovalDoc");
		} else {
			apDao.updateApprovalDoc(dto);
			LOGGER.error("updateApprovalDoc");
		}
	}

	// 수정
	@Override
	public void updateApprovalDoc(ApprovalDocumentDto dto) {
		// TODO Auto-generated method stub
		apDao.updateApprovalDoc(dto);
	}

	// 삭제
	@Override
	public void deleteApprovalDoc(ApprovalDocumentDto dto) {
		apDao.deleteApprovalDoc(dto);
	}
}
