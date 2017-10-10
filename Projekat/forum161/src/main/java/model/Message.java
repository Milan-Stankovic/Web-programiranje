package model;

import java.io.Serializable;
import java.util.UUID;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String content;
	private boolean read=false;
	private String senderID;
	private boolean isFlag=false;
	private String receiverID;
	private boolean reply=false;
	private UUID id;

	public boolean  getIsFlag(){
		return isFlag;
	}
	public void setIsFlag(boolean b){
		this.isFlag = b;
	}
	
	public String getSenderID() {
		return senderID;
	}

	public void setSenderID(String sender) {
		this.senderID = sender;
	}

	public String getReceiverID() {
		return receiverID;
	}

	public void setReceiverID(String receiver) {
		this.receiverID = receiver;
	}

	public Message(){
		
	}

	public void finalize() throws Throwable {

	}

	public String getContent(){
		return content;
	}

	public boolean getRead(){
		return read;
	}

	public void setContent(String newVal){
		content = newVal;
	}

	public void setRead(boolean newVal){
		read = newVal;
	}
	public boolean getReply() {
		return reply;
	}
	public void setReply(boolean reply) {
		this.reply = reply;
	}
	public UUID getID() {
		return id;
	}
	public void setID(UUID id) {
		this.id = id;
	}

}
