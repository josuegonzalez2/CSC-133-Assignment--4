package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.GameWorld;

public class TurnRightCmd extends Command{
	private GameWorld gw;
	
	public TurnRightCmd(GameWorld gw) {
		super("Right");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent ev) {
		gw.turnRight();
		
		//Dialog.show("Right", "Turning right!", new Command("Continue"));
		System.out.println("Turning right!");
		
		gw.notifyObservers(gw.getObjects());
	}
}