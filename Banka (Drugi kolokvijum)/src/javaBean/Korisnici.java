package javaBean;

import java.util.ArrayList;

public class Korisnici {

	private ArrayList<Korisnik> korisnici;

	public ArrayList<Korisnik> getKorisnici() {
		return korisnici;
	}

	public void setKorisnici(ArrayList<Korisnik> korisnici) {
		this.korisnici = korisnici;
	}
	
	public Korisnici(){
		korisnici= new ArrayList<Korisnik>();
	}
	
	public Korisnik checkForm(String userName, String password){
		
		for(Korisnik k : korisnici){
			
			if(k.getUserName().equals(userName)){
				
				if(k.getPassword().equals(password))
					return k;
				return null;
			}
			
		}
		
		return null;
	}
	
	public Korisnik findRacun(int broj){
		
		for(Korisnik k : korisnici){
			
			for(Racun r : k.getRacuni()){
				if(r.getBrojRacuna()==broj){
					return k;
				}
			}
			
		}
		
		return null;
		
	}
	public boolean addUser(Korisnik kor){
		
		for(Korisnik k : korisnici){
			if(k.getUserName().equals(kor.getUserName())){
			return false;
			}
		}
		korisnici.add(kor);
		return true;
	}
	
}
