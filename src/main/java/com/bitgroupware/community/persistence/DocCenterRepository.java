package com.bitgroupware.community.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.bitgroupware.community.vo.DocCenterVo;

public interface DocCenterRepository extends JpaRepository<DocCenterVo, Integer>, QuerydslPredicateExecutor<DocCenterVo>{

	@Query(value = "select r1.* from (select * from doc_center where doc_del_check = 'N' and doc_cate like ?2 order by doc_no desc) r1 limit 10 offset ?1", nativeQuery = true)
	List<DocCenterVo> findAllByPagingAndDocCate(int begin, String searchKeyword);
	@Query(value = "select r1.* from (select * from doc_center where doc_del_check = 'N' and doc_title like ?2 order by doc_no desc) r1 limit 10 offset ?1", nativeQuery = true)
	List<DocCenterVo> findAllByPagingAndDocTitle(int begin, String searchKeyword);
	@Query(value = "select r1.* from (select * from doc_center where doc_del_check = 'N' and doc_content like ?2 order by doc_no desc) r1 limit 10 offset ?1", nativeQuery = true)
	List<DocCenterVo> findAllByPagingAndDocContent(int begin, String searchKeyword);

	@Query(value = "select count(*) from doc_center where doc_del_check = 'N' and doc_title like ?", nativeQuery = true)
	int countByDocTitle(String searchKeyword);
	@Query(value = "select count(*) from doc_center where doc_del_check = 'N' and doc_cate like ?", nativeQuery = true)
	int countByDocCate(String searchKeyword);
	@Query(value = "select count(*) from doc_center where doc_del_check = 'N' and doc_content like ?", nativeQuery = true)
	int countByDocContent(String searchKeyword);
	
	@Query(value = "select max(doc_no) from doc_center", nativeQuery = true)
	int findByMaxDocNo();
}
