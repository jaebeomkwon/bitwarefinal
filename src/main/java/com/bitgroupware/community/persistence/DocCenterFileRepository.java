package com.bitgroupware.community.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bitgroupware.community.vo.DocCenterFileVo;
import com.bitgroupware.community.vo.DocCenterVo;

public interface DocCenterFileRepository extends JpaRepository<DocCenterFileVo, Integer>{

	List<DocCenterFileVo> findByDocCenter(DocCenterVo docCenter);
	
	@Query(value = "select count(*) from doc_center_file where doc_file_url = ?", nativeQuery = true)
	int countByDocFileUrl(String fileUrl);

	DocCenterFileVo findByDocFileUrl(String fileUrl);
}
