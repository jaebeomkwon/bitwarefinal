package com.bitgroupware.community.vo;

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
@ToString(exclude = "notice")
@Entity(name = "notice_file")
public class NoticeFileVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ntFileNo;
	private String ntFileName;
	private String ntFileUrl;
	private String ntFileType;
	
	@ManyToOne
	@JoinColumn(name="ntNo", nullable = false, updatable = false)
	private NoticeVo notice;
	
	public void setNotice(NoticeVo notice) {
		this.notice = notice;
		notice.getNoticeFileList().add(this);
	}
}
