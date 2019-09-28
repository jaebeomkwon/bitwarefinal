package com.bitgroupware.community.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitgroupware.community.persistence.DocCenterFileRepository;
import com.bitgroupware.community.persistence.DocCenterRepository;
import com.bitgroupware.community.utils.TemporaryFileUrl;
import com.bitgroupware.community.vo.DocCenterFileVo;
import com.bitgroupware.community.vo.DocCenterVo;
import com.bitgroupware.community.vo.NoticeVo;
import com.bitgroupware.utils.Search;

@Service
public class DocCenterServiceImpl implements DocCenterService {

	@Autowired
	private DocCenterRepository docCenterRepo;
	@Autowired
	private DocCenterFileRepository docCenterFileRepo;

	public int countDocCenter(Search search) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("docTitle");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");

		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		int count = 0;
		switch (searchCondition) {
		case "docTitle":
			count = docCenterRepo.countByDocTitle(searchKeyword);
			break;
		case "docCate":
			count = docCenterRepo.countByDocCate(searchKeyword);
			break;
		case "docContent":
			count = docCenterRepo.countByDocContent(searchKeyword);
			break;
		}
		return count;
	}
	
	public List<DocCenterVo> selectDocCenterList(int begin, Search search) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("docTitle");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");

		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		List<DocCenterVo> docCenterList = null;
		switch (searchCondition) {
		case "docTitle":
			docCenterList = docCenterRepo.findAllByPagingAndDocTitle(begin, searchKeyword);
			break;
		case "docCate":
			docCenterList = docCenterRepo.findAllByPagingAndDocCate(begin, searchKeyword);
			break;
		case "docContent":
			docCenterList = docCenterRepo.findAllByPagingAndDocContent(begin, searchKeyword);
			break;
		}
		return docCenterList;
	}

	@Transactional
	public void insertDocCenter(DocCenterVo docCenter) {
		Map<String, String> map = TemporaryFileUrl.fileUrl;
		if (!map.isEmpty()) {
			docCenter.setDocFileCheck("Y");
			docCenterRepo.save(docCenter);
			int docNo = docCenterRepo.findByMaxDocNo();
			DocCenterVo temporaryDocCenter = docCenterRepo.findById(docNo).get();
			Set<String> keys = map.keySet();
			Iterator<String> iterator = keys.iterator();
			int idx = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				String fileUrl = map.get(key);
				idx = fileUrl.indexOf("_") + 1;
				String fileName = fileUrl.substring(idx);
				DocCenterFileVo docCenterFile = new DocCenterFileVo();
				docCenterFile.setDocCenter(temporaryDocCenter);
				docCenterFile.setDocFileName(fileName);
				docCenterFile.setDocFileUrl(fileUrl);
				String regex = "^([\\S]+(\\.(?i)(jpg|gif|png|jpeg|bmp))$)";
				boolean bool = fileName.matches(regex);
				if(bool) {
					docCenterFile.setDocFileType("image");
				}else {
					docCenterFile.setDocFileType("document");
				}
				docCenterFileRepo.save(docCenterFile);
			}
			TemporaryFileUrl.fileUrl.clear();
		} else {
			docCenter.setDocFileCheck("N");
			docCenterRepo.save(docCenter);
		}
	}

	public DocCenterVo selectDocCenterByDocNo(int docNo) {
		return docCenterRepo.findById(docNo).get();
	}

	@Transactional
	public void updateDocCenter(DocCenterVo docCenter) {
		Map<String, String> map = TemporaryFileUrl.fileUrl;
		if (!map.isEmpty()) {
			docCenter.setDocFileCheck("Y");
			docCenterRepo.save(docCenter);
			Set<String> keys = map.keySet();
			Iterator<String> iterator = keys.iterator();
			int idx = 0;
			while (iterator.hasNext()) {
				String key = iterator.next();
				String fileUrl = map.get(key);
				idx = fileUrl.indexOf("_") + 1;
				String fileName = fileUrl.substring(idx);
				DocCenterFileVo docCenterFile = new DocCenterFileVo();
				docCenterFile.setDocCenter(docCenter);
				docCenterFile.setDocFileName(fileName);
				docCenterFile.setDocFileUrl(fileUrl);
				String regex = "^([\\S]+(\\.(?i)(jpg|gif|png|jpeg|bmp))$)";
				boolean bool = fileName.matches(regex);
				if(bool) {
					docCenterFile.setDocFileType("image");
				}else {
					docCenterFile.setDocFileType("document");
				}
				docCenterFileRepo.save(docCenterFile);
			}
			TemporaryFileUrl.fileUrl.clear();
		} else {
			List<DocCenterFileVo> docCenterFileList = docCenterFileRepo.findByDocCenter(docCenter);
			if (docCenterFileList.size() == 0) {
				docCenter.setDocFileCheck("N");
			} else {
				docCenter.setDocFileCheck("Y");
			}
			docCenterRepo.save(docCenter);
		}
	}

	public void deleteDocCenterFile(String fileUrl) {
		int count = docCenterFileRepo.countByDocFileUrl(fileUrl);
		if (count != 0) {
			DocCenterFileVo docCenterFile = docCenterFileRepo.findByDocFileUrl(fileUrl);
			docCenterFileRepo.delete(docCenterFile);
		}
	}

	public void increaseDocCnt(int docNo) {
		DocCenterVo docCenter = docCenterRepo.findById(docNo).get();
		docCenter.setDocCnt(docCenter.getDocCnt()+1);
		docCenterRepo.save(docCenter);
	}
}