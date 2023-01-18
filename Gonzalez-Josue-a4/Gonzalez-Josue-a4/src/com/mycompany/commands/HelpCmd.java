package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class HelpCmd extends Command{
	public HelpCmd() {
		super("Help");
	}
	
	public void actionPerformed(ActionEvent ev) {
		String content = "Accelerate: 'a'\nBrake: 'b'\nTurn Left: 'l'\nTurn Right: 'r'\n"
				+ "Pause Game: 'p'\nPosition: 'o'\nExit: 'x'";
				
		Dialog.show("Help", content, new Command("Continue"));
	}
}
