package com.bitgroupware.community.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "docCenter")
@Entity(name = "doc_center_file")
public class DocCenterFileVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int docFileNo;
	private String docFileName;
	private String docFileUrl;
	private String docFileType;
	
	@ManyToOne
	@JoinColumn(name="docNo", nullable = false, updatable = false)
	private DocCenterVo docCenter;
	
	public void setDocCenter(DocCenterVo docCenter) {
		this.docCenter = docCenter;
		docCenter.getDocCenterFileList().add(this);
	}
}
