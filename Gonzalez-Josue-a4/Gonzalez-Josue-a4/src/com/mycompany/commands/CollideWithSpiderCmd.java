package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.GameWorld;

public class CollideWithSpiderCmd extends Command{
	private GameWorld gw;
	
	public CollideWithSpiderCmd(GameWorld gw) {
		super("Collide with Spider");
		this.gw = gw;
	}
	
	public void actionPerformed(ActionEvent ev) {
		//gw.spiderCollision();	
		
		Dialog.show("Spider", "You have collide with a spider!", new Command("Continue"));
		
		gw.notifyObservers(gw.getObjects());
	}
}
