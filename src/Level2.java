import game2D.Animation;
import game2D.Sprite;
import game2D.TileMap;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Level2 {
	
	Animation dinoLeft, dinoRight, goblinLeft, goblinRight, tigerLeft, tigerRight, cave,
	frostBoltLeft, frostBoltRight, fireBoltLeft, fireBoltRight, deadGoblinLeft, 
	deadGoblinRight, deadDinoLeft, deadDinoRight, deadTiger, orcWalk, orcHit, orcDeath;
	
	Collision collision = new Collision();
	
	ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    ArrayList<Sprite> weaponSprites = new ArrayList<Sprite>();
    Sprite backgroundCave, backgroundCave2;
    long[] shootingSprites = new long[4];
    boolean[] deadSprites = new boolean[5];
    Sprite boss;
    int bossDeadCount = 4;
    boolean bossDead = false;
    int loop = 0;
    
    TileMap tmap = new TileMap();
    
	private void createAnimation(Animation animation, String file, int frameDuration) {
    	String currentLine;
    	try {
    		BufferedReader newReader = new BufferedReader(new FileReader("animations/" + file));
    		currentLine = newReader.readLine();    
    	    String[] animationArray = currentLine.split(", ");
 			for (int j = 0; j < animationArray.length; j ++) {
 				animation.addFrame(new ImageIcon(animationArray[j]).getImage(), frameDuration);
 			}
 			newReader.close();
    	}
    	catch (Exception e) {
    		
    	}
    }
	
	public void initialise() {
		Sprite s = null;
		
		tmap.loadMap("maps/level2", "map.txt");
		
		dinoLeft = new Animation();
		createAnimation(dinoLeft, "level2/dinoLeft.txt", 300);
		dinoRight = new Animation();
		createAnimation(dinoRight, "level2/dinoRight.txt", 300);
		goblinLeft = new Animation();
		createAnimation(goblinLeft, "level2/goblinLeft.txt", 300);
		goblinRight = new Animation();
		createAnimation(goblinRight, "level2/goblinRight.txt", 300);
		tigerLeft = new Animation();
		createAnimation(tigerLeft, "level2/tigerLeft.txt", 300);
		tigerRight = new Animation();
		createAnimation(tigerRight, "level2/tigerRight.txt", 300);
		cave = new Animation();
		createAnimation(cave, "level2/cave.txt", 300);
		frostBoltLeft = new Animation();
		createAnimation(frostBoltLeft, "level2/frostBoltLeft.txt", 1000);
		frostBoltRight = new Animation();
		createAnimation(frostBoltRight, "level2/frostBoltRight.txt", 1000);
		fireBoltLeft = new Animation();
		createAnimation(fireBoltLeft, "level2/fireBoltLeft.txt", 1000);
		fireBoltRight = new Animation();
		createAnimation(fireBoltRight, "level2/fireBoltRight.txt", 1000);
		deadDinoLeft = new Animation();
		createAnimation(deadDinoLeft, "level2/deadDinoLeft.txt", 1000);
		deadDinoRight = new Animation();
		createAnimation(deadDinoRight, "level2/deadDinoRight.txt", 1000);
		deadGoblinLeft = new Animation();
		createAnimation(deadGoblinLeft, "level2/deadGoblinLeft.txt", 1000);
		deadGoblinRight = new Animation();
		createAnimation(deadGoblinRight, "level2/deadGoblinRight.txt", 1000);
		deadTiger = new Animation();
		createAnimation(deadTiger, "level2/deadTiger.txt", 1000);
		orcWalk = new Animation();
		createAnimation(orcWalk, "level2/orcWalk.txt", 150);
		orcHit = new Animation();
		createAnimation(orcHit, "level2/orcHit.txt", 600);
		orcDeath = new Animation();
		createAnimation(orcDeath, "level2/orcDeath.txt", 300);
		
		for (int i = 0; i <= 1; i ++) {
            addSprite(s, dinoRight);
        }
		for (int i = 0; i <= 1; i ++) {
			addSprite(s, goblinRight);
        }
		
        addSprite(s, tigerRight);
        
        for (int i = 0; i <= 1; i++) {
        	addWeaponSprite(s, fireBoltRight);
        }
        
        for (int i = 0; i <= 1; i++) {
        	addWeaponSprite(s, frostBoltRight);
        }
        
        for (int i = 0; i <= 3; i++) {
        	shootingSprites[i] = 0;
        }
        
        for (int i = 0; i < deadSprites.length; i++) {
        	deadSprites[i] = false;
        }
        
        boss = new Sprite(orcWalk);
		
		backgroundCave = new Sprite(cave);
		backgroundCave2 = new Sprite(cave);
		
		System.out.println(tmap);
	}
	
    public void drawBoss(Graphics2D g, int xo, int yo) {
	    boss.setOffsets(xo, yo);
        boss.drawTransformed(g);
    }
	
	public void drawTileMap (Graphics2D g, int xo, int yo) {
		backgroundCave.setOffsets(xo, yo);
	    backgroundCave.draw(g);
	    backgroundCave2.setOffsets(xo, yo);
	    backgroundCave2.draw(g);
	    
	    tmap.draw(g, xo, yo);
	}
	
	 public void draw(Graphics2D g, int xo, int yo) {
		 for (int i = 0; i < sprites.size(); i++) {
	        sprites.get(i).setOffsets(xo, yo);
	        sprites.get(i).drawTransformed(g);
	     }
	        
	     for (int i = 0; i < weaponSprites.size(); i++) {
	        weaponSprites.get(i).setOffsets(xo, yo);
	        weaponSprites.get(i).drawTransformed(g);
	     }
	 }
	 
	 public void initializeGame() {
		 //dino sprites
		 sprites.get(0).setX(tmap.getTileXC(46, 0));
	     sprites.get(0).setY(tmap.getTileYC(0, 15) - sprites.get(0).getHeight());
	        
	     sprites.get(1).setX(tmap.getTileXC(100, 0));
	     sprites.get(1).setY(tmap.getTileYC(0, 17) - sprites.get(1).getHeight());
		 // end of dino sprites
	     
	     //mage sprites
	     sprites.get(2).setX(tmap.getTileXC(37, 0));
	     sprites.get(2).setY(tmap.getTileYC(0, 14) - sprites.get(2).getHeight());
	        
	     sprites.get(3).setX(tmap.getTileXC(130, 0));
	     sprites.get(3).setY(tmap.getTileYC(0, 15) - sprites.get(3).getHeight());
	     //end of mage sprites
	     
	     //tiger sprite
	     sprites.get(4).setX(tmap.getTileXC(13, 0));
	     sprites.get(4).setY(tmap.getTileYC(0, 17) - sprites.get(4).getHeight());
	     //end of tiger sprite
	     
	     for (int i = 0; i < weaponSprites.size(); i++) {
	        weaponSprites.get(i).setX(sprites.get(i).getX());
	        weaponSprites.get(i).setY(sprites.get(i).getY());
	     }
	     
	     for (int i = 0; i < sprites.size(); i ++) {
	        sprites.get(i).show();
	     }
	     
		 backgroundCave.setX(0);
	     backgroundCave.setY(0);
	     backgroundCave.show();        
	     backgroundCave2.setX(0 + backgroundCave2.getWidth());
	     backgroundCave2.setY(0);
	     backgroundCave2.show();
	     
	     boss.setX(tmap.getTileXC(164, 0));
	     boss.setY(tmap.getTileYC(0, 15));
	     boss.show();
	     
	     bossDeadCount = 4;
	        bossDead = false;
	        boss.setRotation(0);
	        boss.setVelocityX(0);
	        boss.setAnimation(orcWalk);
	        boss.getAnimation().setAnimationSpeed(150);
	        
	        for (int i = 0; i < deadSprites.length; i++) {
	        	deadSprites[i] = false;
	        }
	        
	        for (int i = 0; i <= 1; i ++) {
	            sprites.get(i).setAnimation(dinoRight);
	            sprites.get(i).setRotation(0);
	            sprites.get(i).setVelocityX(0.1f);
	        }
	        
	        for (int i = 2; i <= 3; i++) {
	        	sprites.get(i).setAnimation(goblinRight);
	        	sprites.get(i).setRotation(0);
	        	sprites.get(i).setVelocityX(0.1f);
	        }
	        
	            sprites.get(4).setVelocityX(0.1f);
	        	sprites.get(4).setAnimation(tigerRight);
	        	sprites.get(4).setRotation(0);
	        
	        for (int i = 0; i <= 3; i++) {
	        	shootingSprites[i] = 0;
	        }
	 }
	 
	 public void initialBackgroundVelocity() {
    	backgroundCave.setVelocityX(0);
   	    backgroundCave2.setVelocityX(0);
     }
	 
	 public void updateBoss(long elapsed, Sprite player) {
		 boss.update(elapsed);
		 
		 if (boss.getAnimation().equals(orcHit) && boss.getAnimation().getCurrentFrameIndex() == 2 &&
				 loop == 0) {
			 boss.setX(boss.getX() - 30);
			 loop++;
		 }
		 
		 if (!bossDead && boss.getX() - player.getX() <= 40) {			 
			 boss.setAnimation(orcHit);
			 boss.setVelocityX(0);
		 }
		 else if (!bossDead && boss.getVelocityX() < 0) {
			 orcHit.start();
			 boss.setAnimation(orcWalk);
			 boss.setVelocityX(-0.1f);
			 loop = 0;
		 }
		 if(boss.getX() >= tmap.getTileXC(164, 0)) {
			 boss.setAnimation(orcWalk);
			 boss.setVelocityX(-0.1f);
			 loop = 0;
		 }
		 if(bossDeadCount == 0) {
			 bossDead = true;
		 }
		 if(bossDead) {
			 boss.setY(tmap.getTileYC(0, 15) + 25);
			 boss.setVelocityX(0);
			 orcDeath.setLoop(false);
			 boss.setAnimation(orcDeath);
			 
		 }
	 }
	 
	 public void updateBackground(long elapsed, Player player) {
    	if (player.getVelocityX() > 0) {
       	    backgroundCave.setVelocityX(-0.02f);
       	    backgroundCave2.setVelocityX(-0.02f);
       	}
       	else if (player.getVelocityX() < 0) {
       		backgroundCave.setVelocityX(0.02f);
       	    backgroundCave2.setVelocityX(0.02f);
       	}
       	else {
       		backgroundCave.setVelocityX(0);
       	    backgroundCave2.setVelocityX(0);
       	}
       	if (player.getX() - Game.screenWidth / 2 <= 0) {
       		backgroundCave.setVelocityX(0);
       	    backgroundCave2.setVelocityX(0);
       	}
       	if (player.getX() - Game.screenWidth / 2 >= backgroundCave2.getX()) {
       		backgroundCave.setX(backgroundCave2.getX() + backgroundCave2.getWidth());
       	}
       	else {
       		backgroundCave.setX(backgroundCave2.getX() - backgroundCave2.getWidth());
       	}
       	if (player.getX() - Game.screenWidth / 2 >= backgroundCave.getX()) {
       		backgroundCave2.setX(backgroundCave.getX() + backgroundCave.getWidth());
       	}
       	else {
       		backgroundCave2.setX(backgroundCave.getX() - backgroundCave.getWidth());
       	}
    }
	 
	public void updateSprites(long elapsed, Sprite player) {
		for (int i = 0; i < sprites.size(); i++) {
       	    sprites.get(i).update(elapsed);
       	}
       	
       	for (int i = 0; i < weaponSprites.size(); i++) {
       		weaponSprites.get(i).update(elapsed);
       	}
       	
       	backgroundCave.update(elapsed);
       	backgroundCave2.update(elapsed);
       	
       	for (int i = 0; i <= 3; i++) {
       		if (!weaponSprites.get(i).isVisible() && !deadSprites[i] && 
       		        shootingSprites[i] < 2000) {
        	    weaponSprites.get(i).setX(sprites.get(i).getX());
        	    weaponSprites.get(i).setY(sprites.get(i).getY());
        	    weaponSprites.get(i).setVelocityX(0);
        	    weaponSprites.get(i).hide();
        	    shootingSprites[i] = shootingSprites[i] + elapsed;
       		}
       		if ((Math.abs((sprites.get(i).getX() - weaponSprites.get(i).getX())) >= 400)) {
       			weaponSprites.get(i).hide();
       		}
       		if (!weaponSprites.get(i).isVisible() && 
       				shootingSprites[i] >= 2000 && !deadSprites[i]) {
       			if (sprites.get(i).getAnimation().equals(dinoLeft)){
       			    weaponSprites.get(i).setVelocityX(-0.2f);
       			    weaponSprites.get(i).setAnimation(fireBoltLeft);
       			}
       			else if (sprites.get(i).getAnimation().equals(dinoRight)) {
       				weaponSprites.get(i).setVelocityX(0.2f);
       				weaponSprites.get(i).setAnimation(fireBoltRight);
       			}
       			else if (sprites.get(i).getAnimation().equals(goblinLeft)) {
       				weaponSprites.get(i).setVelocityX(-0.2f);
       				weaponSprites.get(i).setAnimation(frostBoltLeft);
       				weaponSprites.get(i).setY(weaponSprites.get(i).getY() + 45);
       			}
       			else if (sprites.get(i).getAnimation().equals(goblinRight)) {
       				weaponSprites.get(i).setVelocityX(0.2f);
       				weaponSprites.get(i).setAnimation(frostBoltRight);
       				weaponSprites.get(i).setY(weaponSprites.get(i).getY() + 45);
       			}
       			shootingSprites[i] = 0;
       			weaponSprites.get(i).show();
       		}      		
        }
       	
       	for (int i = 2; i <= 3; i++) {
        	if (deadSprites[i]) {
        		sprites.get(i).setVelocityX(0);
        		if (sprites.get(i).getAnimation().equals(dinoLeft)) {
        			sprites.get(i).setAnimation(deadDinoLeft);
        		}
        		else if (sprites.get(i).getAnimation().equals(dinoRight)){
        			sprites.get(i).setAnimation(deadDinoRight);
        		}
        		else if (sprites.get(i).getAnimation().equals(goblinLeft)){
        			sprites.get(i).setAnimation(deadGoblinLeft);
        		}
        		else if (sprites.get(i).getAnimation().equals(goblinRight)){
        			sprites.get(i).setAnimation(deadGoblinRight);
        		}
        		if (sprites.get(i).getRotation() < 100) {
        			sprites.get(i).setY(sprites.get(i).getY() +  1);
        		    sprites.get(i).setRotation(sprites.get(i).getRotation() + 6);
        		}
        	}
        }
       	
       	for (int i = 0; i <= 1; i++) {
        	if (deadSprites[i]) {
        		sprites.get(i).setVelocityX(0);
        		if (sprites.get(i).getAnimation().equals(dinoLeft)) {
        			sprites.get(i).setAnimation(deadDinoLeft);
        		}
        		else if (sprites.get(i).getAnimation().equals(dinoRight)){
        			sprites.get(i).setAnimation(deadDinoRight);
        		}
        		sprites.get(i).setRotation(190);
        	}
        }
       	
       	if (deadSprites[4]) {
    		sprites.get(4).setAnimation(deadTiger);
    		sprites.get(4).setVelocityX(0);
    		sprites.get(4).setRotation(190);
    	}
	}
    
    private void addSprite(Sprite s, Animation animation) {
    	s = new Sprite(animation);
    	s.setVelocityX(0.1f);
    	sprites.add(s);
    }
    
    private void addWeaponSprite(Sprite s, Animation animation) {
    	s = new Sprite(animation);
    	s.setVelocityX(0);
    	weaponSprites.add(s);
    }
    
    public TileMap getTileMap() {
    	return tmap;
    }
    
    public ArrayList<Sprite> getSprites() {
    	return sprites;
    }
    
    public ArrayList<Sprite> getWeaponSprites() {
    	return weaponSprites;
    }
    
    public boolean[] getDeadSprites() {
    	return deadSprites;
    }
    
    public Animation getGoblinLeft() {
    	return goblinLeft;
    }
    
    public Animation getGoblinRight() {
    	return goblinRight;
    }
    
    public Animation getDinoLeft() {
    	return dinoLeft;
    }
    
    public Animation getDinoRight() {
    	return dinoRight;
    }
    
    public Animation getTigerRight() {
    	return tigerRight;
    }
    
    public Animation getTigerLeft() {
    	return tigerLeft;
    }
    
    public Sprite getBoss() {
    	return boss;
    }
    
    public Animation getOrcWalk() {
    	return orcWalk;
    }
    
    public Animation getOrcHit() {
    	return orcHit;
    }
    
    public void returnBoss() {
    	boss.setVelocityX(0.2f);
    }
    
    public void decrementBossDeadCount() {
    	bossDeadCount--;
    }
    
    public boolean bossDead() {
    	return bossDead;
    }
    
    public int bossDeadCount() {
    	return bossDeadCount;
    }
}


