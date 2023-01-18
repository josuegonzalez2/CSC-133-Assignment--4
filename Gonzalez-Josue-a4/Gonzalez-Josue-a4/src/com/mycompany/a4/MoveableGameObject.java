package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.ui.geom.Dimension;

public abstract class MoveableGameObject extends GameObject {
	private int speed;
	private int heading;
	
	public MoveableGameObject() {
		speed = 0;
		heading = 0;
	}
	
	public MoveableGameObject(int size, int color, float x, float y) {
		super(size, color, x, y);
		speed = 0;
		heading = 0;
	}
	
	public MoveableGameObject(int size, int speed, int color, int heading, float x, float y) {
		super(size, color, x, y);
		this.heading = heading;
		this.speed = speed;
		super.rotate(heading);
	}
	
	
	//Getter methods
	public int getSpeed() { return speed; }
	public int getHeading() { return heading; }
	public int getSize() { return super.getSize(); }
	public int getColor() { return super.getColor(); }
	public Point getLocation() { return super.getLocation(); }
	public int getRed() { return super.getRed(); }
	public int getGreen() { return super.getGreen(); }
	public int getBlue() { return super.getBlue(); }
	
	
	
	//Setter methods
	public void setSpeed(int s) { speed = s; }
	public void setHeading(int h) { heading = h; }
	public void setColor(int r, int g, int b) { super.setColor(r, g, b); }
	public void setLocation(float x, float y) { super.setLocation(x, y); }
	public void setRed(int r) { super.setRed(r); }
	public void setGreen(int g) { super.setGreen(g); }
	public void setBlue(int b) { super.setBlue(b); }
	
	/**
	 * moves all movable game objects
	 * - Translation will be applied to the object's translation LT
	 * - Translation is calculated from 
	 * 		* elapsedTime, speed, and heading
	 * - rotation LTs of ant and spiders should be updated
	 * 		whenever their headings are changed
	 * 		* update for ant in turn left and right methods in Ant
	 * 		* update it for spiders in tick() method in GameWorld
	 */
	public void move(int elapsedTime, Dimension dCmpSize) {
		double width = dCmpSize.getWidth();
		double height = dCmpSize.getHeight();
		double theta = Math.toRadians(90 - heading);
		double distance = (speed * elapsedTime) / 1000;

		float deltaX = (float) ((Math.cos(theta) * distance) + super.getValueX());
		float deltaY = (float) ((Math.sin(theta) * distance) + super.getValueY());
		

		if (deltaX < 0 || deltaX > width) {			
			System.out.println("Sorry you are out of bounds you must turn back!");
			//setHeading(heading + 180);
			super.rotate(180);
		}
		if(deltaY < 0 || deltaY > height) {	
			System.out.println("Sorry you are out of bounds you must turn back!");
			//setHeading(heading + 180);
			super.rotate(180);
		}

		setLocation(deltaX, deltaY);
	}
	
	
	//toString method
	public String toString() {
		String parentString = super.toString();
		String thisString = "heading = " + heading 
							+ " speed = " + speed + " ";
		return parentString + thisString;
	}
}
