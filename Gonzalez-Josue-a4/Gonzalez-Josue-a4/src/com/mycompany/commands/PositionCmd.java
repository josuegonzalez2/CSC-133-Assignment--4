package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.GameWorld;

public class PositionCmd extends Command {
	
	private GameWorld gw;
	
	public PositionCmd(GameWorld gw) {
		super("Position");
		this.gw = gw;
	}
	
	@Override
	public  void actionPerformed(ActionEvent e) {
		gw.setPressed(true);
	}
}
