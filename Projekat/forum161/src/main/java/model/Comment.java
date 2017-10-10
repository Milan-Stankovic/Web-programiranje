package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;

import exceptions.ExistingCommentException;

public class Comment  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Date dateOfCreation;
	private int dislikes;
	private Date lastEdited;
	private int likes;
	private String text;
	private User author;
	private Status status;
	private UUID id;

	private CommentType type;
	private String parentID;
	private ArrayList<Comment> comments;
	private boolean edit=true; // za edit polje
	private boolean changed=false; // ako je neko izmenio svoj komentar :D
	private boolean reply = false; // da vidim da li je kliknut reply
	private boolean viewAll = false; // da vidim sve 
	private boolean flag= false; //Da li je pokrenut flag
	
	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
	public boolean getViewAll(){
		return viewAll;
	}
	
	public void setViewAll(boolean b){
		this.viewAll=b;
	}
	
	public boolean getReply(){
		return reply;
	}
	
	public void setReply(boolean b){
		this.reply = b;
	}
	
	public void addComment(Comment c) throws ExistingCommentException{
		
		for (Comment comment : comments) {
			if(comment.getId().toString().equals(c.getId().toString()))
				throw new ExistingCommentException();
		}
		
		comments.add(c);
	}
	
	public boolean getChanged(){
		return changed;
	}
	
	public void setChanged(boolean b){
		this.changed = b;
	}
	
	
	
	public boolean getEdit(){
		return edit;
	}
	
	public void setEdit(boolean b){
		this.edit = b;
	}
	
	
	
	
	
	public ArrayList<Comment> getComments(){
		return comments;
	}
	
	public void setComments(ArrayList<Comment> com){
		this.comments = com;
	} 
	
	public Comment findComment(String id){
		
		for (Comment comment : comments) {
			if(comment.getId().toString().equals(id))
				return comment;
		}
		
		return null;
	}
	
	public String getParentID(){
		return parentID;
	}
	
	public CommentType getCommentType(){
		return type;
	}
	
	public void setCommentType(CommentType t){
		this.type=t;
	}
	
	public void setParentID(String id){
		this.parentID = id;
	}



	public void setId(UUID id) {
		this.id = id;
	}


	public Status getStatus(){
		return status;
	}
	
	public void setStatus(Status s){
		this.status=s;
	}
	
	
	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	
	public Comment(){
		id= UUID.randomUUID();
	}
	
	public void setUUID(UUID u){
		this.id=u;
	}
	public UUID getId(){
		return id;
	}

	public void finalize() throws Throwable {

	}
	
	public void minusLike(){
		this.likes = this.likes-1;
	}
	
	public void plusLike(){
		this.likes = this.likes+1;
	}
	
	public void plusDislike(){
		this.dislikes = this.dislikes+1;
	}
	
	public void minusDislike(){
		this.dislikes = this.dislikes -1;
	}

	public Date getDateOfCreation(){
		return dateOfCreation;
	}

	public int getDislikes(){
		return dislikes;
	}

	public Date getLastEdited(){
		return lastEdited;
	}

	public int getLikes(){
		return likes;
	}

	public String getText(){
		return text;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setDateOfCreation(Date newVal){
		dateOfCreation = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setDislikes(int newVal){
		dislikes = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setLastEdited(Date newVal){
		lastEdited = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setLikes(int newVal){
		likes = newVal;
	}

	/**
	 * 
	 * @param newVal
	 */
	public void setText(String newVal){
		text = newVal;
	}
	

}
