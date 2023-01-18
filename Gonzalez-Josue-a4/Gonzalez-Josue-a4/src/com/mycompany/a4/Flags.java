package com.mycompany.a4;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class Flags extends FixedGameObject {
	private int sequenceNumber;
	
	public Flags() {
	}
	
	public Flags(int flagNum, int size, int color, float x, float y) {
		super(size*2, color, x, y);
		sequenceNumber = flagNum;
	}
	
	//Getter methods
	public int getSequenceNumber() { return sequenceNumber;}
	public Point getLocation() { return super.getLocation(); }
	public int getColor() { return super.getColor(); }
	public int getSize() { return super.getSize(); }
	public float getValueX() { return super.getValueX(); }
	public float getValueY() { return super.getValueY(); }
	

	//Setter methods
	public void setSequenceNumber(int n) { sequenceNumber = n; }
	public void setLocation(float x, float y) { super.setLocation(x,y); }
	public void setColor(int r, int g, int b) { super.setColor(r, g, b); }
	public void setValueX(float x) { super.setValueX(x); }
	public void setValueY(float y) { super.setValueY(y); }

	/**
	 * Flag is a filled isosceles triangle 
	 * must include text showing the flag number
	 * 
	 * @param g
	 * @param pCmpRelPrnt
	 */
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		g.setColor(getColor());
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy(); //save the original xform
		gXform.translate(pCmpRelScrn.getX(),pCmpRelScrn.getY());
		gXform.translate(getMyTranslate().getTranslateX(), getMyTranslate().getTranslateY());
		gXform.concatenate(getMyRotate()); // Rotation is not LAST
		gXform.scale(getMyScale().getScaleX(), getMyScale().getScaleY());
		gXform.translate(-pCmpRelScrn.getX(),-pCmpRelScrn.getY());
		//g.setTransform(gXform);
		
		//position of flags
		int x = (int) (getLocation().getX() - (getSize() / 2) + pCmpRelPrnt.getX());
		int y = (int) (getLocation().getY() - (getSize() / 2) + pCmpRelPrnt.getY());
		int[] xPoints = {x - getSize()/2 , x + getSize()/2, x};
		int[] yPoints = {y - getSize()/2 , y - getSize()/2, y + getSize()/2 };
		int nPoints = 3;
		String str = String.valueOf(sequenceNumber);	
		
		if(!isSelected()) {
			g.fillPolygon(xPoints, yPoints, nPoints);	//fill in isosceles triangle

		} else {
			g.drawPolygon(xPoints, yPoints, nPoints);
		}
		
		g.setColor(ColorUtil.BLACK);
		g.drawString(str, x, y);
		g.setTransform(gOrigXform); 
	}
	
	//toString method
	public String toString() {		
		String parentString = super.toString();
		String thisString = " size = " + getSize()
							+ " seqNum = " + sequenceNumber + " ";
			return "Flag: " + parentString + thisString;
	}
}
