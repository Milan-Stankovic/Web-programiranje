package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import exceptions.ExistingCommentException;
import exceptions.ExistingThemeException;
import exceptions.NoThemeException;

public class Theme implements Serializable, Comparable<Theme> {

	private static final long serialVersionUID = 1L;
	private String content;
	private Date dateOfCreation;
	private int dislikes;
	private int likes;
	private String title;
	private ThemeType type;
	private String subForum;
	private User author;
	private HashMap<String, Comment> comments;
	private UUID id;
	private boolean edit=true;
	private Comment recCom;
	private boolean flag= false; //Da li je pokrenut flag
	
	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public Comment recCom(){
		return recCom;
	}
	
	public void setRecCom(Comment rc){
		this.recCom= rc;
	}
	
	public boolean getEdit(){
		return edit;
	}

	
	public void setEdit(boolean b){
		this.edit = b;
	}
	
	public UUID getId() {
		return id;
	}


	public void setId(UUID id) {
		this.id = id;
	}


	public  HashMap<String, Comment> getComments() {
		return comments;
	}

	
	

	public Comment findComment(String id){
		
		if(comments.containsKey(id)){
			return comments.get(id);
		}else{
		//	System.out.println("U elsu za find Comment");
			ArrayList<Comment> myList = new ArrayList<>(comments.values());
			
			Comment c3 =  recursiveFind(myList, id);
			
		//	System.out.println("U temi comment");
			if(c3 != null){
		//System.out.println(c3.getText());
				return c3;
			}
				if(recCom != null){
		//			System.out.println(recCom.getText());
					return recCom;
			}
				return null;
		}
			
			
		
		
	}
	
	
	Comment recursiveFind(ArrayList<Comment> myList, String parentID)  {
	//	System.out.println("U rekurzivnom addu sam ");
	//	System.out.println("Parent id : " + parentID);
	//	System.out.println("Broj elementa u listi je : "+myList.size());
	//	System.out.println("prvi element u listi je : " + myList.get(0).getText());
	//	System.out.println("Njegov id je : " + myList.get(0).getId().toString());
		
	//System.out.println(recCom.getText());
			
		for (Comment c : myList) {
			
			if (c.getId().toString().equals(parentID)) {
			//	System.out.println("NASAO SAMMMM U 1 !!!!");
		//		System.out.println(comment.getId());
			//	System.out.println(comment.getLikes());
				//System.out.println(comment.getText());
				recCom=c;
				if(recCom!=null)
			//		System.out.println(recCom.getText());
				
				return c;
			}
			
			
				for (Comment comment : c.getComments()) {
				//	System.out.println("Poredi sa :" +comment.getId());
				//	System.out.println("tekst je :" +comment.getText());
				//	System.out.println(comment.getComments().size());
					if (comment.getId().toString().equals(parentID)) {
				//		System.out.println("NASAO SAMMMM U 2 !!!!");
					//	System.out.println(comment.getId());
					//	System.out.println(comment.getLikes());
					//	System.out.println(comment.getText());
						recCom=comment;
						return comment;
					}
					if (comment.getComments().size() > 0){ 
					//	System.out.println(comment.getText());
					//	System.out.println(comment.getComments().get(0).getText());
						recursiveFind(comment.getComments(), parentID);
						
					}
				}
			
		}
		return null;
	}
	
	
	
	public void addComment(Comment c) throws ExistingCommentException{
		
		if(!comments.containsKey(c.getId().toString())){
			comments.put(c.getId().toString(), c);
		}else
			throw new  ExistingCommentException();
		
	}
	
	public void setComments( HashMap<String, Comment> comments) {
		this.comments = comments;
	}

	public ThemeType getType() {
		return type;
	}

	public void setType(ThemeType type) {
		this.type = type;
	}

	public String getSubForum() {
		return subForum;
	}

	public void setForum(String forum) {
		this.subForum = forum;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public Theme(){
		comments= new HashMap<String, Comment>();
		id = UUID.randomUUID();
	}

	public void finalize() throws Throwable {

	}

	public String getContent(){
		return content;
	}

	public Date getDateOfCreation(){
		return dateOfCreation;
	}

	public int getDislikes(){
		return dislikes;
	}

	public int getLikes(){
		return likes;
	}

	public String getTitle(){
		return title;
	}

	public void setContent(String newVal){
		content = newVal;
	}

	public void setDateOfCreation(Date newVal){
		dateOfCreation = newVal;
	}

	public void setDislikes(int newVal){
		dislikes = newVal;
	}

	public void setLikes(int newVal){
		likes = newVal;
	}

	public void setTitle(String newVal){
		title = newVal;
	}

	
	public void removeComment(String c){
	//	System.out.println("Pa i u temi usao da ga brsie");
		for (Comment c1 : comments.values()) {
			if(c1.getId().toString().equals(c)){
				comments.remove(c1.getId().toString());
				return;
			}
		}
		
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
	
	public void editComment(Comment c, boolean b){
		
		if(comments.containsKey(c.getId())){ // nisam siguran kako ce ovo raditi proveriti ovde
			Comment c1 = comments.get(c.getId());
			c1.setDislikes(c.getDislikes());
			if(b){
				Date d = new Date();
				c1.setLastEdited(d);
			}
			c1.setLikes(c.getLikes());
			c1.setStatus(c.getStatus());
			c1.setText(c.getText());
			c1.setParentID(( c.getParentID()));
		}else{
		 Comment c1= findComment(c.getId().toString());
		 c1.setDislikes(c.getDislikes());
			if(b){
				Date d = new Date();
				c1.setLastEdited(d);
			}
			c1.setLikes(c.getLikes());
			c1.setStatus(c.getStatus());
			c1.setText(c.getText());
			c1.setParentID(( c.getParentID()));
		}
		
	}
	
	
	
	public void logicalDeleteComment(Comment c){
		
		if(comments.containsKey(c.getId())){ // nisam siguran kako ce ovo raditi proveriti ovde
			Comment c1 = comments.get(c.getId());
			if(c1.getStatus() == Status.AKTIVAN)
				c1.setStatus(Status.LOGICKIOBRISAN);
			else
				c1.setStatus(Status.AKTIVAN);
		}else{
			Comment c1= findComment(c.getId().toString());
			if(c1.getStatus() == Status.AKTIVAN)
				c1.setStatus(Status.LOGICKIOBRISAN);
			else
				c1.setStatus(Status.AKTIVAN);
			
		}
		
	}
	
	

	@Override
	public int compareTo(Theme o) {
		// TODO Auto-generated method stub
		return this.likes > o.likes ? 1 : (this.likes < o.likes ? -1 : 0);

	}

	
}
