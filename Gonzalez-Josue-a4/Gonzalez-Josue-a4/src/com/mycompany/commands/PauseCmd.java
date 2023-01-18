package com.mycompany.commands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a4.Game;
import com.mycompany.a4.GameWorld;

public class PauseCmd extends Command {
	
	private Game game;
	private GameWorld gw;
	
	public PauseCmd(GameWorld gw, Game game) {
		super("Pause"); 
		this.game = game;
		this.gw = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(gw.getPaused()) {
			System.out.println("Resumed game");
			gw.setPaused(false);
			
		} else {
			System.out.println("Game paused");
			gw.setPaused(true);
		}
		
		game.gameStatus();
	}
}
