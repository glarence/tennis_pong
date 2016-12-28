package model;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class TennisBall extends Circle{
	
	private int xSpeed = 2;
	private int ySpeed = 1;
	
	public TennisBall(double centerX, double centerY, double radius, Paint fill){
		super(centerX, centerY, radius, fill);
	}
	
	public String toString(){
		return "Tennis Ball";
	}
	
	public void changeDirectionX(){
		xSpeed *= -1;
	}
	public void changeDirectionY(){
		ySpeed *= -1;
	}
	
	public void update(){
		System.out.println(xSpeed);
		System.out.println(ySpeed);
		this.setCenterX(this.getCenterX() + xSpeed);
		this.setCenterY(this.getCenterY() + ySpeed);
	}
	
	public boolean isMovingRight(){
		if(xSpeed < 0)
			return false;
		return true;
	}

}
