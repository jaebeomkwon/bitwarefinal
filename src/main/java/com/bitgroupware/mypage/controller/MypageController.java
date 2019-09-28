package com.bitgroupware.mypage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bitgroupware.member.service.MemberService;
import com.bitgroupware.member.vo.MemberVo;
import com.bitgroupware.security.config.SecurityUser;

@Controller
@RequestMapping("/user")
public class MypageController {
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping("/mypage")
	public String mypage(Model model, @AuthenticationPrincipal SecurityUser principal) {
		MemberVo member = memberService.selectMember(principal.getMember().getMemId());
		model.addAttribute("member", member);
		return "mypage/mypage";
	}
	
	@RequestMapping("/updateMyInfo")
	public String updateMyInfo(MemberVo memberVo)  {
		memberService.updateMyInfo(memberVo);
		return "redirect:mypage";
	}

}
