package com.bitgroupware.approval.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitgroupware.approval.beans.ApprovalDto;
import com.bitgroupware.approval.beans.ApprovalFileDto;
import com.bitgroupware.approval.persistence.ApprovalDao;
import com.bitgroupware.community.vo.NoticeVo;
import com.bitgroupware.member.vo.MemberVo;
import com.bitgroupware.utils.Search;

@Service
public class ApprovalServiceImpl implements ApprovalService {

	@Autowired
	private ApprovalDao apDao;

	static final Logger LOGGER = LoggerFactory.getLogger(ApprovalServiceImpl.class);

	// 결재 받을 문서 리스트
	public List<ApprovalDto> selectApprovalListToBeByTotal(String memId,int begin,Search search) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("apTitle");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");
		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		List<ApprovalDto> approvalList = null;
		switch (searchCondition) {
		case "apTitle":
			approvalList = apDao.selectApprovalListToBeByTotalApTilte(memId,begin, searchKeyword);
			break;
		case "apContent":
			approvalList = apDao.selectApprovalListToBeByTotalApContent(memId,begin, searchKeyword);
			break;
		}
		return approvalList;
	}

	public List<ApprovalDto> selectApprovalListToBe(String memId, String status,int begin,Search search) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("apTitle");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");
		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		List<ApprovalDto> approvalList = null;
		switch (searchCondition) {
		case "apTitle":
			approvalList = apDao.selectApprovalListToBeApTilte(memId, status,begin, searchKeyword);
			break;
		case "apContent":
			approvalList = apDao.selectApprovalListToBeApContent(memId, status,begin, searchKeyword);
			break;
		}
		return approvalList;
	}

	// 결재 할 문서 리스트
	public List<ApprovalDto> selectApprovalListTo(String memId,int begin,Search search) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("apTitle");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");
		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		List<ApprovalDto> approvalList = null;
		switch (searchCondition) {
		case "apTitle":
			approvalList = apDao.selectApprovalListToApTilte(memId,begin, searchKeyword);
			break;
		case "apContent":
			approvalList = apDao.selectApprovalListToApContent(memId,begin, searchKeyword);
			break;
		}
		return approvalList;
	}
	
	public List<ApprovalDto> selectApprovalListToFinish(String memId,int begin,Search search,String memName) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("apTitle");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");
		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		List<ApprovalDto> approvalList = null;
		switch (searchCondition) {
		case "apTitle":
			approvalList = apDao.selectApprovalListToFinishApTilte(memId,memName,begin, searchKeyword);
			break;
		case "apContent":
			approvalList = apDao.selectApprovalListToFinishApContent(memId,memName,begin, searchKeyword);
			break;
		}
		return approvalList;
	}

	// 읽기
	@Override
	public ApprovalDto selectApproval(String apNo) {
		return apDao.selectApproval(apNo);
	}
	
	@Override
	public List<ApprovalFileDto> selectApprovalFile(String apNo) {
		return apDao.selectApprovalFile(apNo);
	}

	// 기안
	@Override
	public void insertApproval(ApprovalDto approval, MemberVo member) {
		int ranks = member.getRanks().getRanksNo();

		switch (ranks) {
		case 1:
			String teamName = member.getTeam().getTeamName();
			String teamLeaderId = apDao.selectTeamLeader(teamName);
			approval.setApSignpath(teamLeaderId);
			break;
		case 2:
			String deptName = member.getDepartment().getDeptName();
			String headerId = apDao.selectHeader(deptName);
			approval.setApSignpath(headerId);
			break;
		case 3:
			String directorId = apDao.selectDirector(ranks + 1);
			approval.setApSignpath(directorId);
			break;
		case 4:
			String bossId = apDao.selectBoss(ranks + 1);
			approval.setApSignpath(bossId);
			break;
		}
		apDao.insertApproval(approval);
	}

	public int selectRanksNo(String memId) {
		return apDao.selectRanksNo(memId);
	}
	
	public int selectFirstRanksNo(String apNo) {
		return apDao.seletFirstRanksNo(apNo);
	}


	public void updateApprovalPath(ApprovalDto approval, MemberVo member) {
		
		int ranks = member.getRanks().getRanksNo();

		switch (ranks) {
		case 1:
			String teamName = member.getTeam().getTeamName();
			String teamLeaderId = apDao.selectTeamLeader(teamName);
			approval.setApSignpath(teamLeaderId);
			break;
		case 2:
			String deptName = member.getDepartment().getDeptName();
			String headerId = apDao.selectHeader(deptName);
			approval.setApSignpath(headerId);
			break;
		case 3:
			String directorId = apDao.selectDirector(ranks + 1);
			approval.setApSignpath(directorId);
			break;
		case 4:
			String bossId = apDao.selectBoss(ranks + 1);
			approval.setApSignpath(bossId);
			break;
		}
		apDao.updateApprovalPath(approval);
	}

	public void updateApprovalCancel(ApprovalDto approval) {
		apDao.updateApprovalCancel(approval);
	}

	@Override
	public void deleteApproval(ApprovalDto approval) {
		apDao.deleteApproval(approval);
	}
	
	@Override
	public void updateApproval(ApprovalDto approval) {
		System.out.println("update svc");
		apDao.updateApproval(approval);
	}
	
	@Override
	public void updateApprovalFile(ApprovalFileDto approvalFile) {
		apDao.updateApprovalFile(approvalFile);
	}

	@Override
	public void insertApprovalFile(ApprovalFileDto approvalFileDto) {
		apDao.insertApprovalFile(approvalFileDto);
	}
	
	//재범페이징

	@Override
	public int countApproval(String memId, Search search) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("apTitle");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");

		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		int count = 0;
		switch (searchCondition) {
		case "apTitle":
			count = apDao.countByApTitle(memId,searchKeyword);
			break;
		case "apContent":
			count = apDao.countByApContent(memId,searchKeyword);
			break;
		}
		return count;
	}

	@Override
	public int countApprovalStatus(String memId, String status, Search search) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("apTitle");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");

		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		int count = 0;
		switch (searchCondition) {
		case "apTitle":
			count = apDao.countByApTitleStatus(memId,status,searchKeyword);
			break;
		case "apContent":
			count = apDao.countByApContentStatus(memId,status,searchKeyword);
			break;
		}
		return count;
	}

	@Override
	public int countApprovalToFinish(String memId, Search search) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("apTitle");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");

		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		int count = 0;
		switch (searchCondition) {
		case "apTitle":
			count = apDao.countByToApTitle(memId,searchKeyword);
			break;
		case "apContent":
			count = apDao.countByToApContent(memId,searchKeyword);
			break;
		}
		return count;
	}

}
