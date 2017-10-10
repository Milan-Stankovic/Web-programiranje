package files;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;
import java.util.UUID;

import exceptions.NoSuchUsernameException;
import exceptions.UsernameExistsException;
import model.Comment;
import model.Message;
import model.Review;
import model.SubForum;
import model.Theme;
import model.User;
import model.UserObserver;
import model.UserType;

public class Users extends Observable {

	private HashMap<String,User> active; // NEMAM MI SMISLA OVO ZBOG NACINA NA KOJI SAM KORISTIO INSTANTIATE
	private HashMap<String,User> allUsers;
	private static Users instance = null;
	
	private Users(){
		active = new HashMap<String,User>();
		HashMap<String,User> load = (HashMap<String,User>)FileUtility.ReadObject(FileType.User);
		if(load == null){
			allUsers = new HashMap<String,User>();
			User admin = new User();
			admin.setPhone("000");
			admin.setEmail("admin@admin.com");
			admin.setFirstName("Admin");
			admin.setLastName("Admin");
			admin.setRole(UserType.ADMIN);
			admin.setUsername("admin");
			admin.setPassword("admin");
			
			Date d = new Date();
			admin.setRegistrationDate(d);
			allUsers.put(admin.getUsername(), admin);
		}
		else
			setAllUsers((HashMap<String,User>)FileUtility.ReadObject(FileType.User));
		this.addObserver(new UserObserver());
	}
	
	public static Users Instance()
	{
		if(instance == null)
			instance = new Users();
		return instance;
	}
	
	public ArrayList<SubForum> getFollowedSubForums(String id){
		
		if(allUsers.containsKey(id)){
			return	allUsers.get(id).getFollowedForums();
		}
		
		return null;
	}
	
	
	public ArrayList<Review> getReviews(String id){
		
		if(allUsers.containsKey(id)){
			return	allUsers.get(id).getReviews();
		}
		
		return null;
	}
	
	
	public ArrayList<Theme> getFollowedThemes(String id){
		
		if(allUsers.containsKey(id)){
			return	allUsers.get(id).getFollowedThemes();
		}
		
		return null;
	}
	
	public ArrayList<Comment> getFollowedComments(String id){
		
		if(allUsers.containsKey(id)){
			return	allUsers.get(id).getFollowedComments();
		}
		
		return null;
	}
	
	
	
	public User addAdmin(User u){
		u.setRole(UserType.ADMIN);
		setChanged();
		notifyObservers(allUsers);
		return u;
	}
	
	public User addMod(User u){
		u.setRole(UserType.MODERATOR);
		setChanged();
		notifyObservers(allUsers);
		return u;
	}
	
	public User removeTitle(User u){
		u.setRole(UserType.REGULAR);
		setChanged();
		notifyObservers(allUsers);
		return u;
	}
	
	
	
	
	
	public User updateUser(User u){
		
		User us=null;
		
		if(allUsers.containsKey(u.getUsername()))
			 us =allUsers.get(u.getUsername()).updateUser(u);
		
		setChanged();
		notifyObservers(allUsers);
		return us;
	}
	
	public Message readMessage(Message m){
		
		boolean b = false;
		if(allUsers.containsKey(m.getReceiverID())){
			ArrayList<Message> alm =  allUsers.get(m.getReceiverID()).getMessages();
			for (Message message : alm) {
				if(message.getID().toString().equals(m.getID().toString())){
					message.setRead(true);
					setChanged();
					notifyObservers(allUsers);
					b=true;
				}
			}
		}
		
		if(allUsers.containsKey(m.getSenderID())){
			ArrayList<Message> alm =  allUsers.get(m.getSenderID()).getMessages();
			for (Message message : alm) {
				if(message.getID().toString().equals(m.getID().toString())){
					message.setRead(true);
					setChanged();
					notifyObservers(allUsers);
					b=true;
				}
			}
		}
		if(b)
			return m;
		
		
		return null;
	}
	
	public Message sendMessage(String reciverID, String senderID, String text){
		
		if(allUsers.containsKey(reciverID)){
		
			if(allUsers.containsKey(senderID)){
				
			//	System.out.println("Nasao usere u send message");
				
				Message m1 = new Message();
				m1.setContent(text);
				m1.setReceiverID(reciverID);
				m1.setSenderID(senderID);
				m1.setID(UUID.randomUUID());
				allUsers.get(reciverID).sendMessage(m1);
				allUsers.get(senderID).sendMessage(m1);
				setChanged();
				notifyObservers(allUsers);
				return m1;
			}
		
		}
		
		
		return null;
	}
	
public Message respondToFlag(String reciverID, String senderID, String text){
		
		if(allUsers.containsKey(reciverID)){
		
			if(allUsers.containsKey(senderID)){
				
			//	System.out.println("Nasao usere u send message");
				
				Message m1 = new Message();
				m1.setContent(text);
				m1.setReceiverID(reciverID);
				m1.setSenderID(senderID);
				m1.setID(UUID.randomUUID());
				allUsers.get(reciverID).sendMessage(m1);
				setChanged();
				notifyObservers(allUsers);
				return m1;
			}
		
		}
		
		
		return null;
	}
	
	
	
	public User editUser(User u){
		
		User us=null;
		
		if(allUsers.containsKey(u.getUsername()))
			 us =allUsers.get(u.getUsername()).editUser(u);
		
		setChanged();
		notifyObservers(allUsers);
		return us;
	}
	
	/*public void AddActiveUsers(User u) throws LoggedInException, NoSuchUsernameException, WrongPasswordException, NoUsersException
	{
		if(allUsers.isEmpty())
			throw new NoUsersException();
		else
			if(!active.containsKey(u.getUsername()))
				if(allUsers.containsKey(u.getUsername()))
					if(u.getPassword().equals(allUsers.get(u.getUsername()).getPassword()))
						active.put(u.getUsername(), u);
					else
						throw new WrongPasswordException();
				else
					throw new NoSuchUsernameException();
			else
				throw new LoggedInException();
	}
	*/
	
	public Collection<User> getActive() {
		return active.values();
	}
	
	public boolean followTheme(User u, Theme t){
		
		if(allUsers.containsKey(u.getUsername())){
			boolean b= 	allUsers.get(u.getUsername()).addFollowedTheme(t);
			setChanged();
			notifyObservers(allUsers);
			System.out.println("U Userima updejtuje");
			return b;
		}
		return false;
	}
	
	
	public boolean followForum(User u, SubForum sf){
		
		if(allUsers.containsKey(u.getUsername())){
			boolean b= 	allUsers.get(u.getUsername()).addFollowedForum(sf);
			setChanged();
			notifyObservers(allUsers);
			return b;
			
		}
		return false;
	}
	

	public boolean followComment(String u, String subid, String themid, String commid){
		
		if(allUsers.containsKey(u)){
			
			for (SubForum su : Forum.Instance().getAllSubForums()) {
				if(su.getName().equals(subid)){
					if(su.getThemes().containsKey(themid)){
						System.out.println("Nasao komentar i dodace ga");
						Comment c= su.getThemes().get(themid).findComment(commid);
						boolean b= 	allUsers.get(u).addFollowedComment(c);
						setChanged();
						notifyObservers(allUsers);
						return b;
					}
				}
			}
			
			
		}
		return false;
	}
	
	
	
	public Collection<User> getAllUsers() {
		Collection<User> retVal = null;
		try{
			retVal  = new ArrayList<User>(allUsers.values());
		}catch(NullPointerException e)
		{
			retVal = new ArrayList<User>();
		}
		return retVal;
	}

	public void setAllUsers(HashMap<String,User> allUsers) {
		this.allUsers = allUsers;

	}
	
	public void addNewUser(User u) throws UsernameExistsException
	{
		if(!allUsers.containsKey(u.getUsername()))
		{
			allUsers.put(u.getUsername(), u);
			setChanged();
			notifyObservers(allUsers);
		}else
			throw new UsernameExistsException();
	}
	
	
	public void removeActiveUser(User u) throws NoSuchUsernameException
	{
		if(active.containsKey(u.getUsername()))
		{
			active.remove(u.getUsername());
			setChanged();
			notifyObservers(allUsers);
		}else
			throw new NoSuchUsernameException();
			
	}
	
	
	
	public void removeUser(User u) throws NoSuchUsernameException
	{
		if(allUsers.containsKey(u.getUsername()))
		{
			allUsers.remove(u.getUsername());
			setChanged();
			notifyObservers(allUsers);
		}else
			throw new NoSuchUsernameException();
			
	}
	

	public Collection<User> getNormalActiveUsers()
	{
		ArrayList<User> normActUsers= new ArrayList<User>();
		for (User u : active.values()) {
			if(u.getRole() == UserType.REGULAR)
			normActUsers.add(u);
		}
		return normActUsers;
	}
	
	public Collection<User> getActiveAdmins()
	{
		ArrayList<User> adminActUsers= new ArrayList<User>();
		for (User u : active.values()) {
			if(u.getRole() == UserType.ADMIN)
				adminActUsers.add(u);
		}
		return adminActUsers;
	}
	
	public Collection<User> getActiveModerators()
	{
		ArrayList<User> modActUsers= new ArrayList<User>();
		for (User u : active.values()) {
			if(u.getRole() == UserType.ADMIN)
				modActUsers.add(u);
		}
		return modActUsers;
	}
	
	public Collection<User> getNormalExistingUsers()
	{
		ArrayList<User> normExtUsers= new ArrayList<User>();
		for (User u : allUsers.values()) {
			if(u.getRole() == UserType.REGULAR)
				normExtUsers.add(u);
		}
		return normExtUsers;
	}
	
	public Collection<User> getExistingAdmins()
	{
		ArrayList<User> adminExtUsers= new ArrayList<User>();
		for (User u : allUsers.values()) {
			if(u.getRole() == UserType.ADMIN)
				adminExtUsers.add(u);
		}
		return adminExtUsers;
	}
	
	public Collection<User> getExistingModerators()
	{
		ArrayList<User> modExtUsers= new ArrayList<User>();
		for (User u : allUsers.values()) {
			if(u.getRole() == UserType.ADMIN)
				modExtUsers.add(u);
		}
		return modExtUsers;
	}
	
	public boolean addReview (User u, Review r ){
		
		if(!allUsers.containsKey(u.getUsername()))
			return false;
		
		boolean b =  u.addReview(r);
		
			setChanged();
			notifyObservers(allUsers);
		
			
		return b;
	}
	
	public ArrayList<Review> getLikes(User u){
		
		if(allUsers.containsKey(u.getUsername())){
			User u1 =allUsers.get(u.getUsername());
			return u1.getLikes();
		}
		return null;
		
	}
	
	
	public ArrayList<Review> getDislikes(User u){
		
		if(allUsers.containsKey(u.getUsername())){
			User u1 =allUsers.get(u.getUsername());
			return u1.getDislikes();
		}
		return null;
		
	}

	
	
}
