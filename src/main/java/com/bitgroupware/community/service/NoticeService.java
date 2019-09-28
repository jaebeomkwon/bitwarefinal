package com.bitgroupware.community.service;

import java.util.List;

import com.bitgroupware.community.vo.NoticeVo;
import com.bitgroupware.utils.Search;

public interface NoticeService {

	int countNotice(Search search);

	List<NoticeVo> selectNoticeList(int begin, Search search);
	
	List<NoticeVo> selectMainNoticeList();

	void insertNotice(NoticeVo notice);

	NoticeVo selectNoticeByNtNo(int ntNo);

	void updateNotice(NoticeVo notice);

	void deleteNoticeFile(String fileUrl);

	void increaseNtCnt(int ntNo);

}
