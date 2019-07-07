package sero583.pacman;

import java.awt.Color;
import java.util.Map;
import java.util.Set;

public class Level {
	public static final int DEFAULT_X = 64;
	public static final int DEFAULT_Y = 64;
	public static final Color DEFAULT_COLOR = Color.RED;
	public static final int DEFAULT_BOT_COUNT = 4;
	
	public static int ENTITY_COUNT = 1;
	
	private String name;
	private int width;
	private int height;
	
	private Player player;
	private Set<Bot> bots;
	private Map<Vector2, LevelObject> objects;
	
	public Level() {
		this("default", DEFAULT_X, DEFAULT_Y);
	}
	
	public Level(int width, int height) {
		this("default", width, height);
	}
	
	public Level(String name, int width, int height) {
		this.name = name;
		
		this.width = width;
		this.height = height;
	}
	
	public Player createPlayer(String name) {
		return this.createPlayer(name, DEFAULT_COLOR);
	}
	
	public Player createPlayer(String name, Color color) {
		return this.createPlayer(name, color, -1, -1);
	}
	
	public Player createPlayer(String name, Color color, int x, int z) {
		return new Player(name, ENTITY_COUNT++, color, x, z);
	}
	
	public void generateBots() {
		this.generateBots(DEFAULT_BOT_COUNT);
	}
	
	public void generateBots(int count) {
		for(int i = 0; i < count; i++) {
			
		}
	}
	
	public void tickBots() {
		for(Bot bot : this.bots) {
			//bot.processMovement(this);
		}
	}
	
	public void setLevelObjects(Map<Vector2, LevelObject> objects) {
		this.objects = objects;
	}
	
	public void setDefaultLevelObjects() {
		Wall wall = new Wall(Color.BLUE, -1, -1);
		for(int x = 0; x < this.getWidth(); x++) {
			for(int y = 0; y < this.getHeight(); y++) {
				wall.setX(x);
				wall.setY(y);
				this.putLevelObject(wall, true, false);
			}
		}
		
		this.player = this.createPlayer("PacMan");
		this.player.setX(32);
		this.player.setY(32);
		this.putLevelObject(this.player, true, false);
		this.update();
	}
	
	public boolean putLevelObject(LevelObject object) {
		return this.putLevelObject(object, true, true);
	}
	public boolean putLevelObject(LevelObject object, boolean override) {
		return this.putLevelObject(object, override, true);
	}
	
	//Returns false if invalid vector2 or wont override
	public boolean putLevelObject(LevelObject object, boolean override, boolean callUpdate) {
		if(object.isValidVector()==false) {
			Main.instance.launcher.getLogger().error("Tried to register LevelObject (" + object.getClass().getSimpleName() + ") with invalid Vector2!");
			return false;
		}
		
		if(this.objects.containsKey(object.toVector2())==true&&override==false) {
			//Main.instance.launcher.getLogger().error("Tried to register LevelObject (" + object.getClass().getSimpleName() + "), one already existent and overriding was set to false.");
			return false;
		}
		
		this.objects.put(object.toVector2(), object);
		
		if(callUpdate==true) {
			this.update();
		}
		
		return true;
	}
	
	public void update() {
		Main.instance.launcher.onUpdate(this);
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public String getName() {
		return this.name;
	}
	
	
	public Player getPlayer() {
		return this.player;
	}
	
	public Set<Bot> getBots() {
		return this.bots;
	}
}
