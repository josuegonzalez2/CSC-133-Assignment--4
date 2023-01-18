package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.GameWorld;

public class CollideWithFlagCmd extends Command{
	private GameWorld gw;
	
	public CollideWithFlagCmd(GameWorld gw) {
		super("Collide with Flag");
		this.gw = gw;		
	}

	
	public void actionPerformed(ActionEvent ev) {
		int flagNum = ev.getKeyEvent();
		Command cmd = new Command("Continue");
		
		if(flagNum == 49) {
			flagNum = 1;
			
			Dialog.show("Flags", "You pressed: "+ flagNum, cmd);
			
			gw.flagReached(flagNum);
			gw.notifyObservers(gw.getObjects());
			
		} else if(flagNum >= 50 && flagNum <= 57) {
			flagNum = (flagNum - 50)+2;
			
			Dialog.show("Flags", "You pressed: "+ flagNum, cmd);
			
			gw.flagReached(flagNum);
			gw.notifyObservers(gw.getObjects());
			
		} else {
			TextField numText = new TextField();
			
			Dialog.show("Enter a number between 1-9", numText, cmd);
			
			int temp = Integer.parseInt(numText.getText());

			if(temp < 1 && temp > 9) {
				Dialog.show("Error", "Invalid number try again", cmd); 
				
			} else {
				flagNum = temp;
				
				Dialog.show("Flags", "You pressed: "+ flagNum, cmd);
				
				gw.flagReached(flagNum);
				gw.notifyObservers(gw.getObjects());
			}
		}
	}
}
