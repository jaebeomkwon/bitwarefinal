package com.bitgroupware.community.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.bitgroupware.community.vo.NoticeVo;

public interface NoticeRepository extends JpaRepository<NoticeVo, Integer>, QuerydslPredicateExecutor<NoticeVo>{

	@Query(value = "select r1.* from (select * from notice where nt_del_check = 'N' and nt_cate like ?2 order by nt_no desc) r1 limit 10 offset ?1", nativeQuery = true)
	List<NoticeVo> findAllByPagingAndNtCate(int begin, String searchKeyword);
	@Query(value = "select r1.* from (select * from notice where nt_del_check = 'N' and nt_title like ?2 order by nt_no desc) r1 limit 10 offset ?1", nativeQuery = true)
	List<NoticeVo> findAllByPagingAndNtTitle(int begin, String searchKeyword);
	@Query(value = "select r1.* from (select * from notice where nt_del_check = 'N' and nt_content like ?2 order by nt_no desc) r1 limit 10 offset ?1", nativeQuery = true)
	List<NoticeVo> findAllByPagingAndNtContent(int begin, String searchKeyword);

	@Query(value = "select count(*) from notice where nt_del_check = 'N' and nt_title like ?", nativeQuery = true)
	int countByNtTitle(String searchKeyword);
	@Query(value = "select count(*) from notice where nt_del_check = 'N' and nt_cate like ?", nativeQuery = true)
	int countByNtCate(String searchKeyword);
	@Query(value = "select count(*) from notice where nt_del_check = 'N' and nt_content like ?", nativeQuery = true)
	int countByNtContent(String searchKeyword);
	
	@Query(value = "select max(nt_no) from notice", nativeQuery = true)
	int findByMaxNtNo();
	
	@Query(value = "select * from notice where nt_del_check = 'N' order by nt_date desc limit 5", nativeQuery = true)
	List<NoticeVo> findTop5ByOrderByNtDateDesc();
}
