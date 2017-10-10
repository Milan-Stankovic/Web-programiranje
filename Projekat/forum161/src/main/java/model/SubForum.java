package model;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import exceptions.ExistingThemeException;
import exceptions.NoThemeException;

public class SubForum implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String description;
	private Image icon;
	private String name;
	private String rules;

	private HashMap<String,User> moderators;
	private User mainModerator;
	
	private HashMap<String, Theme> themes;
	private boolean edit=true;
	private boolean flag= false; //Da li je pokrenut flag
	private boolean addMod = false;
	
	public boolean getAddMod(){
		return addMod;
	}
	
	public void setAddMod(boolean b){
		this.addMod=b;
	}
	
	public boolean getFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
	public boolean getEdit(){
		return edit;
	}
	
	public void setEdit(boolean b){
		this.edit = b;
	}
	
	
	
	public  HashMap<String, Theme> getThemes() {
		return themes;
	}

	public void setThemes( HashMap<String, Theme> themes) {
		this.themes = themes;
	}
	
	
	public Theme findTheme(String s){
		
		if(themes.containsKey(s))
			return themes.get(s);
		return null;
		
	}
	
	
	
	public void addTheme(Theme t) throws ExistingThemeException{
		
		 themes.put(t.getId().toString(), t);

	}
	
	public void removeTheme(Theme t) throws NoThemeException{
		
		if(!themes.containsKey(t.getId().toString()))
			throw new NoThemeException();
		themes.remove(t.getId().toString());

	}
	
	public void editTheme(Theme t) throws NoThemeException, ExistingThemeException {
		
		this.removeTheme(t);
		this.addTheme(t);

	}
	
	

	public HashMap<String,User> getModerators() {
		return moderators;
	}

	public void setModerators(HashMap<String,User> moderators) {
		this.moderators = moderators;
	}
	
	public boolean addModerator(User m){
		
		if(moderators.containsKey(m.getUsername())){
			moderators.put(m.getUsername(), m);
			return true;
		}
		return false;
		
	}
	
	public boolean removeModerator(User m){
		
		if(moderators.containsKey(m.getUsername())){
			moderators.remove(m.getUsername());
			return true;
		}
		return false;
	}
	

	public User getMainModerator() {
		return mainModerator;
	}

	public void setMainModerator(User mainModerator) {
		this.mainModerator = mainModerator;
	}

	public SubForum(){

	}

	public void finalize() throws Throwable {

	}

	public String getDescription(){
		return description;
	}

	public Image getIcon(){
		return icon;
	}

	public String getName(){
		return name;
	}

	public String getRules(){
		return rules;
	}

	
	public void setDescription(String newVal){
		description = newVal;
	}


	public void setIcon(Image newVal){
		icon = newVal;
	}


	public void setName(String newVal){
		name = newVal;
	}

	
	public void setRules(String newVal){
		rules = newVal;
	}

	

}
