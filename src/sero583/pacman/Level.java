package sero583.pacman;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

public class Level {
	public static final double DEFAULT_VELOCITY = 0.5;
	public static final int DEFAULT_X = 64;
	public static final int DEFAULT_Y = 64;
	public static final Color DEFAULT_COLOR = Color.RED;
	public static final int DEFAULT_BOT_COUNT = 4;
	
	public static final int COIN_SCORE = 20;
	
	public static int ENTITY_COUNT = 1;
	
	private Main main;
	
	private String name;
	private int width;
	private int height;
	
	private Player player;
	private Set<Bot> bots;
	private Map<Vector2, LevelObject> objects; //no bots inside
	private Map<Vector2, Path> paths;
	
	private Timer timer;
	private TickTask task;
	
	public Level(Main main) {
		this(main, "default", DEFAULT_X, DEFAULT_Y);
	}
	
	public Level(Main main, int width, int height) {
		this(main, "default", width, height);
	}
	
	public Level(Main main, String name, int width, int height) {
		this.main = main;
		
		this.name = name;
		
		this.width = width;
		this.height = height;
		
		this.timer = new Timer();
		this.task = new TickTask(this);
		this.timer.scheduleAtFixedRate(this.task, 0L, 20L);
	}
	
	public Main getMainInstance() {
		return this.main;
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
	
	public void tick() {
		double velX = this.player.getVelX();
		double velY = this.player.getVelY();
		
		Object collisionPartner = null;
		Vector2 newPos = null;
		
		//TODO: test if first Left-right and then up-down checks should be made
		if(velX>0) {
			newPos = this.player.add(DEFAULT_VELOCITY);
		} else if(velX<0) {
			newPos = this.player.substract(DEFAULT_VELOCITY);
		}
		
		if(velY>0) {
			newPos = this.player.add(0, DEFAULT_VELOCITY);
		} else if(velY<0) {
			newPos = this.player.substract(0, DEFAULT_VELOCITY);
		}
		collisionPartner = this.getThingAt(newPos);
		
		if(collisionPartner!=null) {
			if(collisionPartner instanceof Bot) {
				this.endGame();
			} else if(collisionPartner instanceof Wall) {
				//no move
			} else if(collisionPartner instanceof Path) {
				//move and check for coins
				Path path = (Path) collisionPartner;
				if(path.hasCoin()==true) {
					this.getMainInstance().addScore(COIN_SCORE);
					path.setCoin(false);
				}
				this.player.setVector2(newPos);
			}
		} else {
			this.getMainInstance().launcher.getLogger().error("Player moved to invalid area!");
		}
		
		for(Bot bot : this.bots) {
			//do AI stuff here
		}
	}
	
	public void endGame() {
		//TODO: unschedule etc.
		this.getMainInstance().launcher.getLogger().info("Here we have to stop. Score made = " + this.getMainInstance().getScore());
	}
	
	public void setLevelObjects(Map<Vector2, LevelObject> objects) {
		this.objects = objects;
		this.paths = this.getPaths();
	}
	
	public void setDefaultLevelObjects() {
		Wall wall = new Wall(Color.BLUE, -1, -1);
		for(int x = 0; x < this.getWidth(); x++) {
			for(int y = 0; y < this.getHeight(); y++) {
				wall.setX(x);
				wall.setY(y);
				this.putLevelObject(wall, true);
			}
		}
		
		//TODO: enhance path generation paths
		Path path = new Path(Color.RED, -1, -1);
		for(int x = 1; x < this.getWidth()-1; x++) {
			for(int y = 1; y < this.getHeight()-1; y++) {
				path.setX(x);
				path.setY(y);
				
				this.putLevelObject(path, true);
			}
		}
		
		this.player = this.createPlayer("PacMan");
		this.player.setX(32);
		this.player.setY(32);
		
		this.paths = this.getPaths();
		
		this.getMainInstance().launcher.startRenderer();
	}
	
	public boolean putLevelObject(LevelObject object) {
		return this.putLevelObject(object, true);
	}
	
	//Returns false if invalid vector2 or wont override
	public boolean putLevelObject(LevelObject object, boolean override) {
		
		if(object.isValidVector()==false) {
			this.getMainInstance().launcher.getLogger().error("Tried to register LevelObject (" + object.getClass().getSimpleName() + ") with invalid Vector2!");
			return false;
		}
		
		if(this.objects.containsKey(object.toVector2())==true&&override==false) {
			//this.getMainInstance().launcher.getLogger().error("Tried to register LevelObject (" + object.getClass().getSimpleName() + "), one already existent and overriding was set to false.");
			return false;
		}
		
		this.objects.put(object.toVector2(), object);
		return true;
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
	
	public Entity getEntity(int id) {
		if(this.player.getId()==id) {
			return this.player;
		} else {
			for(Bot bot : this.bots) {
				if(bot.getId()==id) {
					return bot;
				}
			}
		}
		return null;
	}
	
	public LevelObject getLevelObjectAt(Vector2 vector) {
		for(LevelObject object : this.objects.values()) {
			if(object.equals(vector)==true) {
				return object;
			}
		}
		return null;
	}
	
	public Entity getEntityAt(Vector2 vector) {
		if(this.player.equals(vector)==true) {
			return this.player;
		} else {
			for(Bot bot : this.bots) {
				if(bot.equals(vector)==true) {
					return bot;
				}
			}
		}
		return null;
	}
	
	//Shouldnt be used, only for caching since path will only change at new generation
	public Map<Vector2, Path> getPaths() {
		Map<Vector2, Path> map = new HashMap<Vector2, Path>();
		
		for(LevelObject obj : this.objects.values()) {
			if(obj instanceof Path) {
				map.put(obj.toVector2(), (Path) obj);
			}
		}
		return map;
	}
	
	public Object getThingAt(Vector2 vector) {
		//entites have more priority
		Entity ent = this.getEntityAt(vector);
		if(ent==null) {
			return this.getLevelObjectAt(vector);
		}
		return ent;
	}
	
	public boolean entityExists(int id) {
		return this.getEntity(id) != null;
	}
}
