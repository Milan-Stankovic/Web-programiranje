package javaBean;

import java.util.ArrayList;

public class Korisnik {

	private ArrayList<Racun> racuni;
	private String userName;
	private String password;
	public ArrayList<Racun> getRacuni() {
		return racuni;
	}
	public void setRacuni(ArrayList<Racun> racuni) {
		this.racuni = racuni;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Korisnik(ArrayList<Racun> racuni, String userName, String password) {
		super();
		this.racuni = racuni;
		this.userName = userName;
		this.password = password;
	}
	public Korisnik(){
		racuni= new ArrayList<Racun>();
	}
	public boolean addRacun(Racun r){
		
		for(Racun rac : racuni){
			if(rac.getBrojRacuna()== r.getBrojRacuna())
				return false;
		}
		racuni.add(r);
		return true;
	}
	public int brojRacuna(){
		
		return racuni.size();
	}
	
	
	
}
