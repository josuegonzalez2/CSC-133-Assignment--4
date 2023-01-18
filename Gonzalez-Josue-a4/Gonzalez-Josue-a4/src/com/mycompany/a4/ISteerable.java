package com.mycompany.a4;

public interface ISteerable {
	
	/**
	 * Moveable Steerable Objects need a value to change direction
	 * @param amount - value to change object direction
	 */
	public void steer(boolean turn);
}
