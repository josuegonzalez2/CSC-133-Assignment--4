package com.mycompany.a4;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;

public class ScoreView extends Container implements Observer {
	private Label elapsedTime;
	private Label playerLives;
	private Label lastFlagReached;
	private Label foodLevel;
	private Label healthLevel;
	private Label sound;
	
	public ScoreView() {
		add(elapsedTime = new Label("Time: "));
		add(playerLives = new Label("Lives Left: "));
		add(lastFlagReached = new Label("Last Flag Reached: "));
		add(foodLevel = new Label("Food Level: "));
		add(healthLevel = new Label("Health Level: "));
		add(sound = new Label("Sound: "));
		
		
		setLayout(new FlowLayout(CENTER));
		textStyle();
	}
	
	@Override
	public void update (Observable o, Object arg) {
		//code here to update labels from the game/ant state data
		GameWorld gw = (GameWorld) o;
		Ant ant = Ant.getAnt();
		
		elapsedTime.setText("Time: " + gw.getElapsedTime());
		playerLives.setText("Lives Left: " + gw.getPlayerLives());
		lastFlagReached.setText("Last Flag Reached: " + ant.getLastFlagReached());
		foodLevel.setText("Food Level: " + ant.getFoodLevel());
		healthLevel.setText("Health Level: " + ant.getHealthLevel());
		
		if (gw.getSound()) {
			sound.setText("Sound: " + "ON");
		} else {
			sound.setText("Sound: " + "OFF");
		}
		
		textStyle();
	}
	
	public void textStyle() {
		elapsedTime.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		elapsedTime.getAllStyles().setPadding(RIGHT, 5);
		
		playerLives.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		playerLives.getAllStyles().setPadding(RIGHT, 1);
		
		lastFlagReached.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		lastFlagReached.getAllStyles().setPadding(RIGHT, 1);
		
		foodLevel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		foodLevel.getAllStyles().setPadding(RIGHT, 5);
		
		healthLevel.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		healthLevel.getAllStyles().setPadding(RIGHT, 2);
		
		sound.getAllStyles().setFgColor(ColorUtil.rgb(0, 0, 255));
		sound.getAllStyles().setPadding(RIGHT, 3);
	}

}
