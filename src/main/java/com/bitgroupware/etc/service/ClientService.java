package com.bitgroupware.etc.service;

import java.util.List;

import com.bitgroupware.etc.beans.ClientVo;
import com.bitgroupware.utils.Search;

public interface ClientService {

	int countClient(Search search);

	List<ClientVo> selectClientList(int begin, Search search);

	void insertClient(ClientVo client);

	ClientVo selectClientByClientNo(int clientNo);

	void updateClient(ClientVo client);

	void deleteClient(int clientNo);

}
