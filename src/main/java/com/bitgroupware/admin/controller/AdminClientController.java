package com.bitgroupware.admin.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bitgroupware.etc.beans.ClientVo;
import com.bitgroupware.etc.service.ClientService;
import com.bitgroupware.utils.Pager;
import com.bitgroupware.utils.Search;

@Controller
@RequestMapping("/admin")
public class AdminClientController {

	@Autowired
	private ClientService clientService;
	
	@RequestMapping("/selectClientList")
	public String selectClientList(Model model, @RequestParam(defaultValue = "1") int curPage, Search search) {
		
		int count = clientService.countClient(search);
		
		Pager page = new Pager(count, curPage);
		int blockBegin = page.getBlockBegin();
		int blockEnd = page.getBlockEnd();
		
		List<Integer> block= new ArrayList<Integer>();
		for( int i = blockBegin; i<=blockEnd; i++) {
			block.add(i);
		}
		
		int begin = page.getPageBegin()-1;
		
		List<ClientVo> clientList = clientService.selectClientList(begin, search);
		
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String today = format.format(date);
		
		model.addAttribute("clientList", clientList);
		model.addAttribute("today",today);
		model.addAttribute("page",page);
		model.addAttribute("block",block);
		model.addAttribute("search",search);
		
		return "admin/etc/clientList";
	}
	
	@RequestMapping("/insertClient")
	public String insertClient(ClientVo client) {
		clientService.insertClient(client);
		return "redirect:/admin/selectClientList";
	}
	
	@RequestMapping(value="/selectClientByClientNoAjax")
	@ResponseBody
	public List<ClientVo> selectClientByClientNoAjax(int clientNo) {
		ClientVo client = clientService.selectClientByClientNo(clientNo);
		List<ClientVo> clientList = new ArrayList<ClientVo>();
		clientList.add(client);
		return clientList;
	}

	@RequestMapping("/updateClient")
	public String updateClient(ClientVo client) {
		System.out.println(client);
		clientService.updateClient(client);
		return "redirect:/admin/selectClientList";
	}
	
	@RequestMapping("/deleteClient")
	public String deleteClient(int clientNo) {
		clientService.deleteClient(clientNo);
		return "redirect:/admin/selectClientList";
	}
	
	@RequestMapping(value="/deleteClientCheckBox",method=RequestMethod.POST)
	@ResponseBody
	public String deleteClinet(@RequestParam(value="checkBoxArr[]") List<String> checkBoxArr) {
		for(String checkBox: checkBoxArr) {
			System.out.println(checkBox);
			clientService.deleteClient(Integer.parseInt(checkBox));
		}
		return "삭제완료";
	}

	
	
}
