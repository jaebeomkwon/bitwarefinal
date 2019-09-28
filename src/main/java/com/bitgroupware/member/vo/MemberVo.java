package com.bitgroupware.member.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import com.bitgroupware.company.vo.DepartmentVo;
import com.bitgroupware.company.vo.RanksVo;
import com.bitgroupware.company.vo.TeamVo;
import com.bitgroupware.member.utils.Role;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity(name = "member")
public class MemberVo {

	@Id
	@Column(columnDefinition = "varchar(100)")
	private String memId;
	private String memPw;
	@Column(updatable = false)
	private String memName;
	@Enumerated(EnumType.STRING)
	private Role role;
	// 시큐리티에서 ture면 인식해줌, false면 인식 안함 '아마도.'
	@Column(insertable = false, columnDefinition = "boolean default true")
	private boolean enabled;
	
	private String memJoinDate;
	@Column(insertable = false)
	private String memQuitDate;
	@Column(insertable = false)
	private String memQuitReason;
	@Column(insertable = false, columnDefinition = "varchar(100) default 'work'")
	private String memStatus;
	
	private String memTel;
	private String memOfficeTel;
	
	@Column(updatable = false)
	private String memJumin;
	private String memSignUrl;
	@Column(insertable = false, columnDefinition = "int(11) default 15")
	private int memVacation;
	
	private String memAddrCode;
	private String memAddr;
	private String memAddrDetail;
	private String memAddrExtra;
	
	@Transient
	private MultipartFile file;
	
	@ManyToOne
	@JoinColumn(name = "dept_name")
	private DepartmentVo department;
	@ManyToOne
	@JoinColumn(name = "team_name")
	private TeamVo team;
	@ManyToOne
	@JoinColumn(name = "ranks")
	private RanksVo ranks;
}
