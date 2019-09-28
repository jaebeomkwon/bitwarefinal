package com.bitgroupware.community.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bitgroupware.community.vo.NoticeFileVo;
import com.bitgroupware.community.vo.NoticeVo;

public interface NoticeFileRepository extends JpaRepository<NoticeFileVo, Integer>{

	List<NoticeFileVo> findByNotice(NoticeVo notice);
	
	@Query(value = "select count(*) from notice_file where nt_file_url = ?", nativeQuery = true)
	int countByNtFileUrl(String fileUrl);

	NoticeFileVo findByNtFileUrl(String fileUrl);

}
