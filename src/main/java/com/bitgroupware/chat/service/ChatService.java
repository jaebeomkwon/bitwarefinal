package com.bitgroupware.chat.service;

import java.util.List;

import com.bitgroupware.chat.Beans.ChatAlertDto;
import com.bitgroupware.chat.Beans.ChatMessageDto;
import com.bitgroupware.chat.Beans.DepartmentDto;
import com.bitgroupware.chat.Beans.MemberDto;

public interface ChatService {
	
	List<MemberDto> selectMemberList(MemberDto memberDto);
	
	List<DepartmentDto> selectDeptList(DepartmentDto depDto);
	
	void insertChat(ChatMessageDto chatDto);
	
    public MemberDto selectMemeberInfo(String memId); 

    //public ChatMessageDto selectLastContentList(String receiver);
    List<ChatMessageDto> selectLastContentList(String sender);

	List<DepartmentDto> selectDepartureList();

	List<MemberDto> selectMemberListByDepartmentAjax(String deptName);

	List<ChatMessageDto> selectChatMessageList(String roomId);

	String selectChatMessageListByRoomId(String roomId);

	void insertChatAlert(String sessionId, String memId, String roomId);

	List<ChatAlertDto> checkChatAlert(String receiver);

	void deleteChatAlert(String sessionId, String roomId);

	int countChatAlert(String memId, String roomId);

}
