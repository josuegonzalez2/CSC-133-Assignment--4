package com.mycompany.a4;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.geom.Dimension;

import java.util.Observable;
import java.util.Random;

public class GameWorld extends Observable {
	private GameObjectCollection objects;
	
	private int elapsedTime, playerLives;
	private boolean sound, pause, pressed;
	private double height, width;
	private Sound flagCollision, fsCollision, spiderCollision;
	private BGSound background;
	
	public GameWorld() {
		//code here to create initial game objects/setup
		this.elapsedTime = 0;
		this.playerLives = 3;
		this.sound = false;
		this.pause = false;
		this.pressed = false;
		
		//create collection of objects
		objects = new GameObjectCollection();
	}
	
	public void init() { createObjects(); }
	
	/* additional methods here to manipulate world objects 
	 * and related game state data 
	 */
	public GameObjectCollection getObjects() { return objects; }
	public BGSound getBackground() { return background; }
	public int getElapsedTime() { return elapsedTime; }
	public int getPlayerLives() { return playerLives; }
	public boolean getSound() { return sound; }
	public boolean getPaused() { return pause; }
	public double getMapWidth() { return width; }
	public double getMapHeight() { return height; }
	public boolean getPressed() { return pressed; }
	
	public void setElapsedTime(int t) {
		this.elapsedTime = t;
		this.setChanged();
	}
	
	public void setMapWidth(double width) {
		this.width = width;
		setChanged();
	}
	
	public void setMapHeight(double height) {
		this.height = height;
		setChanged();
	}
	
	public void setSound(boolean sound) {
		this.sound = sound;
		setChanged();
	}
	
	public void setPaused(boolean pause) {
		this.pause = pause;
		setChanged();
	}
	
	public void setPlayerLives(int playerLives) {
		this.playerLives = playerLives;
		setChanged();
	}
	
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
		setChanged();
	}
	
	//create gameObjects
	public void createObjects() {
		Random r = new Random();		
		
		Spider spider = new Spider(25, ColorUtil.rgb(0, 0, 0), (float)r.nextInt(1001), (float)r.nextInt(1001));
		Spider spider2 = new Spider(20, ColorUtil.rgb(0, 0, 0), (float)r.nextInt(1001), (float)r.nextInt(1001));
		
		FoodStation foodStation = new FoodStation(15, ColorUtil.rgb(0, 255, 0), (float)350.8, (float)350.6);
		FoodStation foodStation2 = new FoodStation(20, ColorUtil.rgb(0, 255, 0), (float)900.0, (float)700.4);
		
		Flags flag1 = new Flags(1, 30, ColorUtil.rgb(0, 0, 255), (float)r.nextInt(1001),(float)r.nextInt(1001));
		Flags flag2 = new Flags(2, 30, ColorUtil.rgb(0, 0, 255), (float)r.nextInt(1001),(float)r.nextInt(1001));
		Flags flag3 = new Flags(3, 30, ColorUtil.rgb(0, 0, 255), (float)r.nextInt(1001),(float)r.nextInt(1001));
		Flags flag4 = new Flags(4, 30, ColorUtil.rgb(0, 0, 255), (float)r.nextInt(1001),(float)r.nextInt(1001));
		Flags flag5 = new Flags(5, 30, ColorUtil.rgb(0, 0, 255), (float)r.nextInt(1001),(float)r.nextInt(1001));
		Flags flag6 = new Flags(6, 30, ColorUtil.rgb(0, 0, 255), (float)r.nextInt(1001),(float)r.nextInt(1001));
		Flags flag7 = new Flags(7, 30, ColorUtil.rgb(0, 0, 255), (float)r.nextInt(1001),(float)r.nextInt(1001));
		Flags flag8 = new Flags(8, 30, ColorUtil.rgb(0, 0, 255), (float)r.nextInt(1001),(float)r.nextInt(1001));
		Flags flag9 = new Flags(9, 30, ColorUtil.rgb(0, 0, 255), (float)r.nextInt(1001),(float)r.nextInt(1001));
		
		//Ant ant = new Ant(40, ColorUtil.rgb(255, 0, 0), flag1.getValueX(), flag1.getValueY());
		float antx = flag1.getLocation().getX();
		float anty = flag1.getLocation().getY();
		
		Ant ant = Ant.getAnt();
		
		ant.setLocation(antx, anty);
		
		//add objects
		objects.add(ant);
		objects.add(spider);
		objects.add(spider2);
		objects.add(foodStation);
		objects.add(foodStation2);
		objects.add(flag1);
		objects.add(flag2);
		objects.add(flag3);
		objects.add(flag4);
		objects.add(flag5);
		objects.add(flag6);
		objects.add(flag7);
		objects.add(flag8);
		objects.add(flag9);
		
		//update
		setChanged();
	}
	
	/**
	 * create sounds with selected files.
	 */
	public void createSounds() {
		background = new BGSound("background.wav");
		
		flagCollision = new Sound("flagCollision.wav");
		fsCollision = new Sound("foodStationCollision.wav");
		spiderCollision = new Sound("spiderCollision.wav");
	}
	
	public Flags findFlags() {
		IIterator iterator = objects.getIterator();
		int pointer = 0;
		int seqNum = 0;
		Flags flag = new Flags();
		Flags f = new Flags();
		
		while(iterator.hasNext()) {
			GameObject obj = (GameObject) iterator.getNext();
			
			if(obj instanceof Flags) {
				f = (Flags) objects.getObjAt(pointer);
				int temp = f.getSequenceNumber();
				if(seqNum < temp) {
					flag = (Flags) objects.getObjAt(pointer);
					if(!(iterator.hasNext())) {					
						return flag;
					}
					seqNum = temp;
				}
			}
			pointer++;
		}
		return null;
	}
	
	public Flags getLastFlag() {
		for(int i=objects.getSize(); i >= 1; i--) {			
			GameObject obj = objects.getObjAt(i-1);
						
			if(obj instanceof Flags) {
				return (Flags) obj;
			}
		}
		return null;
	}
	
	private void checkCollision() {
		IIterator iter = objects.getIterator();
		ICollider currentObject;
		IIterator secondIter;
		
		while (iter.hasNext()) {
			currentObject = (ICollider) iter.getNext();
			secondIter = objects.getIterator();
			ICollider otherObject;

				while (secondIter.hasNext()) {
					otherObject = (ICollider) secondIter.getNext();
					if (currentObject != otherObject) {
						if (currentObject.collidesWith((GameObject) otherObject)) {
							currentObject.handleCollision((GameObject) otherObject, this);
						}
					}
				}
			}
		this.setChanged();
	}
	
	//ant movement methods
	/**
	 * The ant accelerates by a tiny amount
	 */
	public void accelerate() {
		Ant ant = Ant.getAnt();
		ant.accelerate();
		ant.checkMaxSpeed();
		setChanged();
	}
	
	/**
	 * The ant decreases its speed by a tiny amount
	 */
	public void brake() {
		Ant ant = Ant.getAnt();
		ant.brake();
		setChanged();
	}
	 
	/**
	  * Turns the ant to the left by 5 degrees
	  */
	public void turnLeft() {
		Ant ant = Ant.getAnt();
		ant.steer(true);
		setChanged();
	}
	  
	  /**
	   * Turns the ant to the right by 5 degrees
	   */
	public void turnRight() {
		Ant ant = Ant.getAnt();
		ant.steer(false);
		setChanged();
	}
	   
	//gameObject Collisions
	/**
	 * Ant has collided with a food station
	 * - Ant's food level adds capacity of food station
	 * - Food Station:
	 * 	+ capacity decreases
	 * 	+ size decreases
	 * 	+ color fades
	 * - New food station is created randomly
	 */
	public void foodStationCollision(FoodStation foodStation) {
		//find game objects
		Ant ant = Ant.getAnt();
		
		if(foodStation.getCapacity() != 0) {
			//Ants food level is increased according to food station capacity
			ant.setFoodLevel(ant.getFoodLevel() + foodStation.getCapacity());
		
			//decreases food station attributes
			foodStation.setCapacity(0);
			//foodStation.setColor(144,238,144);
			int blue = foodStation.getBlue();
			int red = foodStation.getRed();
			int green = foodStation.getGreen();
			
			foodStation.setColor(red - 5, green - 5, blue);
			
			//create new food station with random size and location
			Random r = new Random();
			int cap = r.nextInt(16) + 5;
			//int objId = foodStation.getObjId();
		
			//create new food station
			FoodStation newFS = new FoodStation(cap, ColorUtil.rgb(0, 255, 0), (float)r.nextInt(700), (float)r.nextInt(700));
			objects.add(newFS);
			
			if (this.getSound()) {
				fsCollision.play();
			}
		}
		//update game
		setChanged();
	}
	   
	/**
	  * The ant has collided with a spider
	  * - health level decreases
	  * - fade color of ant
	  * - reduce speed of ant
	  * - if ant can no longer move then ant looses
	  * - if game ends the player loses a life
	  * - if player loses all three lives then player loses 
	  * - Shock wave is created
	  */
	public void spiderCollision(Spider spider) {
		Ant ant = Ant.getAnt();
		
		ant.tookDamage();
		System.out.println("You have hit a spider!");
		
		if (this.getSound()) {
			spiderCollision.play();
		}
		
		checkLives();
		setChanged();
		
		//create shock wave
		//createShockWave(ant);
	}
	
	/**
	 * The ant has reached a flag
	 * - If ant reaches the last flag then end the game
	 * - If flag reached is the next one after lastFlagReached
	 * 		then setLastFlagReached to flagNum
	 * 
	 * @param flagNum
	 */
	public void flagReached(int flagNum) {
		Ant ant = Ant.getAnt();
		Flags flag = getLastFlag();
						
		int flagReached = ant.getLastFlagReached();
		int seqNum = flag.getSequenceNumber();

		if(flagReached + 1 == flagNum) {
			
			if (this.getSound()) {
				flagCollision.play();
			} 
			if(flagNum == seqNum) {
				System.out.println("Congratulations, you won!");
				System.exit(0);
			}
			if(flagNum - 1 == flagReached) {
				ant.setLastFlagReached(flagNum);
			}
		}
		setChanged();
	}
	
	/**
	 * The ant has collided with a spider
	 * - New Shock wave's initial location is equal to
	 * 		ant's location
	 * - Randomly generated constant heading and speed values
	 * - (The speed should be slow enough that the shock wave
	 * 		is seen on the display at least for a short time).
	 * - Collisions between shock waves and other objects have no effect
	 * 		the shock wave continues moving in the world
	 * - shock waves have a maximum lifetime
	 * 		* Dissipate and are removed from the world
	 * 		* Maximum lifetime should be equal to 
	 * 			the amount of time it takes the shock wave to move
	 * 			all the way across the world window which exists
	 * 			at the time it is created
	 */
	public void createShockWave(Ant ant) {
		Random r = new Random();
		
		int swColor = ColorUtil.rgb(114, 200, 184);
		int swSpeed = r.nextInt();
		int swSize = r.nextInt();
		
		float antLocX = ant.getLocation().getX();
		float antLocY = ant.getLocation().getY();
		
		//size, speed, color, heading, x, y
		ShockWave shockWave = new ShockWave(swSize, swSpeed, swColor, 0, antLocX, antLocY);
		//shockWave.draw(g, null, null)
		objects.add(shockWave);
	}
	
	public void checkLives() {
		Ant ant = Ant.getAnt();
		
		if (playerLives == 0) {
			System.out.println("Game over! You have no more lives. \nTime passed: " + elapsedTime);
			System.exit(0);

		}
		
		if (ant.getHealthLevel() == 0 || ant.getFoodLevel() == 0) {
			
			this.setPlayerLives(getPlayerLives() - 1);
			this.elapsedTime = 0;
			objects.clearObjs();
			ant.clear();
			init();

		}
		this.setChanged();
	}  
	
	//game attributes
	/**
	  * The game world has ticked
	  * - ant moves
	  * - food level decreases according to consumptionRate
	  * - elapsedTime increases
	  * - draw object figures every tick
	  */
	public void worldTick(int elapsedTime, Dimension dCmpSize) {
		Ant ant = Ant.getAnt();
		
		setMapWidth(dCmpSize.getWidth());
		setMapHeight(dCmpSize.getHeight());
		
		if(this.elapsedTime == 0) {
			checkBounds(dCmpSize);
		}
		
		ant.move(elapsedTime, dCmpSize);
		ant.setFoodLevel(ant.getFoodLevel() - ant.getConsumptionRate());
		
		IIterator iter = objects.getIterator();
		Random r = new Random();
		GameObject obj;
		
		while(iter.hasNext()) {
			obj = (GameObject) iter.getNext();
			
			if(obj instanceof Spider) {
				Spider spider = (Spider) obj;
				spider.move(elapsedTime, dCmpSize);
			} 
		}
		
		if(getSound()) {
			background.play();
		}
		
		checkCollision();		
		setElapsedTime(this.elapsedTime+1);
		setChanged();
	}
	
	public void checkBounds(Dimension dCmpSize) {
		Ant ant = Ant.getAnt();
		
		IIterator iter = objects.getIterator();
		Random r = new Random();
		GameObject obj;
		//double width = getMapWidth();
		//double height = getMapHeight();
		double width = dCmpSize.getWidth();
		double height = dCmpSize.getHeight();
		
		//double mapArea = width * height;
		
		while(iter.hasNext()) {
			obj = iter.getNext();
			float x = obj.getLocation().getX();
			float y = obj.getLocation().getY();
			
			if(obj instanceof Flags) {
				if(x > 0 ||  x < width) {
					int w = r.nextInt((((int) width - 10) - 10) + 1) + 10;
					int h = r.nextInt((((int) height - 10) - 10) +1) + 10;
					obj.setLocation(w, h);
					
					if(((Flags) obj).getSequenceNumber() == 1) {
						ant.setLocation(obj.getLocation().getX(), obj.getLocation().getY());
					}
				}
				
				if(x > 0 ||  x < height) {
					int w = r.nextInt((((int) width - 10) - 10) + 1) + 10;
					int h = r.nextInt((((int) height - 10) - 10) +1) + 10;
					obj.setLocation(w, h);
					
					if(((Flags) obj).getSequenceNumber() == 1) {
						ant.setLocation(obj.getLocation().getX(), obj.getLocation().getY());
					}
				}
			}
			
			if(obj instanceof FoodStation) {		
				if(x > 0 ||  x < width) {
					int w = r.nextInt((((int) width - 10) - 10) + 1) + 5;
					int h = r.nextInt((((int) height - 10) - 10) + 1) + 10;
					obj.setLocation(w, h);
				}
				
				if(x > 0 ||  x < height) {
					int w = r.nextInt((((int) width - 10) - 10) + 1) + 10;
					int h = r.nextInt((((int) height - 10) - 10) + 1) + 10;
					obj.setLocation(w, h);
				}
			}
			if(obj instanceof Spider) {			
				if(x > 0 ||  x < width) {
					int w = r.nextInt((((int) width - 10) - 10) + 1) + 10;
					int h = r.nextInt((((int) height - 10) - 10) + 1) + 10;
					obj.setLocation(w, h);
				}
				
				if(x > 0 ||  x < height) {
					int w = r.nextInt((((int) width - 10) - 10) + 1) + 10;
					int h = r.nextInt((((int) height - 10) - 10) +1) + 10;
					obj.setLocation(w, h);
				}
			}
		}
	}
	   
	/**
	  * Displays all current game values
	  * - Number of lives left
	  * - The current clock value (elapsedTime)
	  * - The highest flag number the ant has reached
	  * - The ants current food level
	  * - The ants current health level
	  */
	public void display() {
		Ant ant = Ant.getAnt();
		
		System.out.println("\nLives: " + playerLives
				+ "\nElapsedTime:  " + elapsedTime
				+ "\nHighest flag reached: " + ant.getLastFlagReached()
				+ "\nFood level: " + ant.getFoodLevel()
				+ "\nHealth Level: " + ant.getHealthLevel());		
	}
	   
	/**
	  * Output the map
	  * - displays current objects in the game and their values
	  */
	public void map() {
		IIterator iterator = objects.getIterator();
		
		System.out.println("\n-----------------------------------------------------------------------------");
		
		while(iterator.hasNext()) {
			GameObject obj = (GameObject) iterator.getNext();
			if(obj instanceof Ant) {
				System.out.println(obj.toString());
			} else if(obj instanceof Spider) {
				System.out.println(obj.toString());
			} else if(obj instanceof FoodStation) {
				System.out.println(obj.toString());
			} else if(obj instanceof Flags) {
				System.out.println(obj.toString());
			}
		}
		
		System.out.println("-----------------------------------------------------------------------------\n");
	}
	   
	/**
	  * Exits and ends the game
	  * 
	  * @param exit
	  */
	public void gameExit(boolean exit) {
		if(exit == true) {
			System.out.println("Thank you for playing!");
			System.exit(0);
		}
	}
}
