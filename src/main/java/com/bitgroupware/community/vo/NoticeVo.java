package com.bitgroupware.community.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.bitgroupware.member.vo.MemberVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "noticeFileList")
@Entity(name = "notice")
public class NoticeVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ntNo;
	private String ntCate;
	private String ntTitle;
	@Column(columnDefinition = "longtext")
	private String ntContent;
	@Column(updatable = false)
	private Date ntDate = new Date();
	@Column(insertable = false, columnDefinition = "char(1) default 'N'")
	private String ntDelCheck;
	private String ntFileCheck;
	@Column(insertable = false, columnDefinition = "int(11) default 0")
	private int ntCnt;
	
	@ManyToOne
	@JoinColumn(name="mem_id", updatable = false, columnDefinition = "varchar(100)")
	private MemberVo member;
	
	@OneToMany(mappedBy = "notice", fetch = FetchType.EAGER)
	private List<NoticeFileVo> noticeFileList = new ArrayList<NoticeFileVo>();
	
}
