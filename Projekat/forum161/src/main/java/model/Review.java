package model;

import java.io.Serializable;

public class Review implements Serializable {

	private RewType type;
	private String id;
	private RewForType forType; 
	
	public RewForType getForType() {
		return forType;
	}
	public void setForType(RewForType forType) {
		this.forType = forType;
	}
	public RewType getType() {
		return type;
	}
	public void setType(RewType type) {
		this.type = type;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Review() {
		super();
	}
	
	
}
