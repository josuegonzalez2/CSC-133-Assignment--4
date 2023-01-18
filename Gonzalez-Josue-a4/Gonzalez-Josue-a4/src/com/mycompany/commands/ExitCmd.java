package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.GameWorld;

public class ExitCmd extends Command{
	private GameWorld gw;
	
	public ExitCmd(GameWorld gw) {
		super("Exit");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent ev) {	
		if(!Dialog.show("Exit","Would you like to exit the game?", "No", "Yes")) {
			gw.gameExit(true);
		}
		
		gw.notifyObservers(gw.getObjects());
	}
}
