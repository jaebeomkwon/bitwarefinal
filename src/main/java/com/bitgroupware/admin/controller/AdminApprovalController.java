package com.bitgroupware.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bitgroupware.approval.beans.ApprovalDocumentDto;
import com.bitgroupware.approval.service.ApprovalDocService;

@Controller
@RequestMapping("/admin")
public class AdminApprovalController {

	@Autowired
	private ApprovalDocService approvalService;

	// 문서리스트
	@RequestMapping("/selectApprovalDocList")
	public String selectApprovalDocList(Model model) {
		List<ApprovalDocumentDto> approvalDocList = approvalService.selectApprovalDocList();
		model.addAttribute("approvalDocList", approvalDocList);
		return "admin/approval/approvalDocList";
	}

	// 등록페이지(insert+update)
	@RequestMapping("/insertApprovalDocView")
	public String insertApprovalDocView(Model model,ApprovalDocumentDto apdocDto) {
		if(apdocDto.getApdocNo() != 0) { // 수정할 때 필요 해서  Dto 가져감
			apdocDto = approvalService.selectApprovalDoc(apdocDto.getApdocNo());
			model.addAttribute("apdocDto",apdocDto);
		}
		return "admin/approval/approvalDocWrite";
	}

	// 등록(insert+update)
	@RequestMapping("/insertApprovalDoc")
	public String insertApprovalDoc(ApprovalDocumentDto apdocDto) {
		approvalService.insertApprovalDoc(apdocDto);
		return "redirect:/admin/selectApprovalDocList";
	}

	// 삭제
	@RequestMapping("/deleteApprovalDoc")
	public String deleteApprovalDocList(Model model, ApprovalDocumentDto apdocDto) {
		approvalService.deleteApprovalDoc(apdocDto);
		return "redirect:/admin/selectApprovalDocList";
	}
}
