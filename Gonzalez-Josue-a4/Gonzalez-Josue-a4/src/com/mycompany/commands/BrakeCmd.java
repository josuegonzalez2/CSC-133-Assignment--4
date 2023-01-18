package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.GameWorld;

public class BrakeCmd extends Command {
	private GameWorld gw;
	
	public BrakeCmd(GameWorld gw) {
		super("Brake");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent ev) {
		gw.brake();
		
		//Dialog.show("Brake", "Now braking!", new Command("Continue"));
		System.out.println("Now braking");
		
		gw.notifyObservers(gw.getObjects());
	}
}
