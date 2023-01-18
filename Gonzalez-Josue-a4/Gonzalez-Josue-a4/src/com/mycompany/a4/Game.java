package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
//import com.codename1.ui.Label;
//import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.util.UITimer;
import com.mycompany.commands.*;
//import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
//import com.codename1.ui.events.ActionEvent;

public class Game extends Form implements Runnable{
	
	private GameWorld gw;
	private MapView mv;		
	private ScoreView sv;	
	private UITimer timer = new UITimer(this);;
	
	//Containers
	private Container leftAreaContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
	private Container rightAreaContainer = new Container(new BoxLayout(BoxLayout.Y_AXIS));
	private Container bottomAreaContainer = new Container(new FlowLayout(Component.CENTER));
	
	//Tool bar
	Toolbar sideMenu = new Toolbar();
	
	//Command Buttons
	private CmdButton accelerateBtn, brakeBtn, turnLeftBtn, turnRightBtn, positionBtn, pauseBtn;
	
	//Commands
	private AccelerateCmd accelerateCmd;
	private BrakeCmd brakeCmd;
	private TurnLeftCmd turnLeftCmd;
	private TurnRightCmd turnRightCmd;
	private PositionCmd positionCmd;
	private PauseCmd pauseCmd;
		
	public Game() {
		gw = new GameWorld();	//create "Observable" GameWorld
		mv = new MapView();		//create an "Observer for the map
		sv = new ScoreView();	//create an "Observer" for the game/ant state data
		
		gw.addObserver(mv);		//register the map observer
		gw.addObserver(sv);		//register the score observer
		
		/*
		 * code here to create Command objects for each command,
		 * add commands to side menu and title bar area, bind commands to keys, create
		 * control containers for the buttons, add buttons to the control containers,
		 * add commands to the buttons, and add control containers, MapView, and\
		 * ScoreView to the form
		 */
		this.setLayout(new BorderLayout());
		//gw.init();	//initialize world
		
		sideMenu();
		
		leftAreaContainer();
		rightAreaContainer();
		
		bottomAreaContainer();
		
		add(BorderLayout.CENTER, mv);
		add(BorderLayout.NORTH, sv);
		
		gw.notifyObservers(gw.getObjects());
		//timer = new UITimer(this);
		this.show();
		
		gw.setMapHeight(mv.getMapHeight()/2);
		gw.setMapWidth(mv.getMapWidth()/2);
		gw.init();	//initialize world
		
		gw.createSounds();
		gameStatus();
		revalidate();
	}
	
	/**
	 * When an object implementing interface Runnable is used to create a thread,
	 * starting the thread causes the object's run method to be called
	 * in that separately executing thread.
	 */
	@Override
	public void run() {
		Dimension dCmpSize = new Dimension(mv.getWidth(), mv.getHeight());
		
		gw.worldTick(100, dCmpSize);
		gw.notifyObservers();
	}
	
	public boolean getPaused() {
		return gw.getPaused();
	}
	
	public void gameStatus() {
		if(gw.getPaused() && timer != null) {
				
			timer.cancel();	
				
			gw.getBackground().pause();
			pauseBtn.setText("Play");
			gw.setPaused(true);
				
			disableButtons();
			removeListeners();
			enablePosBtn();
		} else {		
			//Schedule(int timeMillis, boolean repeat, Form bound)
			//Binds the timer to start at the given schedule
			timer.schedule(10, true, this);
			  
			pauseBtn.setText("Pause"); 
			gw.setPaused(false); 
			 
			addListeners();
			enableButtons();
			disablePosBtn();
		}
	}
	
	public void disableButtons() {
		//disable all buttons 
		accelerateBtn.setEnabled(false);
		brakeBtn.setEnabled(false);
		turnLeftBtn.setEnabled(false);
		turnRightBtn.setEnabled(false);
		accelerateCmd.setEnabled(false);
	}
	
	public void enableButtons() {
		accelerateBtn.setEnabled(true); 
		brakeBtn.setEnabled(true);
		turnLeftBtn.setEnabled(true); 
		turnRightBtn.setEnabled(true);
		accelerateCmd.setEnabled(true);
	}
	
	public void removeListeners() {
		//remove all key listeners for buttons
		removeKeyListener('a', accelerateCmd);
		removeKeyListener('b', brakeCmd);
		removeKeyListener('l', turnLeftCmd);
		removeKeyListener('r', turnRightCmd);
	}
	
	public void addListeners() {
		addKeyListener('a', accelerateCmd); 
		addKeyListener('b', brakeCmd);
		addKeyListener('l', turnLeftCmd); 
		addKeyListener('r', turnRightCmd);
		  
	}
	
	public void enablePosBtn() {
		//position button it enabled
		positionBtn.setEnabled(true);
		positionCmd.setEnabled(true);
		positionBtn.getAllStyles().setBgColor(ColorUtil.BLUE);
		positionBtn.getAllStyles().setFgColor(ColorUtil.WHITE);
		addKeyListener('o', positionCmd);
	}
	
	public void disablePosBtn() {
		positionBtn.getDisabledStyle();
		positionBtn.setEnabled(false); 
		positionCmd.setEnabled(false);
		positionBtn.getAllStyles().setBgColor(ColorUtil.WHITE);
		positionBtn.getAllStyles().setFgColor(ColorUtil.BLUE);
	}
	
	/**
	 * This method will set the side menu
	 * The side menu will contain:
	 * - Accelerate
	 * - Sound
	 * - About
	 * - Exit
	 */
	private void sideMenu() {
		setToolbar(sideMenu);
		sideMenu.setTitle(" OnTarget Game ");
		
		/* 
		 * Accelerate menu item
		 * - Should invoke 'a' command
		 */
		accelerateCmd = new AccelerateCmd(this.gw);
		sideMenu.addCommandToSideMenu(accelerateCmd);
		
		/* 
		 * Sound menu item 
		 * - Includes a check box showing current state of "sound" attribute
		 * - Selecting sound menu item check box sets a boolean "sound"
		 *		attribute to "ON" or "OFF" accordingly
		 * - When the game starts sound attribute should be "OFF"
		 */
		soundCmdBtn();
		
		/* 
		 * About menu item
		 * - displays a dialog box with 
		 * 	+ personal name
		 * 	+ course name
		 * 	+ any other info like version number of program
		 */
		AboutCmd about = new AboutCmd();
		sideMenu.addCommandToSideMenu(about);
		
		/*
		 * Exit menu item
		 * - invokes 'x' command
		 * - dialog box to prompt graphically for confirmation
		 * 		and then exit the program
		 */
		ExitCmd exit = new ExitCmd(this.gw);
		sideMenu.addCommandToSideMenu(exit);
		
		/*
		 * Help command
		 * - displays dialog box of all key commands
		 */
		HelpCmd help = new HelpCmd();
		sideMenu.addCommandToRightBar(help);

		mv.getAllStyles().setBorder(Border.createLineBorder(5, ColorUtil.rgb(255, 0, 0)));

	}
	
	/**
	 * This method will have the bottom area of commands
	 * This includes:
	 * - Collide with Flag
	 * - Collide with Spider
	 * - Collide with Food Stations
	 * - Tick
	 */
	private void bottomAreaContainer() {
		setLayout(new BorderLayout());
		bottomAreaContainer.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.BLACK));
		this.addComponent(BorderLayout.SOUTH, bottomAreaContainer);
		
		//position command button 
		positionCmdBtn();
		
		//pause command button
		pauseCmdBtn();
		
		//collide with Flag command button
		//collideWithFlagCmdBtn();
		
		//collide with Spider command button
		//collideWithSpiderCmdBtn();
		
		// collide with Food Station command button
		//collideWithFoodStationCmdBtn();

		// Tick command button
		//tickCmdBtn();

		
	}
	
	/**
	 * This method will have the left area of commands
	 * This includes:
	 * - Accelerate
	 * - Left
	 */
	private void leftAreaContainer() {
		leftAreaContainer.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.BLACK));
		this.addComponent(BorderLayout.WEST, leftAreaContainer);
		//Accelerate command Button
		accelerateCmdBtn();

		//Turn Left command Button
		turnLeftCmdBtn();
	}
	
	/**
	 * This method will have the right area of commands
	 * This includes:
	 * - Brake
	 * - Right
	 */
	private void rightAreaContainer() {
		rightAreaContainer.getAllStyles().setBorder(Border.createLineBorder(3, ColorUtil.BLACK));
		this.addComponent(BorderLayout.EAST, rightAreaContainer);
		
		//Brake command Button
		brakeCmdBtn();

		//Turn Right command Button
		turnRightCmdBtn();
	}
	
	
	//Command buttons
	
	//create position command button
	private void positionCmdBtn() {
		positionCmd = new PositionCmd(gw);
		positionBtn = new CmdButton(positionCmd);
		positionBtn.setCommand(positionCmd);		
		//positionBtn.getAllStyles().setMargin(Component.TOP, 250);
		positionBtn.getAllStyles().setBorder(Border.createLineBorder(6, ColorUtil.BLACK));
		//if(gw.getPaused()) { enablePosBtn(); } else { disablePosBtn(); }
		
		bottomAreaContainer.addComponent(positionBtn);
		
	}
	
	//create pause command button
	private void pauseCmdBtn() {
		pauseCmd = new PauseCmd(gw, this);
		pauseBtn = new CmdButton(pauseCmd);
		pauseBtn.setCommand(pauseCmd);
		
		addKeyListener('p', pauseCmd);
		
		pauseBtn.getAllStyles().setBorder(Border.createLineBorder(6, ColorUtil.BLACK));
		pauseBtn.setText("Play");
		gw.setPaused(true);
		
		bottomAreaContainer.addComponent(pauseBtn);
	}
	
	//Create accelerate command button
	private void accelerateCmdBtn() {
		accelerateCmd = new AccelerateCmd(gw);
		accelerateBtn = new CmdButton(accelerateCmd);
		accelerateBtn.setCommand(accelerateCmd);
		
		addKeyListener('a', accelerateCmd);
		
		
		accelerateBtn.getAllStyles().setMargin(Component.TOP, 250);
		accelerateBtn.getAllStyles().setBorder(Border.createLineBorder(6, ColorUtil.BLACK));
		
		
		leftAreaContainer.add(accelerateBtn);
	}
	
	//create brake command button
	private void brakeCmdBtn() {
		brakeCmd = new BrakeCmd(gw);
		brakeBtn = new CmdButton(brakeCmd);
		brakeBtn.setCommand(brakeCmd);
		
		addKeyListener('b', brakeCmd);
		
		brakeBtn.getAllStyles().setMargin(Component.TOP, 250);
		brakeBtn.getAllStyles().setBorder(Border.createLineBorder(6, ColorUtil.BLACK));
		
		rightAreaContainer.add(brakeBtn);
	}
	
	//create collide with flag command button
	/*private void collideWithFlagCmdBtn() {
		CollideWithFlagCmd flagCmd = new CollideWithFlagCmd(gw);
		CmdButton flagBtn = new CmdButton(flagCmd);
		flagBtn.setCommand(flagCmd);
		
		addKeyListener('1',flagCmd);
		addKeyListener('2',flagCmd);
		addKeyListener('3',flagCmd);
		addKeyListener('4',flagCmd);
		addKeyListener('5',flagCmd);
		addKeyListener('6',flagCmd);
		addKeyListener('7',flagCmd);
		addKeyListener('8',flagCmd);
		addKeyListener('9',flagCmd);

		flagBtn.getAllStyles().setBorder(Border.createLineBorder(6, ColorUtil.BLACK));
		
		bottomAreaContainer.addComponent(flagBtn);
	}*/
	
	//create collide with food station command button
	/*private void collideWithFoodStationCmdBtn() {
		CollideWithFoodStationCmd foodStationCmd = new CollideWithFoodStationCmd(gw);
		CmdButton foodStationBtn = new CmdButton(foodStationCmd);
		foodStationBtn.setCommand(foodStationCmd);
		
		addKeyListener('f', foodStationCmd);
		
		foodStationBtn.getAllStyles().setBorder(Border.createLineBorder(6, ColorUtil.BLACK));
		
		bottomAreaContainer.addComponent(foodStationBtn);
	}*/
	
	//create collide with spider command button
	/*private void collideWithSpiderCmdBtn() {
		CollideWithSpiderCmd spiderCmd = new CollideWithSpiderCmd(gw);
		CmdButton spiderBtn = new CmdButton(spiderCmd);
		spiderBtn.setCommand(spiderCmd);
		
		addKeyListener('g', spiderCmd);
		
		spiderBtn.getAllStyles().setBorder(Border.createLineBorder(6, ColorUtil.BLACK));
		
		bottomAreaContainer.addComponent(spiderBtn);
	}*/
	
	//Sound command button
	private void soundCmdBtn() {
		CheckBox checkSound = new CheckBox("Sound");
		SoundCmd sound = new SoundCmd(this.gw, checkSound);
		
		checkSound.setCommand(sound);
		checkSound.getAllStyles().setBgTransparency(125);
		checkSound.getAllStyles().setFgColor(ColorUtil.WHITE);
		checkSound.getAllStyles().setBgColor(ColorUtil.LTGRAY);
		
		sideMenu.addComponentToSideMenu(checkSound);
	}
	
	//create tick command button
	/*private void tickCmdBtn() {
		TickCmd tickCmd = new TickCmd(this.gw);
		CmdButton tickBtn = new CmdButton(tickCmd);
		tickBtn.setCommand(tickCmd);
		
		addKeyListener('t', tickCmd);
		
		tickBtn.getAllStyles().setBorder(Border.createLineBorder(6, ColorUtil.BLACK));
		
		bottomAreaContainer.addComponent(tickBtn);
	}*/
	
	//create turn left command button
	private void turnLeftCmdBtn() {
		turnLeftCmd = new TurnLeftCmd(gw);
		turnLeftBtn = new CmdButton(turnLeftCmd);
		turnLeftBtn.setCommand(turnLeftCmd);
		
		addKeyListener('l', turnLeftCmd);
		
		turnLeftBtn.getAllStyles().setBorder(Border.createLineBorder(6, ColorUtil.BLACK));
		
		leftAreaContainer.add(turnLeftBtn);
	}
	
	//create turn right command button
	private void turnRightCmdBtn() {
		turnRightCmd = new TurnRightCmd(gw);
		turnRightBtn = new CmdButton(turnRightCmd);
		turnRightBtn.setCommand(turnRightCmd);
		
		addKeyListener('r', turnRightCmd);
		
		turnRightBtn.getAllStyles().setBorder(Border.createLineBorder(6, ColorUtil.BLACK));
		
		rightAreaContainer.add(turnRightBtn);
	}
	
	
	
	//private void play() {
		/* code here to accept and execute user 
		 * commands that
		 * operate on the game world
		 * (refer to "Appendix - CN1 Notes*
		 * for accepting keyboard commands via a text
		 * field located on the form)
		 */
		
		/*Label myLabel = new Label("Enter a Command: ");
		this.addComponent(myLabel);
		final TextField myTextField = new TextField();
		this.addComponent(myTextField);
		this.show();
		
		myTextField.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent evt) {
				
				String sCommand = myTextField.getText().toString();
				myTextField.clear();
				if(sCommand.length() != 0)
					switch (sCommand.charAt(0)) {
						case 'a':
							gw.accelerate();
							break;
						//add code to handle rest of the commands
						case 'b':
							gw.brake();
							break;
						//Lower case L
						case 'l':
							gw.turnLeft();
							break;
						case 'r':
							gw.turnRight();
							break;
						case 'f':
							gw.foodStationCollision();
							break;
						case 'g':
							gw.spiderCollision();
							break;
						case 't':
							gw.worldTick();
							break;
						case 'd':
							gw.display();
							break;
						case 'm':
							gw.map();
							break;
						case 'x':
							System.out.println("Would you like to exit the game? y/n");
							break;
						case 'y':
							gw.gameExit(true);
							break;
						case 'n':
							gw.gameExit(false);
							break;
						case '1':
							gw.flagReached(1);
							break;
						case '2':
							gw.flagReached(2);
							break;
						case '3':
							gw.flagReached(3);
							break;
						case '4':
							gw.flagReached(4);
							break;
						case '5':
							gw.flagReached(5);
							break;
						case '6':
							gw.flagReached(6);
							break;
						case '7':
							gw.flagReached(7);
							break;
						case '8':
							gw.flagReached(8);
							break;
						case '9':
							gw.flagReached(9);
							break;
						default :
							System.err.println("Incorrect command. Try Again.");
							break;
					} //switch
			} //actionPerformed
		} //new ActionListener()
		); //addActionListener
	} //play*/

}
