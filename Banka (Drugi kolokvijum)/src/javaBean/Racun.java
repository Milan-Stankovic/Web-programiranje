package javaBean;

public class Racun {
	
	private int brojRacuna;
	//private TipRacuna tip; da ne preterujem
	private String tipRacuna;
	private int raspolozivoStanje;
	private int rezervisanoStanje;
	private boolean online;
	private boolean aktivan;
	
	
	
	public int getBrojRacuna() {
		return brojRacuna;
	}
	public void setBrojRacuna(int brojRacuna) {
		this.brojRacuna = brojRacuna;
	}
	public String getTipRacuna() {
		return tipRacuna;
	}
	public void setTipRacuna(String tipRacuna) {
		this.tipRacuna = tipRacuna;
	}
	public int getRaspolozivoStanje() {
		return raspolozivoStanje;
	}
	public void setRaspolozivoStanje(int raspolozivoStanje) {
		this.raspolozivoStanje = raspolozivoStanje;
	}
	public int getRezervisanoStanje() {
		return rezervisanoStanje;
	}
	public void setRezervisanoStanje(int rezervisanoStanje) {
		this.rezervisanoStanje = rezervisanoStanje;
	}
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public Racun(int brojRacuna, String tipRacuna, int raspolozivoStanje, int rezervisanoStanje, boolean online,
			boolean aktivan) {
		super();
		this.brojRacuna = brojRacuna;
		this.tipRacuna = tipRacuna;
		this.raspolozivoStanje = raspolozivoStanje;
		this.rezervisanoStanje = rezervisanoStanje;
		this.online = online;
		this.aktivan = aktivan;
	}
	
	public Racun(){
		super();
	}
	
	public void addIznos(int i){
		raspolozivoStanje+=i;
	}
	
	public boolean isAktivan() {
		return aktivan;
	}
	public void setAktivan(boolean aktivan) {
		this.aktivan = aktivan;
	}

}
