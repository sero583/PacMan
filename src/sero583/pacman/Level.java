package sero583.pacman;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

import sero583.pacman.launcher.ScreenInfo;

public class Level {
	public static final long TPS = 20;
	public static final double DEFAULT_VELOCITY = 0.5;
	public static final int DEFAULT_X = 15;
	public static final int DEFAULT_Y = 15;
	public static final Color DEFAULT_PLAYER_COLOR = Color.RED;
	public static final int DEFAULT_BOT_COUNT = 4;
	
	public static final double DEFAULT_PLAYER_X = 1;
	public static final double DEFAULT_PLAYER_Y = 1;
	
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
		
		this.objects = new HashMap<Vector2, LevelObject>();
		this.bots = new HashSet<Bot>();
		
		this.timer = new Timer();
	}
	
	public void startTickTask() {
		this.task = new TickTask(this);
		this.timer.scheduleAtFixedRate(this.task, 0L, TPS);
	}
	
	public Main getMainInstance() {
		return this.main;
	}
	
	public Player createPlayer(String name) {
		return this.createPlayer(name, DEFAULT_PLAYER_COLOR);
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
			Vector2 gen = this.generatePosInRange();
			
			while(this.getEntityAt(gen)!=null) {
				//keep generating..
				gen = this.generatePosInRange();
			}
			Bot bot = new Bot(ENTITY_COUNT++, Color.GRAY, gen.getX(), gen.getY());
			this.bots.add(bot);
		}
	}
	
	public Vector2 generatePosInRange() {
		int x = Utils.generateNumber(1, this.getWidth()-1);
		int y = Utils.generateNumber(1, this.getHeight()-1);
		return new Vector2(x, y);
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
		}/* else {
			this.getMainInstance().launcher.getLogger().error("Player moved to invalid area!");
		}*/
		
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
				//this.getMainInstance().launcher.getLogger().info("XandY: " + x + ":" + y);
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
				//this.getMainInstance().launcher.getLogger().info("XandY: " + x + ":" + y);
				
				this.putLevelObject(path, true);
			}
		}
		
		this.player = this.createPlayer("PacMan");
		this.player.setX(DEFAULT_PLAYER_X);
		this.player.setY(DEFAULT_PLAYER_Y);
		
		this.paths = this.getPaths();
		this.generateBots();
		
		this.getMainInstance().launcher.startThread();
	}
	
	private double chunkSizeX;
	private double chunkSizeY;

	public void onSizeUpdate() {
		ScreenInfo screen = this.getMainInstance().launcher.getScreenInfo();
		this.getMainInstance().launcher.getLogger().info(screen.toString());

		this.chunkSizeX = screen.divideWidth(this.getWidth());
		this.chunkSizeY = screen.divideHeight(this.getHeight());
	}
	
	public void grahicsRender(Graphics g) {
		this.getMainInstance().launcher.getLogger().info("Width:" + chunkSizeX + ", Height: " + chunkSizeY);
		/*int i = 0;
		for(LevelObject object : this.objects.values()) {
			double startX = chunkSizeX * object.getX();
			double startY = chunkSizeY * object.getY();
			
			double endX = chunkSizeX * (object.getX()+1);
			double endY = chunkSizeY * (object.getY()+1);
			
			String data = "RESULT Index: " + i + ": StartX: " + startX + ", StartY: " + startY + ", EndX: " + endX + ", EndY: " + endY;
			if(i<100) this.getMainInstance().launcher.getLogger().info(data);
			String his = "HISTORY Index: " + i + ": ObjectX: " + object.getX() + ", ObjectY: " + object.getY() + ", ChunkX: " + chunkSizeX + ", ChunkY: " + chunkSizeY;
			if(i<100) this.getMainInstance().launcher.getLogger().info(his);
			
			if(object.hasColor()==true) {
				g.setColor(object.getColor());
				g.fillRect((int) startX, (int) startY, (int) endX, (int) endY);
			}
			i++;
		}
		this.getMainInstance().launcher.getLogger().info("Level object count:" + objects.values().size());
		
		for(Bot bot : this.bots) {
			double startX = chunkSizeX * bot.getX();
			double startY = chunkSizeY * bot.getY();
			double endX = chunkSizeX * (bot.getX()+1);
			double endY = chunkSizeY * (bot.getY()+1);
			g.setColor(bot.getColor());
			g.fillRect((int) startX, (int) startY, (int) endX, (int) endY);
		}*/
		
		//player here
		double startX = chunkSizeX * this.player.getX();
		double startY = chunkSizeY * this.player.getY();
		double endX = chunkSizeX * (this.player.getX()+1);
		double endY = chunkSizeY * (this.player.getY()+1);
		
		
		
		g.setColor(this.player.getColor());
		g.fillRect((int) startX, (int) startY, (int) endX, (int) endY);
		
		String calc = "Calculation:" + "StartX: " + this.player.getX() + "*" + chunkSizeX + ", StartY: " + this.player.getY() + "*" + chunkSizeY +
					   ", EndX: " + endX + "*" + chunkSizeX + ", EndY: " + (this.player.getX()+1) + "*" + (this.player.getY()+1);
		this.getMainInstance().launcher.getLogger().info(calc);
		String res = "Result:" + "StartX: " + startX +  ", StartY: " + startY + ", EndX: " + endX + ", EndY: " + endY;
		
		
		//g.drawRect(0, 0, 100, 100);
		
		
		/*try {
			Thread.currentThread().sleep(5000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
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
		
		for(Map.Entry<Vector2, LevelObject> entry : this.objects.entrySet()) {
			Vector2 pos = entry.getKey();
			LevelObject obj = entry.getValue();
			
			if(pos.equals(object.toVector2())==true) {
				if(override==true) {
					this.objects.remove(pos); //idk if necessary but putted it in there anyway
					break;
				} else return false;
			}
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
