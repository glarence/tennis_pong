package model;

public class TennisPlayer {
	
	//Instance variables
	private String name;
	private int speed;
	private int serve;
	private int forehand;
	private int backhand;
	private String imageURL;
	
	//Default constructor
	public TennisPlayer(){}
	
	public TennisPlayer(String name, int speed, int serve, int backhand,
						int forehand, String imageURL){
		this.name = name;
		this.speed = speed;
		this.serve = serve;
		this.forehand = forehand;
		this.backhand = backhand;
		this.imageURL = imageURL;
	}
	
	//Actions
	
	public void userSuperMove(){
		
	}
	
	public void moveUp(){
		
	}
	
	public void moveDown(){
		
	}
	
	public void moveLeft(){
		
	}
	
	public void moveRight(){
		
	}
	
	public void hitServe(){
		
	}
	
	public void hitForehand(){
		
	}
	
	public void hitBackhand(){
		
	}
	
	//Getters and Setters
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setSpeed(int speed){
		this.speed = speed;
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public void setServe(int serve){
		this.serve = serve;
	}
	
	public int getServe(){
		return serve;
	}
	
	public void setForehand(int forehand){
		this.forehand = forehand;
	}
	
	public int getForehand(){
		return forehand;
	}
	
	public void setBackhand(int backhand){
		this.backhand = backhand;
	}
	
	public int getBackhand(){
		return backhand;
	}
	
	public void setImageURL(String imageURL){
		this.imageURL = imageURL;
	}
	
	public String getImageURL(){
		return imageURL;
	}
	
	//To give name when used such as for implementing combo-box
	public String toString(){
		return name;
	}

}
