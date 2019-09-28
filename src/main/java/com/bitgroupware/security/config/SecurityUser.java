package com.bitgroupware.security.config;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.bitgroupware.member.vo.MemberVo;

public class SecurityUser extends User{
	
	private static final long serialVersionUID = 1L;
	
	private MemberVo member;
	
	public SecurityUser(MemberVo member) {
		super(member.getMemId(), member.getMemPw(), AuthorityUtils.createAuthorityList(member.getRole().toString()));
		//member.getId는 꼭 주키일 필요는 없고 id로 사용할 것을 넣는다. 
		this.member = member;
	}

	public MemberVo getMember() {
		return member;
	}

}
