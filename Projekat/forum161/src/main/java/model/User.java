package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class User implements Serializable {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}


	private static final long serialVersionUID = 1L;
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String phone;
	private Date registrationDate;
	private UserType role;
	private String username;
	private ArrayList<Message> messages;
	private ArrayList<SubForum> followedForums;
	private ArrayList<Theme> followedThemes;
	private ArrayList<Comment> followedComments;
	public ArrayList<Comment> getFollowedComments() {
		return followedComments;
	}

	public void setFollowedComments(ArrayList<Comment> followedComments) {
		this.followedComments = followedComments;
	}

	public ArrayList<Review> getReviews() {
		return reviews;
	}

	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}


	private ArrayList<Review> reviews;

	public ArrayList<Review> getRewivews() {
		return reviews;
	}

	public void setRewivews(ArrayList<Review> rewivews) {
		this.reviews = rewivews;
	}

	public User(){
		super();
		this.messages = new ArrayList<Message>();
		this.followedForums = new ArrayList<SubForum>();
		this.followedThemes = new ArrayList<Theme>();
		this.followedComments = new ArrayList<Comment>();
		this.reviews = new ArrayList<Review>();
	}
	
	

	public User(String email, String firstName, String lastName, String password, String phone, Date registrationDate,
			UserType role, String username, ArrayList<Message> messages, ArrayList<SubForum> followedForums, ArrayList<Theme> followedThemes, ArrayList<Comment> followedComments,  ArrayList<Review> reviews) {
		super();
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.phone = phone;
		this.registrationDate = registrationDate;
		this.role = role;
		this.username = username;
		this.messages = messages;
		this.followedForums = followedForums;
		this.followedThemes =  followedThemes;
		this.followedComments = followedComments;
		this.reviews= reviews;
	}

	
	public User updateUser(User u){
		
		this.email = u.email;
		this.firstName = u.firstName;
		this.lastName = u.lastName;
		this.password = u.password;
		this.phone = u.phone;
		this.registrationDate = u.registrationDate;
		this.role = u.role;
		this.username = u.username;
		this.messages = u.messages;
		this.followedForums = u.followedForums;
		this.followedThemes =  u.followedThemes;
		this.followedComments = u.followedComments;
		this.reviews=u.reviews;
		
		return this;
		
	}
	
	public User editUser(User u){
		
		this.email = u.email;
		this.firstName = u.firstName;
		this.lastName = u.lastName;
		this.password = u.password;
		this.phone = u.phone;
		return this;
		
	}
	
	public boolean addReview(Review r){
		
		if(r != null)
		//	System.out.println("Sta HOCES SADA ?");
		
	//	System.out.println(r.getId());
		
	//	System.out.println(reviews.size());
		
		for (Review re : reviews) {
			
		//	System.out.println(re.getId());
			
			if(re.getId().equals(r.getId()))
				if(re.getType()==r.getType())
					if(re.getForType()==r.getForType()){
			//			System.out.println("remove ga");
						this.reviews.remove(re);
						return false;
					}
		}
		//System.out.println("add ga :D");
		this.reviews.add(r);
		return true;
	}
	
	public ArrayList<Review> getLikes() {
		ArrayList<Review> likes = new ArrayList<Review>();
		
		for (Review r : reviews) {
			if(r.getType()==RewType.LIKE)
				likes.add(r);
		}
		
		return likes;
	}
	
	public ArrayList<Review> getDislikes() {
		ArrayList<Review> dislikes = new ArrayList<Review>();
		
		for (Review r : reviews) {
			if(r.getType()==RewType.DISLIKE)
				dislikes.add(r);
		}
		
		return dislikes;
	}
	
	public boolean addFollowedForum(SubForum sb){
		
		for (SubForum s : followedForums) {
			if(s.getName().equals(sb.getName()))
				return false;
		}
		//System.out.println("U useru dodaje followed Sub");
		followedForums.add(sb);
		return true;
	}
	
	
	public boolean addFollowedTheme(Theme t){
		
		//System.out.println("Usao sam u follow theme");
		
		for (Theme th : this.followedThemes) {
		//	System.out.println("Ove sadrzim : " +th.getTitle());
			if(th.getId().toString().equals(t.getId().toString()))
				return false;
		}
	
	//	System.out.println("Dodao sam je :D");
		this.followedThemes.add(t);
	
		
		
		return true;
	}
	
	public boolean addFollowedComment(Comment c){
		
		System.out.println("Usao sam u follow commenta");
		
		for (Comment cm : this.followedComments) {
			System.out.println(cm.getText());
			if(cm.getId().toString().equals(c.getId().toString()))
				return false;
		}
		System.out.println("Dodao sam ga 100%");
		this.followedComments.add(c);
		
		
		return true;
	}
	
	public void sendMessage(Message m){
	//	System.out.println("evo dodajem message");
	//	System.out.println(username);
		
		
		//System.out.println("u send message");
	//	System.out.println(m.getContent());
	//	System.out.println(m.getID().toString());
		messages.add(0,m);
	
	}
	
	
	public void sendFlag(String s){
	//	System.out.println("evo saljem flag");
		//System.out.println(username);
		
		Message m = new Message();
		m.setID(UUID.randomUUID());
		m.setContent(s);
		m.setIsFlag(true);
		m.setReceiverID(username);
		m.setSenderID("SYSTEM");
	//	System.out.println("u sendFlag");
		
		messages.add(0,m);
		
	}
	
	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}

	public ArrayList<SubForum> getFollowedForums() {
		return followedForums;
	}

	public void setFollowedForums(ArrayList<SubForum> followedForums) {
		this.followedForums = followedForums;
	}

	public ArrayList<Theme> getFollowedThemes() {
		return followedThemes;
	}

	public void setFollowedThemes(ArrayList<Theme> followedThemes) {
		this.followedThemes = followedThemes;
	}

	public void finalize() throws Throwable {

	}

	public String getEmail(){
		return email;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
	}

	public String getPassword(){
		return password;
	}

	public String getPhone(){
		return phone;
	}

	public Date getRegistrationDate(){
		return registrationDate;
	}

	public UserType getRole(){
		return role;
	}

	public String getUsername(){
		return username;
	}


	public void setEmail(String newVal){
		email = newVal;
	}


	public void setFirstName(String newVal){
		firstName = newVal;
	}

	public void setLastName(String newVal){
		lastName = newVal;
	}

	public void setPassword(String newVal){
		password = newVal;
	}

	public void setPhone(String newVal){
		phone = newVal;
	}

	public void setRegistrationDate(Date newVal){
		registrationDate = newVal;
	}

	public void setRole(UserType newVal){
		role = newVal;
	}

	public void setUsername(String newVal){
		username = newVal;
	}

}