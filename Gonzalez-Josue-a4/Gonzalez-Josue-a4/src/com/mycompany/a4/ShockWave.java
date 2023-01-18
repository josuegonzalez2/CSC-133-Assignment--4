package com.mycompany.a4;

import java.util.Random;
import java.util.Vector;
import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;
import com.codename1.util.MathUtil;

public class ShockWave extends MoveableGameObject implements IDrawable{

	// the lifespan of a shock wave, in ms
	private int lifeLength = 16;
	private int size;
	
	// the speed of a shock wave
	private int speed = 200;
	private int MAX_LEVEL = 8;
	private float epsilon = (float)0.001;
	Random r = new Random();
	int hold = 20;
	float arr[] = new float[8];
	
	// this shock waves current lifespan
	private int lifeSpan;
	
	Vector<Point> controlPointVector = new Vector<Point>();
	
	ShockWave(int lifeLength, int speed, int color, int heading, float x, float y) {
		//Size, speed, color, heading, x, y
		super(lifeLength, speed, color, heading, x, y);
		lifeSpan = lifeLength;
	}
	
	//method to draw Bezier Curve
	private void drawBezierCurve(Graphics g, Vector<Point> controlPointVector, int level) {
		if(straightEnough(controlPointVector)) {
			//draw line from first control point to last control point
			Point q0 = (Point) controlPointVector.elementAt(0);
			Point q3 = (Point) controlPointVector.elementAt(3);
			
			g.setColor(getColor());
			g.drawLine((int)(q0.getX()), (int)(q0.getY()), (int)(q3.getX()), (int)(q3.getY()));
			
		} else {
			Vector<Point> leftSubVector = new Vector<Point>();
			Vector<Point> rightSubVector = new Vector<Point>();
			
			subdivideCurve(controlPointVector, leftSubVector, rightSubVector);
			drawBezierCurve(g, leftSubVector, level + 1);
			drawBezierCurve(g, rightSubVector, level + 1);
		}
	}
	
	/* determines whether the four points Q0, Q1, Q2, Q3 in the input array of control
	 * Points are within some tolerance "epsilon" of being co-linear
	 */
	private boolean straightEnough(Vector<Point> controlPointVector) {
		//find length around control polygon
		Point q0 = (Point) controlPointVector.elementAt(0);
		Point q1 = (Point) controlPointVector.elementAt(1);
		Point q2 = (Point) controlPointVector.elementAt(2);
		Point q3 = (Point) controlPointVector.elementAt(3);
		
		float d1 = lengthOf(q0,q1) + lengthOf(q1,q2) + lengthOf(q2,q3);
		
		// find distance directly between first and last control point
		float d2 = lengthOf(q0, q3);
		
		if(Math.abs(d1-d2) < epsilon){	//epsilon ("tolerance") = (e.g.) .001
		 	return true;
		 } else {
		 	return false;
		 }
		
	}
	
	/**
	 * Splits the input control point vector Q into two control point
	 * vectors R and S such that R and S define two Bezier cirve segments that
	 * together exactly match the bezier curve defined by Q
	 * 
	 * @param cpv	controlPointVector
	 * @param lsv	leftSubVector
	 * @param rsv	rightSubVector
	 */
	private void subdivideCurve(Vector<Point> cpv, Vector<Point> lsv, Vector<Point> rsv) {
		float tempX;
		float tempY;
		
		Point q0 = (Point) cpv.elementAt(0);
		Point q1 = (Point) cpv.elementAt(1);
		Point q2 = (Point) cpv.elementAt(2);
		Point q3 = (Point) cpv.elementAt(3);
		
		// R(0) = Q(0)
		Point r0 = new Point(q0.getX(), q0.getY());
		
		// R(1) = (Q(0) + Q(1)) / 2.0
		tempX = (float) ((q0.getX() + q1.getX()) / 2.0);
		tempY = (float) ((q0.getY() + q1.getY()) / 2.0);
		Point r1 = new Point(tempX, tempY);
		
		// R(2) = (R(1) / 2.0) + (Q(1) + Q(2)) / 4.0
		tempX = (float) ((r1.getX() / 2.0) + ((q1.getX() + q2.getX()) / 4.0));
		tempY = (float) ((r1.getY() / 2.0) + ((q1.getY() + q2.getY()) / 4.0));
		Point r2 = new Point(tempX, tempY);
		
		// S(3) = Q(3)
		Point s3 = new Point(q3.getX(), q3.getY());
		
		// S(2) = (Q(2) + Q(3)) / 2.0
		tempX = (float) ((q2.getX() + q3.getX()) / 2.0);
		tempY = (float) ((q2.getY() + q3.getY()) / 2.0);
		Point s2 = new Point();
		
		// S(1) = (Q(1) + Q(2)) / 4.0 + S(2) / 2.0
		tempX = (float) ((q1.getX() + q2.getX()) / 4.0 + s2.getX() / 2.0);
		tempY = (float) ((q1.getY() + q2.getY()) / 4.0 + s2.getY() / 2.0);
		Point s1 = new Point(tempX, tempY);
		
		// R(3) = (R(2) + S(1)) / 2.0
		tempX = (float) ((r2.getX() + s1.getX()) / 2.0);
		tempY = (float) ((r2.getY() + s1.getY()) / 2.0);
		Point r3 = new Point(tempX, tempY);
		
		// S(0) = R(3)
		Point s0 = new Point(r3.getX(), r3.getY());
		
		lsv.add(r0);
		lsv.add(r1);
		lsv.add(r2);
		lsv.add(r3);
		
		rsv.add(s0);
		rsv.add(s1);
		rsv.add(s2);
		rsv.add(s3);
	}
	
	//find the length of two control points
	private float lengthOf(Point p1, Point p2) {
		//Point slope formula: (y2-y1)/(x2-x1)
		float x1 = p1.getX();
		float x2 = p2.getY();
		float x3 = x2 - x1;
		
		float y1 = p1.getY();
		float y2 = p2.getY();
		float y3 = y2 - y1;
		
		
		return y3 / x3;
	}

	/**
	 * Shock Waves are shaped like cubic Bezier curves
	 * 
	 * @param g
	 * @param pCmpRelPrnt
	 */
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		controlPointVector.add(pCmpRelPrnt);
		
		if(lifeLength > 0) {
			drawBezierCurve(g, controlPointVector, 1);
			lifeLength--;
		}
	}
}
