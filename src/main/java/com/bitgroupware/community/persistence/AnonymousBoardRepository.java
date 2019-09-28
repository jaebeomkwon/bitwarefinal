package com.bitgroupware.community.persistence;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.bitgroupware.community.vo.AnonymousBoardVo;
import com.bitgroupware.community.vo.NoticeVo;

public interface AnonymousBoardRepository extends JpaRepository<AnonymousBoardVo, Integer> {

	@Query(value = "select count(*) from anonymous_board where btitle like ?", nativeQuery = true)
	int countByBtitle(String searchKeyword);
	@Query(value = "select count(*) from anonymous_board where bcontent like ?", nativeQuery = true)
	int countByBcontent(String searchKeyword);
	
	@Query(value = "select r1.* from (select * from anonymous_board where btitle like ?2 order by bgroup desc, bstep asc) r1 limit 10 offset ?1", nativeQuery = true)
	List<AnonymousBoardVo> findAllByPagingAndBtitle(int begin, String searchKeyword);
	@Query(value = "select r1.* from (select * from anonymous_board where bcontent like ?2 order by bgroup desc, bstep asc) r1 limit 10 offset ?1", nativeQuery = true)
	List<AnonymousBoardVo> findAllByPagingAndBcontent(int begin, String searchKeyword);
	
	AnonymousBoardVo findByBnoAndBpw(int bno, String bpw);

	@Query(value = "select ifnull(max(bno),0) from anonymous_board", nativeQuery = true)
	int selectMaxBno();
	
	@Modifying
	@Transactional
	@Query(value = "update anonymous_board set bstep = bstep + 1 where bgroup = ?1 and bstep > ?2", nativeQuery = true)
	void replyShape(int bgroup, int bstep);

	@Modifying
	@Transactional
	@Query(value = "alter table anonymous_board auto_increment=0", nativeQuery = true)
	void initAutoIncrement();

}
