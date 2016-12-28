package model;

public class NPC {
	
	//Instance variables
	private String name;
	private String imageURL;
	
	//Default constructor
	public NPC(){}
	
	public NPC(String name, String imageURL){
		this.name = name;
		this.imageURL = imageURL;
	}
	
	//Getters and Setters
	
	public void setName(String name){
		this.name = name;
	}
		
	public String getName(){
		return name;
	}
	
	public void setImageURL(String imageURL){
		this.imageURL = imageURL;
	}
	
	public String getImageURL(){
		return imageURL;
	}

}
