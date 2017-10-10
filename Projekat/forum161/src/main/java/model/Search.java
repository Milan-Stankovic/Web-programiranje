package model;

import java.io.Serializable;
import java.util.ArrayList;

import files.Forum;
import files.Users;

public class Search implements Serializable {

	private ArrayList<Theme> themes;
	private ArrayList<SubForum> subForums;
	private ArrayList<User> users;
	public ArrayList<Theme> getThemes() {
		return themes;
	}
	public void setThemes(ArrayList<Theme> themes) {
		this.themes = themes;
	}
	public ArrayList<SubForum> getSubForums() {
		return subForums;
	}
	public void setSubForums(ArrayList<SubForum> subForums) {
		this.subForums = subForums;
	}
	public ArrayList<User> getUsers() {
		return users;
	}
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
	public Search() {
		super();
		this.themes= new ArrayList<Theme>();
		this.subForums = new ArrayList<SubForum>();
		this.users = new ArrayList<User>();
		
	}
	public Search(ArrayList<Theme> themes, ArrayList<SubForum> subForums, ArrayList<User> users) {
		super();
		this.themes = themes;
		this.subForums = subForums;
		this.users = users;
	}
	
	public Search doSearch(String s){
		
		ArrayList<Theme> allThemes = new ArrayList<Theme>();
		ArrayList<SubForum> allSubForums = (ArrayList<SubForum>) Forum.Instance().getAllSubForums();
		ArrayList<User> allUsers = (ArrayList<User>) Users.Instance().getAllUsers();
		for (SubForum sub : allSubForums) {
			for (Theme	t : sub.getThemes().values()) {
				allThemes.add(t);
			}
		}
		
		for (User user : allUsers) {
			if(user.getUsername().contains(s)){
				users.add(user);
			}
		}
		
		for (SubForum sub : allSubForums) {
			if(sub.getDescription().contains(s)){
				subForums.add(sub);
				continue;
			}
			if(sub.getName().contains(s)){
				subForums.add(sub);
				continue;
			}
			if(sub.getMainModerator().getUsername().contains(s)){
				subForums.add(sub);
				continue;
			}
			
		}
		
		for (Theme t : allThemes) {
			if(t.getContent().contains(s)){
				themes.add(t);
				continue;}
			if(t.getTitle().contains(s)){
				themes.add(t);
				continue;}
			if(t.getAuthor().getUsername().contains(s)){
				themes.add(t);
				continue;}
			if(t.getSubForum().contains(s)){
				themes.add(t);
				continue;}
		}
		
		
		return this;
	}
	
}
