package com.bitgroupware.approval.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bitgroupware.approval.beans.ApprovalDocumentDto;
import com.bitgroupware.approval.beans.ApprovalDto;
import com.bitgroupware.approval.beans.ApprovalFileDto;
import com.bitgroupware.approval.persistence.ApprovalDao;
import com.bitgroupware.approval.service.ApprovalDocService;
import com.bitgroupware.approval.service.ApprovalService;
import com.bitgroupware.member.vo.MemberVo;
import com.bitgroupware.security.config.SecurityUser;
import com.bitgroupware.utils.Pager;
import com.bitgroupware.utils.Search;

@Controller
@RequestMapping("/user")
public class ApprovalController {
	
	@Autowired
	private ApprovalService approvalService;
	
	@Autowired
	private ApprovalDocService approvalDocService;
	
	@Autowired
	private ApprovalDao apdao;

	@RequestMapping("/approvalDocList")
	@ResponseBody
	public List<ApprovalDocumentDto> selectApprovalDocList() {
		List<ApprovalDocumentDto> approvalDocList = approvalDocService.selectApprovalDocList();
		return approvalDocList;
	}
	
	@RequestMapping("/insertApprovalView")
	public String insertApprovalView(Model model, int apdocNo) {
		ApprovalDocumentDto approvalDocument = approvalDocService.selectApprovalDoc(apdocNo);
		model.addAttribute("approvalDocument",approvalDocument);
		return "approval/approvalWrite";
	}
	
	// 등록
	@RequestMapping("/insertApproval")
	public String insertApproval(ApprovalDto approval, @AuthenticationPrincipal SecurityUser principal,MultipartHttpServletRequest request,ApprovalFileDto approvalFileDto) {
		approval.setApDeleteflag("N");
		approval.setApDocstatus("1");
		approval.setFileCheck("N");
		approval.setMemId(principal.getMember().getMemId());
		approval.setMemName(principal.getMember().getMemName());
		MemberVo member = principal.getMember();
		String memSignUrl = member.getMemSignUrl();
		int ranks = member.getRanks().getRanksNo();
		
		switch(ranks) {
		case 1 : approval.setApSignUrl1(memSignUrl);
				 approval.setApSignName1(member.getMemName());
			break;
		case 2 : approval.setApSignUrl2(memSignUrl);
				 approval.setApSignName2(member.getMemName());
			break;
		case 3 : approval.setApSignUrl3(memSignUrl);
				 approval.setApSignName3(member.getMemName());
			break;
		case 4 : approval.setApSignUrl4(memSignUrl);
				 approval.setApSignName4(member.getMemName());
			break;
		case 5 : approval.setApSignUrl5(memSignUrl);
				 approval.setApSignName5(member.getMemName());
			break;
		}
		approvalService.insertApproval(approval, principal.getMember());
		
		// 파일 업로드
		String apNo = apdao.selectMaxApNo();
		List<MultipartFile> fileList = request.getFiles("file");
		fileList.remove((fileList.size()-1));
		System.out.println("filelistsize="+fileList.size());
		if(!approvalFileDto.getFile().isEmpty()) {
		String path = request.getSession().getServletContext().getRealPath("/");
		System.out.println(path);
		System.out.println("====================="+fileList);
			for (MultipartFile mf : fileList) {
				String originFileName = mf.getOriginalFilename(); // 원본 파일 명
				String saveFile = path + System.currentTimeMillis() + originFileName;
				String fileUrl = File.separator + System.currentTimeMillis() + originFileName;
				approvalFileDto.setApFilename(originFileName);
				approvalFileDto.setApFileurl(fileUrl);
				approvalFileDto.setApNo(apNo);
				try {
					mf.transferTo(new File(saveFile));
					approvalService.insertApprovalFile(approvalFileDto);
					apdao.updateApprovalFileCheck(apNo);
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "redirect:/user/selectApprovalListToBe";
	}
	
	
	@RequestMapping("/downloadFile")
	@ResponseBody
	public ResponseEntity<byte[]> displayFile(String apFileurl,String apFilename,HttpServletRequest request) throws Exception {
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		String path = request.getSession().getServletContext().getRealPath("/");
		try {
			HttpHeaders headers = new HttpHeaders();
			in = new FileInputStream(path+apFileurl);
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			headers.add("Content-Disposition",
					"attachment; filename=\"" + new String(apFilename.getBytes("utf-8"), "iso-8859-1") + "\"");
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		} finally {
			if (in != null)
				in.close();
		}
		return entity;
	}
	
	@RequestMapping("/updateApprovalView")
	public String updateApprovalView(Model model,String apNo,ApprovalFileDto approvalFile) {
		ApprovalDto approval = approvalService.selectApproval(apNo);
		List<ApprovalFileDto> approvalFileList = approvalService.selectApprovalFile(apNo);
		model.addAttribute("approval",approval);
		model.addAttribute("approvalFileList",approvalFileList);
		return "approval/approvalUpdate";
	}
	
	@RequestMapping("/updateApproval")
	public String updateApproval(ApprovalDto approval,ApprovalFileDto approvalFileDto,MultipartHttpServletRequest request) {
		System.out.println("update");
		System.out.println(approval);
		approvalService.updateApproval(approval);
		// 파일 업로드
		String apNo = apdao.selectMaxApNo();
		List<MultipartFile> fileList = request.getFiles("file");
		System.out.println("1");
		if(!approvalFileDto.getFile().isEmpty()) {
			System.out.println("2");
			String path = request.getSession().getServletContext().getRealPath("/");
				for (MultipartFile mf : fileList) {
					System.out.println("3");
					String originFileName = mf.getOriginalFilename(); // 원본 파일 명
					String saveFile = path + System.currentTimeMillis() + originFileName;
					String fileUrl = File.separator + System.currentTimeMillis() + originFileName;
					approvalFileDto.setApFilename(originFileName);
					approvalFileDto.setApFileurl(fileUrl);
					approvalFileDto.setApNo(apNo);
					try {
						mf.transferTo(new File(saveFile));
						approvalService.updateApprovalFile(approvalFileDto);
						System.out.println("4");
						apdao.updateApprovalFileCheck(apNo);
						System.out.println(approvalFileDto);
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		return "redirect:/user/selectApprovalListToBe";
	}
	
	
	// 결재 받을 문서 리스트
	@RequestMapping("/selectApprovalListToBe")
	public String selectApprovalListToBe(Model model, @AuthenticationPrincipal SecurityUser principal, String status, 
			@RequestParam(defaultValue = "1") int curPage, Search search,String apNo) {
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String today = format.format(date);
		String memId = principal.getMember().getMemId();
		List<ApprovalDto> approvalListToBe;
		int count;
		if(status==""||status==null) {
			count = approvalService.countApproval(memId,search);
		}else {
			count = approvalService.countApprovalStatus(memId,status,search);
		}
			Pager page = new Pager(count, curPage);
			int blockBegin = page.getBlockBegin();
			int blockEnd = page.getBlockEnd();
			
			List<Integer> block= new ArrayList<Integer>();
			for( int i = blockBegin; i<=blockEnd; i++) {
				block.add(i);
			}
			int begin = page.getPageBegin()-1;
		if(status==""||status==null) {
			approvalListToBe = approvalService.selectApprovalListToBeByTotal(memId,begin,search);
		}else {
			approvalListToBe = approvalService.selectApprovalListToBe(memId, status,begin, search);
		}
		model.addAttribute("approvalListToBe",approvalListToBe);
		model.addAttribute("today",today);
		model.addAttribute("page",page);
		model.addAttribute("block",block);
		model.addAttribute("search",search);
		model.addAttribute("status",status);
		return "approval/approvalListToBe";
	}
	
	// 결재 할 문서 리스트
	@RequestMapping("/selectApprovalListTo")
	public String selectApprovalListTo(Model model, @AuthenticationPrincipal SecurityUser principal, 
			@RequestParam(defaultValue = "1") int curPage, Search search) {
		String memId = principal.getMember().getMemId();
		int count = approvalService.countApproval(memId,search);

		Pager page = new Pager(count, curPage);
		int blockBegin = page.getBlockBegin();
		int blockEnd = page.getBlockEnd();

		List<Integer> block = new ArrayList<Integer>();
		for (int i = blockBegin; i <= blockEnd; i++) {
			block.add(i);
		}

		int begin = page.getPageBegin() - 1;
		
		List<ApprovalDto> approvalList = approvalService.selectApprovalListTo(memId,begin,search);
		model.addAttribute("approvalList",approvalList);
		System.out.println(approvalList);
		model.addAttribute("page",page);
		model.addAttribute("block",block);
		model.addAttribute("search",search);
		model.addAttribute("status",1);
		return "approval/approvalListTo";
	}
	
	@RequestMapping("/selectApprovalListFinish")
	public String selectApprovalListToFinish(Model model, @AuthenticationPrincipal SecurityUser principal, 
			@RequestParam(defaultValue = "1") int curPage, Search search) {
		
		String memId = principal.getMember().getMemId();
		String memName = principal.getMember().getMemName();
		int count = approvalService.countApprovalToFinish(memId,search);

		Pager page = new Pager(count, curPage);
		int blockBegin = page.getBlockBegin();
		int blockEnd = page.getBlockEnd();

		List<Integer> block = new ArrayList<Integer>();
		for (int i = blockBegin; i <= blockEnd; i++) {
			block.add(i);
		}

		int begin = page.getPageBegin() - 1;
		
		List<ApprovalDto> approvalList = approvalService.selectApprovalListToFinish(memId,begin,search,memName);
		model.addAttribute("approvalList",approvalList);
		model.addAttribute("page",page);
		model.addAttribute("block",block);
		model.addAttribute("search",search);
		model.addAttribute("status",2);
		return "approval/approvalListTo";
	}
	

	// 읽기
	@RequestMapping("/selectApprovalView")
	public String selectApprovalView(Model model, String apNo, @AuthenticationPrincipal SecurityUser principal) {
		ApprovalDto approval = approvalService.selectApproval(apNo);
		List<ApprovalFileDto> approvalFileList = approvalService.selectApprovalFile(apNo);
		List<ApprovalDocumentDto> approvalFile = approvalDocService.selectApprovalDocList();
		model.addAttribute("approval", approval);
		model.addAttribute("approvalFileList",approvalFileList);
		model.addAttribute("approvalFile",approvalFile);
		String memId = principal.getMember().getMemId();
		int firstRanksNo = approvalService.selectFirstRanksNo(apNo);
		int ranksNo = approvalService.selectRanksNo(memId);
		model.addAttribute("firstRanksNo",firstRanksNo);
		model.addAttribute("ranksNo",ranksNo);
		
		String sessionId = principal.getMember().getMemId();
		model.addAttribute("sessionId", sessionId);
		return "approval/approvalDetail";
	}
	
	// 삭제
	@RequestMapping("/deleteApproval")
	public String deleteApproval(ApprovalDto approval) {
		approvalService.deleteApproval(approval);
		return "redirect:/user/selectApprovalListToBe";
	}
	
	// 결재
	@RequestMapping("/updateApprovalPath")
	public String updateApprovalPath(String apNo, int finalSign, int ranksNo, @AuthenticationPrincipal SecurityUser principal) {
		ApprovalDto approval = approvalService.selectApproval(apNo);
		MemberVo member = principal.getMember();
		if(ranksNo==finalSign) {
			approval.setApDocstatus("4");
			approval.setApSignpath(null);
			String memSignUrl = member.getMemSignUrl();
			int ranks = member.getRanks().getRanksNo();
			switch(ranks) {
			case 1 : approval.setApSignUrl1(memSignUrl);
					 approval.setApSignName1(member.getMemName());
				break;
			case 2 : approval.setApSignUrl2(memSignUrl);
					 approval.setApSignName2(member.getMemName());
				break;
			case 3 : approval.setApSignUrl3(memSignUrl);
					 approval.setApSignName3(member.getMemName());
				break;
			case 4 : approval.setApSignUrl4(memSignUrl);
					 approval.setApSignName4(member.getMemName());
				break;
			case 5 : approval.setApSignUrl5(memSignUrl);
					 approval.setApSignName5(member.getMemName());
				break;
			}
		}else {
			approval.setApDocstatus("2");
			approval.setApSignpath(member.getMemName());
			String memSignUrl = member.getMemSignUrl();
			int ranks = member.getRanks().getRanksNo();
			switch(ranks) {
			case 1 : approval.setApSignUrl1(memSignUrl);
					 approval.setApSignName1(member.getMemName());
				break;
			case 2 : approval.setApSignUrl2(memSignUrl);
					 approval.setApSignName2(member.getMemName());
				break;
			case 3 : approval.setApSignUrl3(memSignUrl);
					 approval.setApSignName3(member.getMemName());
				break;
			case 4 : approval.setApSignUrl4(memSignUrl);
					 approval.setApSignName4(member.getMemName());
				break;
			case 5 : approval.setApSignUrl5(memSignUrl);
					 approval.setApSignName5(member.getMemName());
				break;
			}
		}
		approvalService.updateApprovalPath(approval, member);
		return "redirect:/user/selectApprovalView?apNo="+apNo;
	}
	
	// 반려
	@RequestMapping("/updateApprovalCancel")
	public String updateApprovalCancel(String apNo, String apComment, @AuthenticationPrincipal SecurityUser principal) {
		ApprovalDto approval = approvalService.selectApproval(apNo);
		approval.setApDocstatus("3");
		approval.setApSignpath(null);
		approval.setApComment(apComment);
		MemberVo member = principal.getMember();
		int ranks = member.getRanks().getRanksNo();
		String memSignUrl = "/images/no.png";

		switch (ranks) {
		case 1:
			approval.setApSignUrl1(memSignUrl);
			approval.setApSignName1(member.getMemName());
			break;
		case 2:
			approval.setApSignUrl2(memSignUrl);
			approval.setApSignName2(member.getMemName());
			break;
		case 3:
			approval.setApSignUrl3(memSignUrl);
			approval.setApSignName3(member.getMemName());
			break;
		case 4:
			approval.setApSignUrl4(memSignUrl);
			approval.setApSignName4(member.getMemName());
			break;
		case 5:
			approval.setApSignUrl5(memSignUrl);
			approval.setApSignName5(member.getMemName());
			break;
		}
		approvalService.updateApprovalCancel(approval);
		return "redirect:/user/selectApprovalView?apNo="+apNo;
	}
}
