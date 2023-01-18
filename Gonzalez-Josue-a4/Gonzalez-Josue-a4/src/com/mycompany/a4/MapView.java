   package com.mycompany.a4;

import java.util.Observer;
import java.util.Observable;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.plaf.Border;
import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;

public class MapView extends Container implements Observer {
	
	private float height, width;
	private float winLeft, winBottom, winRight, winTop;
	private GameWorld gw;
	private FixedGameObject selectedObj;
	private GameObjectCollection objects;
	Transform worldToND, ndToDisplay, theVTM;
	private Point pPrevDragLoc = new Point(-1, -1);
	
	public MapView() {
		this.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.rgb(255, 0, 0)));
		//initialize world window
		this.winLeft = 0;
		this.winBottom = 0;
		this.winRight = getMapWidth() / 2;
		this.winTop = getMapHeight() /2;
		
		this.width = this.winRight - this.winLeft;
		this.height = this.winTop - this.winBottom;
	}
	
	public void update (Observable o, Object arg) {
		//code here to call the method in GameWorld (Observable)
		//that output the game object information to the console
		this.gw = (GameWorld) o;
		this.getAllStyles().setBorder(Border.createLineBorder(4, ColorUtil.rgb(255, 0, 0)));
		gw.map();
		
		objects = gw.getObjects();
		
		this.repaint();
	}
	
	public float getMapWidth() { return width; }
	public float getMapHeight() { return height; }
	public boolean getPressed() { return gw.getPressed(); }
	public boolean getPaused() { return gw.getPaused(); }
	public float getWinLeft() { return winLeft; }
	public float getWinBottom() { return winBottom; }
	
	public void setPressed(boolean pressed) { gw.setPressed(pressed); }
	public void setPaused(boolean pause) { gw.setPaused(pause); }
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		//Calculate winWidth and winHeight
		float winHeight = winTop - winBottom;
		float winWidth = winRight - winLeft;
		
		//construct the view Transformation Matrix
		worldToND = buildWorldToND(winWidth, winHeight, winLeft, winBottom);
		ndToDisplay = buildNDToDisplay(this.width, this.height);
		theVTM = ndToDisplay.copy();
		theVTM.concatenate(worldToND);	//worldToND will be applied first to points!
		
		//concatenate the VTM onto the g's current transformation 
		//(do not forget to apply "local origin" transformation)
		Transform gXform = Transform.makeIdentity();
		g.getTransform(gXform);
		gXform.translate(getAbsoluteX(), getAbsoluteY()); //local origin xform
		gXform.concatenate(theVTM); // VTM xform
		gXform.translate(-getAbsoluteX(), -getAbsoluteY());
		//g.setTransform(gXform);
		
		
		//tell each shape to draw itself using the g (which contains the VTM)
		Point pCmpRelPrnt = new Point(this.getX(), this.getY());
		Point pCmpRelScrn = new Point(getAbsoluteX(), getAbsoluteY());
		
		GameObject next;
		IIterator iter = objects.getIterator();
		
		//Iterate through all game objects to draw all of them
		while(iter.hasNext()) {
			next = (GameObject) iter.getNext();
			next.draw(g, pCmpRelPrnt, pCmpRelScrn);
			g.resetAffine();
		}
	}
	
	/**
	 * 
	 * @param w	window width
	 * @param h	window height
	 * @param l window left 
	 * @param b window bottom
	 * @return
	 */
	public Transform buildWorldToND(float w, float h, float l, float b) {
		Transform tmpXfrom = Transform.makeIdentity();
		tmpXfrom.scale((1 / w), (1 / h));
		tmpXfrom.translate(-l, -b);
		return tmpXfrom;
	}
	
	/**
	 * 
	 * @param w display width
	 * @param h display height
	 * @return
	 */
	public Transform buildNDToDisplay(float w, float h) {
		Transform tmpXfrom = Transform.makeIdentity();
		tmpXfrom.translate(0, h);
		tmpXfrom.scale(w, -h);
		return tmpXfrom;
	}
	
	@Override
	public void pointerPressed(int x, int y) {
		if (this.getPaused()) {
										
			if (selectedObj == null && gw.getPressed() == false) {
	
				x = x - getParent().getAbsoluteX();
				y = y - getParent().getAbsoluteY();
	
				Point pPtrRelPrnt = new Point(x, y);
				Point pCmpRelPrnt = new Point(this.getX(), this.getY());
	
				IIterator iter = objects.getIterator();
				GameObject obj;
	
				while (iter.hasNext()) {
					obj = (GameObject) iter.getNext();
					if (obj instanceof FixedGameObject) {
						FixedGameObject FixedObj = (FixedGameObject) obj;
						if (((FixedGameObject) FixedObj).contains(pPtrRelPrnt, pCmpRelPrnt)) {
							
							selectedObj = (FixedGameObject) obj;
							selectedObj.setSelected(true);
							System.out.println("You've selected a object: " + obj.toString());
						}
					}
				}
	
			} else if (selectedObj != null && gw.getPressed() == true) {
				selectedObj.setLocation(x - getParent().getAbsoluteX() - getX(), 
						y - getParent().getAbsoluteY());
				selectedObj.setSelected(false);
				selectedObj = null;
				gw.setPressed(false);
			}
		
		}
	}
	
	//zooming and panning methods
	public void zoom(float factor) {
		//positive factor would zoom in (make the worldWin smaller), suggested value is 0.05f
		//negative factor would zoom out (make the worldWin larger), suggested value is -0.05f
		//calculate winWidth and winHeight
		float newWinLeft = width + width * factor;
		float newWinRight = winRight - width * factor;
		float newWinTop = winTop - height * factor;
		float newWinBottom = winBottom + height * factor;
		float newWinHeight = newWinTop - newWinBottom;
		float newWinWidth = newWinRight - newWinLeft;
					
		//in CN1 do not use world window dimensions greater than 1000
		if(newWinWidth <= 1000 && newWinHeight <= 1000 && newWinWidth > 0 && newWinHeight > 0) {
			winLeft = newWinLeft;
			winRight = newWinRight;
			winTop = newWinTop;
			winBottom = newWinBottom;
		} else {
			System.out.println("Cannot zoom further!");
		}
					
		this.repaint();
	}
	
	public void panHorizontal(double delta) {
		//positive delta would pan right (image would shift left), suggested value is 5
		// negative delta would pan left (image would shift right), suggested value is -5
		winLeft += delta;
		winRight += delta;
		this.repaint();
	}
	
	public void panVertical(double delta) {
		//positive delta would pan up (image would shift down), suggested value is 5
		//negative delta would pan down (image would shift up), sugested value is -5
		winBottom += delta;
		winTop += delta;
		this.repaint();
	}
	
	@Override
	public boolean pinch(float scale) {
		if(scale < 1.0) {
			//zooming out: two fingers come closer together (on actual device), right mouse
			//click + draw towards the top left corner of screen (on simulator)
			zoom(-0.05f);
		} else if(scale > 1.0) {
			//zooming In: two fingers go away from each other (on actual device), right mouse
			//click + drag away from the top left corner of screen (on simulator)
			zoom(0.05f);
		}
		
		return true;
	}
	
	@Override
	public void pointerDragged(int x, int y) {
		if(pPrevDragLoc.getX() != -1) {
			if(pPrevDragLoc.getX() < x) {
				panHorizontal(5);
			} else if(pPrevDragLoc.getX() > x) {
				panHorizontal(5);
			}
			
			if(pPrevDragLoc.getY() < y) {
				panVertical(-5);
			} else if(pPrevDragLoc.getY() > y) {
				panVertical(5);
			}
		}
		
		pPrevDragLoc.setX(x);
		pPrevDragLoc.setY(y);
	}
}
