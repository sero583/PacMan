package sero583.pacman;

import java.awt.Color;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

public class Level {
	public static final int DEFAULT_VELOCITY = 1;
	public static final int DEFAULT_X = 64;
	public static final int DEFAULT_Y = 64;
	public static final Color DEFAULT_COLOR = Color.RED;
	public static final int DEFAULT_BOT_COUNT = 4;
	
	public static final int COIN_SCORE = 20;
	
	public static int ENTITY_COUNT = 1;
	
	private String name;
	private int width;
	private int height;
	
	private Player player;
	private Set<Bot> bots;
	private Map<Vector2, LevelObject> objects;
	
	private Timer timer;
	private TickTask task;
	
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
		
		this.timer = new Timer();
		this.task = new TickTask(this);
		this.timer.scheduleAtFixedRate(this.task, 0L, 20L);
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
		for(LevelObject object : this.objects.values()) {
			//bot.processMovement(this);
			if(object instanceof Player) {
				Player p = (Player) object;
				
				int velX = p.getVelX();
				int velY = p.getVelY();
				
				LevelObject collisionPartner = null;
				if(velX>0) {
					p.setVelX(--velX);
					//go right
					collisionPartner = this.getLevelObjectAt(p.add(1));
				} else if(velX<0) {
					p.setVelX(++velX);
					//go left
					collisionPartner = this.getLevelObjectAt(p.substract(1));
				}
				
				if(velY>0) {
					p.setVelY(--velY);
					//go up
					collisionPartner = this.getLevelObjectAt(p.add(0, 1));
				} else if(velY<0) {
					p.setVelY(++velY);
					//go down
					collisionPartner = this.getLevelObjectAt(p.substract(0, 1));
				}
				
				if(collisionPartner!=null) {
					if(collisionPartner instanceof Bot) {
						//dead
					} else if(collisionPartner instanceof Wall) {
						//no move
					} else if(collisionPartner instanceof Path) {
						//move and check for coins
						Path path = (Path) collisionPartner;
						if(path.hasCoin()==true) {
							Main.instance.addScore(COIN_SCORE);
							path.setCoin(false);
						}
					}
				} else {
					Main.instance.launcher.getLogger().error("Player moved to invalid area!");
				}
			} else if(object instanceof Bot) {
				Bot b = (Bot) object;
				
				//do AI stuff here
			}/* else if(object instanceof Path) {
				Path p = (Path) object;
			} TODO: Clarify if this is needed */
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
	
	public boolean entityExists(int id) {
		return this.getEntity(id) != null;
	}
}
