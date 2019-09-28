package com.bitgroupware.community.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.bitgroupware.member.vo.MemberVo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "docCenterFileList")
@Entity(name = "doc_center")
public class DocCenterVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int docNo;
	private String docCate;
	private String docTitle;
	@Column(columnDefinition = "longtext")
	private String docContent;
	@Column(updatable = false)
	private Date docDate = new Date();
	@Column(insertable = false, columnDefinition = "char(1) default 'N'")
	private String docDelCheck;
	private String docFileCheck;
	@Column(insertable = false, columnDefinition = "int(11) default 0")
	private int docCnt;
	
	@ManyToOne
	@JoinColumn(name="mem_id", updatable = false, columnDefinition = "varchar(100)")
	private MemberVo member;
	
	@OneToMany(mappedBy = "docCenter", fetch = FetchType.EAGER)
	private List<DocCenterFileVo> docCenterFileList = new ArrayList<DocCenterFileVo>();
	
}
