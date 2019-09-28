package com.bitgroupware.chat.Beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatMessageDto {
	public enum MessageType {
	    CHAT, JOIN, LEAVE
	  }

	  private MessageType messageType;
	  private String content;
	  private String sender;
	  private String receiver;
	  private String roomId;
	  
}
