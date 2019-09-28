package com.bitgroupware;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitgroupware.security.config.SecurityUser;

@Controller
public class LoginController {

	@RequestMapping("/login")
	public void login() {
	}
	@RequestMapping("/loginFail")
	public void loginFail() {
	}
	@RequestMapping("/loginSuccess")
	public void loginSuccess() {
	}
	@RequestMapping("/accessDenied")
	public void accessDenied() {
	}
	@RequestMapping("/loginCheck")
	@ResponseBody
	public String loginCheck(@AuthenticationPrincipal SecurityUser principal) {
		try {
			if(principal.getMember()!=null) {
				return "success";
			}else {
				return "fail";
			}
		}catch (NullPointerException e) {
			return "fail";
		}
	}
}
