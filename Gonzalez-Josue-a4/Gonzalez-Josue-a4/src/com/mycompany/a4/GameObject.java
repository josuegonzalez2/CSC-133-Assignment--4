package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.charts.models.Point;
import com.codename1.ui.Transform;

public abstract class GameObject implements IDrawable, ICollider{
	
	
	//private Point location;
	private int color, size, red, green, blue;
	private float valueX, valueY;
	
	private Transform myTranslate, myRotate, myScale;
		
	public GameObject() {		
	}
	
	public GameObject(int size, int color, float x, float y) {
		this.size = size;
		this.color = color;
		//this.location = new Point(x,y);
		this.valueX = x;
		this.valueY = y;
		
		//setup transformations
		myTranslate = Transform.makeIdentity();
		myRotate = Transform.makeIdentity();
		myScale = Transform.makeIdentity();
		
		myTranslate.translate(x, y);
		myScale.scale(size, size);
		myRotate.rotate(180, x, y);
		
	}
	
	//Getter methods
	public int getColor() { return color; }
	public int getSize() { return size; }
	public Point getLocation() { 
		return new Point(myTranslate.getTranslateX(), myTranslate.getTranslateY()); 
	}
	public float getValueX() { return valueX;}
	public float getValueY() { return valueY; }
	public int getRed() { return red; }
	public int getGreen() { return green; }
	public int getBlue() { return blue; }
	public Transform getMyTranslate() { return myTranslate; }
	public Transform getMyRotate() { return myRotate; }
	public Transform getMyScale() { return myScale; }
	
	//Setter methods
	public void setColor(int r, int g, int b) { 
		color = ColorUtil.rgb(r,g,b);
		red = r;
		green = g;
		blue = b;
	}
	public void setLocation(float x, float y) { 
		///location = new Point(x,y);
		valueX = x;
		valueY = y;
		myTranslate.setIdentity();
		myTranslate.translate(x, y);
	}
	public void setValueX(float x) { valueX = x; }
	public void setValueY(float y) { valueY = y; }
	public void setRed(int r) { setColor(r, green, blue); }
	public void setGreen(int g) { setColor(red,g,blue); }
	public void setBlue(int b) { setColor(red,green, b); }
		
	//Transformations
	/** 
	 * @param tx
	 * @param ty
	 */
	public void translate(float tx, float ty) {
		myTranslate.translate(tx, ty);
	}
	
	/**
	 * @param degrees
	 */
	public void rotate(float degrees) {
		/* pivot point (rotation origin) is (0,0),
		 * this means the rotation will be applied 
		 * about the screen origin
		 */
		//rotate(angle, px, py)
		myRotate.rotate((float) Math.toRadians(degrees), 0, 0);
		
	}
	
	/**
	 * @param sx
	 * @param sy
	 */
	public void scale(float sx, float sy) {	
		/* Remember that like other transformation methods,
		 * scale() is also applied relative to screen origin
		 */
		myScale.scale(sx, sy);
	}
	
	public void resetTransform() {
		myRotate.setIdentity();
		myTranslate.setIdentity();
		myScale.setIdentity();
		
	}
	
	/**
	 * - Will check if bother current objects
	 * 		are in the same position
	 * 
	 * @param otherObject
	 * @return boolean
	 */
	@Override
	public boolean collidesWith(GameObject otherObject) {
		boolean result = false;
		
		double thisCenterX = this.getLocation().getX() + (this.getSize()/2);
		double thisCenterY = this.getLocation().getY() + (this.getSize()/2);
		
		double otherCenterX = otherObject.getLocation().getX() + (otherObject.getSize()/2);
		double otherCenterY = otherObject.getLocation().getY() + (otherObject.getSize()/2);
		
		//find dist between centers (use square, to avoid taking roots)
		double dx = thisCenterX - otherCenterX;
		double dy = thisCenterY - otherCenterY;
		
		double distBetweenCentersSqr = (dx*dx + dy*dy);
		
		//find square of sum of radii
		double thisRadius = this.getSize()/2;
		double otherRadius = otherObject.getSize()/2;
		
		double radiiSqr = (thisRadius*thisRadius + 2*thisRadius*otherRadius + otherRadius*otherRadius);
		
		if(distBetweenCentersSqr <= radiiSqr) {
			result = true;
		}
		
		return result;
	}
	
	//toString method
	public String toString() {		
		return "loc = " + getLocation().getX() + ", " + getLocation().getY() +  
				" color = [" + ColorUtil.red(color) + ", " +  
							   ColorUtil.green(color) + ", " +
							   ColorUtil.blue(color) + "] ";                    
	}
	
	public abstract void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn);
	
	@Override
	public void handleCollision(GameObject otherObject, GameWorld gw) {}
}
