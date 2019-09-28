package com.bitgroupware.etc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bitgroupware.etc.beans.ClientVo;
import com.bitgroupware.etc.persistence.ClientRepository;
import com.bitgroupware.utils.Search;

@Service
public class ClientServiceImpl implements ClientService{

	@Autowired
	private ClientRepository clientRepo;

	public int countClient(Search search) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("clientName");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");

		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		int count = 0;
		switch (searchCondition) {
		case "clientName":
			count = clientRepo.countByClientName(searchKeyword);
			break;
		case "clientCompany":
			count = clientRepo.countByClientCompany(searchKeyword);
			break;
		}
		return count;
	}
	
	public List<ClientVo> selectClientList(int begin, Search search) {
		if (search.getSearchCondition() == null)
			search.setSearchCondition("clientName");
		if (search.getSearchKeyword() == null)
			search.setSearchKeyword("");

		String searchCondition = search.getSearchCondition();
		String searchKeyword = "%" + search.getSearchKeyword().trim() + "%";
		List<ClientVo> clientList = null;
		switch (searchCondition) {
		case "clientName":
			clientList = clientRepo.findAllByPagingAndClientName(begin, searchKeyword);
			break;
		case "clientCompany":
			clientList = clientRepo.findAllByPagingAndClientCompany(begin, searchKeyword);
			break;
		}
		return clientList;
	}

	public void insertClient(ClientVo client) {
		clientRepo.save(client);
	}

	public ClientVo selectClientByClientNo(int clientNo) {
		return clientRepo.findById(clientNo).get();
	}

	public void updateClient(ClientVo client) {
		clientRepo.save(client);
	}

	public void deleteClient(int clientNo) {
		clientRepo.deleteById(clientNo);
	}

}