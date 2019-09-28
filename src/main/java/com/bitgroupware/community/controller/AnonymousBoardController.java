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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitgroupware.community.service.AnonymousBoardService;
import com.bitgroupware.community.vo.AnonymousBoardVo;
import com.bitgroupware.member.utils.Role;
import com.bitgroupware.security.config.SecurityUser;
import com.bitgroupware.utils.Pager;
import com.bitgroupware.utils.Search;
import com.bitgroupware.utils.TemporaryPaging;

@Controller
@RequestMapping("/user")
public class AnonymousBoardController {

	@Autowired
	private AnonymousBoardService anonymousBoardService;
	
	@RequestMapping("/selectAnonymousBoardList")
	public String selectAnonymousBoardList(Model model, @RequestParam(defaultValue = "1") int curPage, Search search) {
		
		int count = anonymousBoardService.countAnonymousBoard(search);
		
		Pager page = new Pager(count, curPage);
		int blockBegin = page.getBlockBegin();
		int blockEnd = page.getBlockEnd();
		
		List<Integer> block= new ArrayList<Integer>();
		for( int i = blockBegin; i<=blockEnd; i++) {
			block.add(i);
		}
		
		int begin = page.getPageBegin()-1;
				
		List<AnonymousBoardVo> anonymousBoardList = anonymousBoardService.selectAnonymousBoardList(begin,search);
		
		List<TemporaryPaging> pagingList = new ArrayList<TemporaryPaging>();

		count = count - ((curPage-1) * 10);
		for (AnonymousBoardVo anonymousBoard : anonymousBoardList) {
			TemporaryPaging paging = new TemporaryPaging();
			paging.setAnonymousBoard(anonymousBoard);
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
		
		return "community/anonymousBoardList";
	}
	
	@RequestMapping("/insertAnonymousBoardView")
	public String insertAnonymousBoardView() {
		return "community/anonymousBoardWrite";
	}
	
	@RequestMapping("/insertAnonymousBoard")
	public String insertAnonymousBoard(AnonymousBoardVo anonymousBoard) {
		anonymousBoardService.insertAnonymousBoard(anonymousBoard);
		return "redirect:/user/selectAnonymousBoardList";
	}
	
	@RequestMapping("/selectAnonymousBoard")
	public String selectAnonymousBoard(Model model, int bno, @AuthenticationPrincipal SecurityUser principal) {
		if(!principal.getMember().getRole().equals(Role.ROLE_ADMIN)) {
			anonymousBoardService.increaseBcnt(bno);
		}
		AnonymousBoardVo anonymousBoard = anonymousBoardService.selectAnonymousBoard(bno);
		model.addAttribute("anonymousBoard", anonymousBoard);
		return "community/anonymousBoardDetail";
	}
	
	@RequestMapping("/checkPwAjax")
	@ResponseBody
	public boolean checkPwAjax(int bno, String bpw) {
		return anonymousBoardService.checkPwAjax(bno, bpw);
	}
	
	@RequestMapping("/updateAnonymousBoardView")
	public String updateAnonymousBoardView(Model model, AnonymousBoardVo anonymousBoard) {
		model.addAttribute("anonymousBoard",anonymousBoard);
		return "community/anonymousBoardUpdate";
	}
	
	@RequestMapping("/updateAnonymousBoard")
	public String updateAnonymousBoard(AnonymousBoardVo anonymousBoard) {
		anonymousBoardService.updateAnonymousBoard(anonymousBoard);
		return "redirect:/user/selectAnonymousBoardList";
	}
	
	@RequestMapping("/deleteAnonymousBoard")
	public String deleteAnonymousBoard(int bno) {
		anonymousBoardService.deleteAnonymousBoard(bno);
		return "redirect:/user/selectAnonymousBoardList";
	}
	
	@RequestMapping(value="/deleteAnonymousBoardCheckBox",method=RequestMethod.POST)
	@ResponseBody
	public String deleteNotice(@RequestParam(value="checkBoxArr[]") List<String> checkBoxArr) {
		for(String checkBox: checkBoxArr) {
			anonymousBoardService.deleteAnonymousBoard(Integer.parseInt(checkBox));
		}
		return "삭제완료";
	}
	
	@RequestMapping("/insertAnonymousBoardReplyView")
	public String insertAnonymousBoardReplyView(Model model, AnonymousBoardVo anonymousBoard) {
		model.addAttribute("anonymousBoard", anonymousBoard);
		return "community/anonymousBoardReplyWrite";
	}
	
	@RequestMapping("/insertAnonymousBoardReply")
	public String insertAnonymousBoardReply(AnonymousBoardVo anonymousBoard) {
		anonymousBoard.setBtitle(anonymousBoard.getBtitle().replace("re:", ""));
		anonymousBoardService.insertAnonymousBoardReply(anonymousBoard);
		return "redirect:/user/selectAnonymousBoardList";
	}
	
	
}
