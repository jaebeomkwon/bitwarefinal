package com.bitgroupware.utils;

import com.bitgroupware.community.vo.AnonymousBoardVo;
import com.bitgroupware.community.vo.DocCenterVo;
import com.bitgroupware.community.vo.NoticeVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TemporaryPaging {

	public NoticeVo notice;
	public DocCenterVo docCenter;
	public AnonymousBoardVo anonymousBoard;
	
	public int count;
}
