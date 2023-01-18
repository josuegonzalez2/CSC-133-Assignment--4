package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.GameWorld;

public class AccelerateCmd extends Command{
	private GameWorld gw;
	
	public AccelerateCmd(GameWorld gw) {
		super("Accelerate");
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {		
		gw.accelerate();
		
		//Dialog.show("Accelerate", "Now accelerating!", new Command("Continue"));
		System.out.println("Now accelerating!");
		
		gw.notifyObservers(gw.getObjects());
	}

}
