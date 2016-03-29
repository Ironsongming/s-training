package com.csm.straining.common.socket.netkit.message;


/**
 * @author chensongming
 */
public class Message {
	
	public static final int HEADER_SIZE = 12;
	public static final int MESSAGEID_SERVER_MAX_CONNECTIONS = 0;
	private int messageID;
	private int version;
	private byte[] messageContent;
	
	public Message() {
		
	}
	
	public Message(int messageID) {
		this.messageID = messageID;
	}

	public Message(int messageID, int version) {
		this.messageID = messageID;
		this.version = version;
	}

	public Message(int messageID, int version, byte[] messageContent) {
		this.messageID = messageID;
		this.version = version;
		this.messageContent = messageContent;
	}

	public int getMessageID() {
		return messageID;
	}

	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public byte[] getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(byte[] messageContent) {
		this.messageContent = messageContent;
	}
	
	public String getMessageContentAsString() {
		if (this.messageContent == null) {
			return null;
		}
		
		return new String(this.messageContent);
	}
	
	public int getPacketSize() {
		if (this.messageContent == null) {
			return 12;
		}
		return 12 + this.messageContent.length;
	}
	
	public int getContentLength() {
		if (this.messageContent == null) {
			return 0;
		}
		return this.messageContent.length;
	}
	
	
	
	

}
