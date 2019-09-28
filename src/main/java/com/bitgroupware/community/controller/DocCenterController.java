package com.bitgroupware.community.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bitgroupware.community.service.DocCenterService;
import com.bitgroupware.community.vo.DocCenterVo;
import com.bitgroupware.community.vo.NoticeVo;
import com.bitgroupware.member.utils.Role;
import com.bitgroupware.security.config.SecurityUser;
import com.bitgroupware.utils.Pager;
import com.bitgroupware.utils.Search;
import com.bitgroupware.utils.TemporaryPaging;

@Controller
@RequestMapping("/user")
public class DocCenterController {

	@Autowired
	private DocCenterService docCenterService;
	
	@RequestMapping("/selectDocCenterList")
	public String selectDocCenterList(Model model, @RequestParam(defaultValue = "1") int curPage, Search search) {
		
		int count = docCenterService.countDocCenter(search);
		
		Pager page = new Pager(count, curPage);
		int blockBegin = page.getBlockBegin();
		int blockEnd = page.getBlockEnd();
		
		List<Integer> block= new ArrayList<Integer>();
		for( int i = blockBegin; i<=blockEnd; i++) {
			block.add(i);
		}
		
		int begin = page.getPageBegin()-1;
		
		List<DocCenterVo> docCenterList = docCenterService.selectDocCenterList(begin, search);
		
		List<TemporaryPaging> pagingList = new ArrayList<TemporaryPaging>();

		count = count - ((curPage-1) * 10);
		for (DocCenterVo docCenter : docCenterList) {
			TemporaryPaging paging = new TemporaryPaging();
			paging.setDocCenter(docCenter);
			paging.setCount(count--);
			pagingList.add(paging);
		}
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String today = format.format(date);
		
		model.addAttribute("pagingList", pagingList);
		model.addAttribute("today",today);
		model.addAttribute("page",page);
		model.addAttribute("block",block);
		model.addAttribute("search",search);
		
		return "community/docCenterList";
	}
	
	@RequestMapping("/selectDocCenter")
	public String selectDocCenter(Model model, int docNo, @AuthenticationPrincipal SecurityUser principal) {
		if(!principal.getMember().getRole().equals(Role.ROLE_ADMIN)) {
			docCenterService.increaseDocCnt(docNo);
		}
		DocCenterVo docCenter = docCenterService.selectDocCenterByDocNo(docNo);
		model.addAttribute("docCenter", docCenter);
		return "community/docCenterDetail";
	}
}
