package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.GameWorld;

public class CollideWithFoodStationCmd extends Command {
	private GameWorld gw;
	
	public CollideWithFoodStationCmd(GameWorld gw) {
		super("Collide with Food Stations");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent ev) {
		//gw.foodStationCollision();
		
		Dialog.show("FoodStation", "Collided with food station!", new Command("Continue"));
		System.out.println("Collided with food station!");
		
		gw.notifyObservers(gw.getObjects());
	}
}
