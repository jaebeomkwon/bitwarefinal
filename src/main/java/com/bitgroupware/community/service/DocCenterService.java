package com.bitgroupware.community.service;

import java.util.List;

import com.bitgroupware.community.vo.DocCenterVo;
import com.bitgroupware.utils.Search;

public interface DocCenterService {

	int countDocCenter(Search search);

	List<DocCenterVo> selectDocCenterList(int begin, Search search);

	void insertDocCenter(DocCenterVo docCenter);

	DocCenterVo selectDocCenterByDocNo(int docNo);

	void updateDocCenter(DocCenterVo docCenter);

	void deleteDocCenterFile(String fileUrl);

	void increaseDocCnt(int docNo);

}
