package com.bitgroupware.chat.controller;



import static java.lang.String.format;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.bitgroupware.chat.Beans.ChatMessageDto;
import com.bitgroupware.chat.Beans.ChatMessageDto;
import com.bitgroupware.chat.Beans.ChatMessageDto.MessageType;

@Component
public class WebSocketEventListener {
	private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
	
	@Autowired
	private SimpMessageSendingOperations messagingTemplate;
	
	@EventListener
	public void handleWebSocketDisconnectListener (SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		
		String username = (String) headerAccessor.getSessionAttributes().get("username");
		String roomId = (String) headerAccessor.getSessionAttributes().get("room_id");
		if(username != null) {
			logger.info("User Disconnected: " + username);
			
			ChatMessageDto message = new ChatMessageDto();
			message.setMessageType(MessageType.LEAVE);
			message.setSender(username);
			
			messagingTemplate.convertAndSend(format("/channel/%s", roomId), message);
		}
	}
}
