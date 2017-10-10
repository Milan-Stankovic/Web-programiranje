package model;

import java.io.Serializable;

public class Flag implements Serializable{

	private User sender;
	private String message;
	private SubForum subForum;
	private Theme theme;
	private Comment comment;
	
	public Flag(){
		sender=null;
		message = "";
		subForum=null;
		theme=null;
		comment=null;
	}
	
	public User getSender() {
		return sender;
	}
	public void setSender(User sender) {
		this.sender = sender;
	}
	public SubForum getSubForum() {
		return subForum;
	}
	public void setSubForum(SubForum subForum) {
		this.subForum = subForum;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Theme getTheme() {
		return theme;
	}
	public void setTheme(Theme theme) {
		this.theme = theme;
	}
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	
}
