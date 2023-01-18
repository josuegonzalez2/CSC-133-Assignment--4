package com.mycompany.a4; 

import com.codename1.charts.models.Point;

public abstract class FixedGameObject extends GameObject implements ISelectable {

	private Point location;
	private boolean selected;
	
	public FixedGameObject() {
	}
	
	public FixedGameObject(int size, int color, float x, float y) {
		super(size, color, x, y);
		this.location = new Point(x,y);
		
	}
	public void setLocation(float x, float y) {this.location = new Point(x,y);}
	
	public Point getLocation() {return location;}
	public int getSize() {return super.getSize();}
	
	public String toString() {return super.toString();}
	
	@Override
	public void setSelected(boolean selected) {this.selected = selected;}
	
	@Override
	public boolean isSelected() {return selected;}
	
	/**
	 * ...[assign iShapeX & iShapeY to rect coordinates
	 * (upper left corner of rect which is relative to the origin of the component)
	 * supplied in the constructor]
	 */
	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		int px = (int) pPtrRelPrnt.getX(); //pointer location relative to
		int py = (int) pPtrRelPrnt.getY(); //parent's origin
		
		int xLoc = (int) pCmpRelPrnt.getX() + (int) this.getLocation().getX(); // shape location relative
		int yLoc = (int) pCmpRelPrnt.getY() + (int) this.getLocation().getY(); // to parent's origin
		
		if((px >= xLoc) && (px <= xLoc + this.getSize()/2) && (py >= yLoc) && (py <= yLoc + this.getSize()/2)) {
			return true;
		} else {
			return false;
		}
	}
}
