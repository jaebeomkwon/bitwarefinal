package com.bitgroupware.chat.Beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ChatAlertDto {

	private int alertNo;
	private String sender;
	private String receiver;
	private String roomId;
}
