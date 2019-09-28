package com.bitgroupware.etc.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bitgroupware.etc.beans.ClientVo;

public interface ClientRepository extends JpaRepository<ClientVo, Integer>{

	@Query(value = "select r1.* from (select * from client where client_name like ?2 order by client_company asc) r1 limit 10 offset ?1", nativeQuery = true)
	List<ClientVo> findAllByPagingAndClientName(int begin, String searchKeyword);
	@Query(value = "select r1.* from (select * from client where client_company like ?2 order by client_company asc) r1 limit 10 offset ?1", nativeQuery = true)
	List<ClientVo> findAllByPagingAndClientCompany(int begin, String searchKeyword);

	@Query(value = "select count(*) from client where client_name like ?", nativeQuery = true)
	int countByClientName(String searchKeyword);
	@Query(value = "select count(*) from client where client_company like ?", nativeQuery = true)
	int countByClientCompany(String searchKeyword);
	
	@Query(value = "select max(client_no) from client", nativeQuery = true)
	int findByMaxClientNo();
}
