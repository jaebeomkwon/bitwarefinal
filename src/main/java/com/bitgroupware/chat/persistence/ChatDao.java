package com.bitgroupware.chat.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.bitgroupware.chat.Beans.ChatAlertDto;
import com.bitgroupware.chat.Beans.ChatMessageDto;
import com.bitgroupware.chat.Beans.DepartmentDto;
import com.bitgroupware.chat.Beans.MemberDto;

@Mapper
public interface ChatDao {
	
	//직원불러오기
	//@Select("SELECT M.MEM_ID, M.MEM_NAME, D.DEPT_NAME FROM MEMBER AS M JOIN DEPARTMENT AS D ON M.DEPT_NAME = D.DEPT_NAME")
	@Select("SELECT * FROM MEMBER")
	List<MemberDto> selectMemberList();
	
	//부서명불러오기
	@Select("SELECT * FROM DEPARTMENT")
	List<DepartmentDto> selectDepList();
	
	@Select("SELECT * FROM MEMBER WHERE MEM_ID = #{memId}")
	public MemberDto selectMemeberInfo(String memId);
	
	@Insert("INSERT INTO CHAT_MESSAGE (SENDER, CONTENT, RECEIVER, ROOM_ID) VALUES (#{sender},#{content},#{receiver},#{roomId})")
	void insertChat(ChatMessageDto chatDto);
	
	//@Select("select max(message_id), sender, content, receiver, room_id from chat_message group by room_id having receiver = #{receiver}")
//	@Select("select max(message_id), sender, content, receiver, room_id from chat_message group by room_id")
	@Select("select max(message_id), sender, content, receiver, room_id from chat_message where sender = #{sender} group by room_id")
	List<ChatMessageDto> selectLastContentList(String sender);
	//public ChatMessageDto selectLastContentList(String receiver);
	
//	@Select("select * from member where dept_name = #{deptName}")
//	@Select("SELECT M.*, R.RANKS_NO, C.* FROM MEMBER AS M JOIN RANKS AS R ON M.RANKS = R.RANKS join chat_message as c on m.mem_name = c.sender WHERE DEPT_NAME = #{deptName} ORDER BY R.RANKS_NO DESC;")
	@Select("SELECT M.*, R.RANKS_NO FROM MEMBER AS M JOIN RANKS AS R ON M.RANKS = R.RANKS WHERE DEPT_NAME = #{deptName} ORDER BY R.RANKS_NO DESC")
	List<MemberDto> selectMemberListByDepartment(String deptName);
	
	@Select("select * from chat_message where room_id = #{roomId}")
	List<ChatMessageDto> selectChatMessageList(String roomId);
	
	@Select("select content from chat_message where room_id = #{roomId} order by message_id desc limit 1")
	String selectChatMessageListByRoomId(String roomId);

	@Insert("insert into chat_alert (sender, receiver, room_id) values(#{sessionId},#{memId},#{roomId})")
	void insertChatAlert(String sessionId, String memId, String roomId);

	@Select("select * from chat_alert where receiver = #{receiver}")
	List<ChatAlertDto> checkChatAlert(String receiver);

	@Delete("delete from chat_alert where receiver = #{sessionId} and room_id = #{roomId}")
	void deleteChatAlert(String sessionId, String roomId);

	@Select("select count(*) from chat_alert where sender = #{memId} and room_id = #{roomId}")
	int countChatAlert(String memId, String roomId);
}
