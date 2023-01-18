package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.GameWorld;

public class TurnLeftCmd extends Command{
	private GameWorld gw;
	
	public TurnLeftCmd(GameWorld gw) {
		super("Left");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent ev) {
		gw.turnLeft();		
		
		//Dialog.show("Left", "Turning Left!", new Command("Cntinue"));
		System.out.println("Turning left!");
		
		gw.notifyObservers(gw.getObjects());
	}
}
