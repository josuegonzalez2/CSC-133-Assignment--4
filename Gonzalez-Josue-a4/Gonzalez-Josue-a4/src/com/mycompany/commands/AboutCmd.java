package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;

public class AboutCmd extends Command {
	
	public AboutCmd() {
		super("About");
	}
	
	public void actionPerformed(ActionEvent ev) {
		String content = "Josue Gonzalez\n" + "CSC 133\n" + "Version 3";		
		Dialog.show("About", content, new Command("Continue"));
	}

}
