package com.mycompany.commands;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.GameWorld;

public class SoundCmd extends Command{
	private GameWorld gw;
	private CheckBox cb;
	
	public SoundCmd(GameWorld gw, CheckBox cb) {
		super("Sound");
		this.gw = gw;
		this.cb = cb;
	}
	
	public void actionPerformed(ActionEvent ev) {
		if (cb.isSelected()) {
			cb.getAllStyles().setBgTransparency(255);
			gw.setSound(true);
			gw.notifyObservers(gw.getObjects());
		} else {
			cb.getAllStyles().setBgTransparency(125);
			gw.setSound(false);
			gw.notifyObservers(gw.getObjects());
		}
	}
}
