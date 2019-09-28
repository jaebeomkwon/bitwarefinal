package com.bitgroupware.community.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitgroupware.community.persistence.AnonymousBoardRepository;
import com.bitgroupware.community.vo.AnonymousBoardVo;
import com.bitgroupware.community.vo.DocCenterVo;
import com.bitgroupware.utils.Search;

@Service
public class AnonymousBoardServiceImpl implements AnonymousBoardService{

	@Autowired
	private AnonymousBoardRepository anonymousBoardRepo;
	
	public int countAnonymousBoard(Search search) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("btitle");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");
		
		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		int count = 0;
		switch (searchCondition) {
		case "btitle":
			count = anonymousBoardRepo.countByBtitle(searchKeyword);
			break;
		case "bcontent":
			count = anonymousBoardRepo.countByBcontent(searchKeyword);
			break;
		}
		return count;
	}
	
	public List<AnonymousBoardVo> selectAnonymousBoardList(int begin, Search search) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("btitle");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");

		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		List<AnonymousBoardVo> anonymousBoardList = null;
		switch (searchCondition) {
		case "btitle":
			anonymousBoardList = anonymousBoardRepo.findAllByPagingAndBtitle(begin, searchKeyword);
			break;
		case "bcontent":
			anonymousBoardList = anonymousBoardRepo.findAllByPagingAndBcontent(begin, searchKeyword);
			break;
		}
		
		if(anonymousBoardList.size()>0) {
			for(int i=0; i<anonymousBoardList.size();i++) {
				int count = anonymousBoardList.get(i).getBindent();
				if(count>0) {
					for(int j=1;j<=count;j++) {
						anonymousBoardList.get(i).getBindentcnt().add(j);
					}
				}
			}
		}else {
			anonymousBoardRepo.initAutoIncrement();
		}
		return anonymousBoardList;
	}
	
	public void insertAnonymousBoard(AnonymousBoardVo anonymousBoard) {
		int maxBno = anonymousBoardRepo.selectMaxBno();
		anonymousBoard.setBgroup(maxBno+1);
		anonymousBoard.setBstep(0);
		anonymousBoard.setBindent(0);
		anonymousBoardRepo.save(anonymousBoard);
	}

	public AnonymousBoardVo selectAnonymousBoard(int bno) {
		return anonymousBoardRepo.findById(bno).get();
	}

	public boolean checkPwAjax(int bno, String bpw) {
		if(anonymousBoardRepo.findByBnoAndBpw(bno, bpw)==null) {
			return false;
		}else {
			return true;
		}
	}

	public void updateAnonymousBoard(AnonymousBoardVo anonymousBoard) {
		anonymousBoardRepo.save(anonymousBoard);
	}

	public void deleteAnonymousBoard(int bno) {
		anonymousBoardRepo.deleteById(bno);
	}

	@Override
	public void insertAnonymousBoardReply(AnonymousBoardVo anonymousBoard) {
		int bgroup = anonymousBoard.getBgroup();
		int bstep = anonymousBoard.getBstep();
		
		anonymousBoardRepo.replyShape(bgroup, bstep);
		
		anonymousBoard.setBstep(bstep+1);
		anonymousBoard.setBindent(anonymousBoard.getBindent()+1);
		
		anonymousBoardRepo.save(anonymousBoard);
	}

	public void increaseBcnt(int bno) {
		AnonymousBoardVo anonymousBoard = anonymousBoardRepo.findById(bno).get();
		anonymousBoard.setBcnt(anonymousBoard.getBcnt()+1);
		anonymousBoardRepo.save(anonymousBoard);
	}
}
