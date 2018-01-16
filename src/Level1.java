import game2D.Animation;
import game2D.Sprite;
import game2D.TileMap;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Level1 {
	
	Animation zombieRight, zombieLeft, spiderRight, spiderLeft, 
	wormLeft, wormRight, spiderWeb, fireLeft,fireRight, deadZombieLeft, 
	deadZombieRight, deadSpider, deadWormLeft, deadWormRight, cave, fire, leftBoss, rightBoss;
	
	Collision collision = new Collision();
	
    ArrayList<Sprite> sprites = new ArrayList<Sprite>();
    ArrayList<Sprite> weaponSprites = new ArrayList<Sprite>();
    ArrayList<Sprite> bossSprites = new ArrayList<Sprite>();
    long[] shootingSprites = new long[11];
    boolean[] deadSprites = new boolean[15];
    Sprite backgroundCave, backgroundFire, backgroundCave2, backgroundFire2;
    Sprite boss, bossWeapon;
    long bossShooting;
    int bossDeadCount = 4;
    boolean bossDead = false;

    TileMap tmap = new TileMap();	// Our tile map, note that we load it in init()
    
    public void initialise() {
    	
    	tmap.loadMap("maps/level1", "map.txt");
    	Sprite s = null;
    	// creating the animations for the different sprites
        zombieRight = new Animation();
        createAnimation(zombieRight, "level1/zombieRight.txt", 300);
        zombieLeft = new Animation();
        createAnimation(zombieLeft, "level1/zombieLeft.txt", 300);
        spiderRight = new Animation();
        createAnimation(spiderRight, "level1/spiderRight.txt", 400);
        spiderLeft = new Animation();
        createAnimation(spiderLeft, "level1/spiderLeft.txt", 400);
        wormLeft = new Animation();
        createAnimation(wormLeft, "level1/wormLeft.txt", 1400);
        wormRight = new Animation();
        createAnimation(wormRight, "level1/wormRight.txt", 200);
        spiderWeb = new Animation();
        createAnimation(spiderWeb, "level1/spiderWeb.txt", 1500);
        fireLeft = new Animation();
        createAnimation(fireLeft, "level1/fireLeft.txt", 1200);
        fireRight = new Animation();
        createAnimation(fireRight, "level1/fireRight.txt", 200);
        deadZombieLeft = new Animation();
        createAnimation(deadZombieLeft, "level1/deadZombieLeft.txt", 3000);
        deadZombieRight = new Animation();
        createAnimation(deadZombieRight, "level1/deadZombieRight.txt", 3000);
        deadSpider = new Animation();
        createAnimation(deadSpider, "level1/deadSpider.txt", 3000);
        deadWormLeft = new Animation();
        createAnimation(deadWormLeft, "level1/deadWormLeft.txt", 3000);
        deadWormRight = new Animation();
        createAnimation(deadWormRight, "level1/deadWormRight.txt", 3000);
        cave = new Animation();
        createAnimation(cave, "level1/cave.txt", 3000);
        fire = new Animation();
        createAnimation(fire, "level1/fire.txt", 3000);
        leftBoss = new Animation();
        createAnimation(leftBoss, "level1/leftBoss.txt", 50);
        rightBoss = new Animation();
        createAnimation(rightBoss, "level1/rightBoss.txt", 50);
        
        for (int i = 0; i <= 3; i ++) {
            addSprite(s, zombieRight);
        }
        
        for (int i = 0; i <= 4; i++) {
        	addSprite(s, spiderRight);
        }
        
        for (int i = 0; i <= 5; i++) {
        	addSprite(s, wormLeft);
        }
        
        for (int i = 0; i <= 4; i++) {
        	addWeaponSprite(s, spiderWeb);
        }
        
        for (int i = 0; i <= 5; i++) {
        	addWeaponSprite(s, fireLeft);
        }
        
        for (int i = 0; i <= 10; i++) {
        	shootingSprites[i] = 0;
        }
        
        for (int i = 0; i < deadSprites.length; i++) {
        	deadSprites[i] = false;
        }
        
        backgroundCave = new Sprite(cave);
        backgroundFire = new Sprite(fire);
        backgroundCave2 = new Sprite(cave);
        backgroundFire2 = new Sprite(fire);
        boss = new Sprite(leftBoss);
        bossWeapon = new Sprite(spiderWeb);
        
        System.out.println(tmap);
    }
    
    public void drawBoss(Graphics2D g, int xo, int yo) {
    	boss.setOffsets(xo, yo);
    	boss.drawTransformed(g);
    	bossWeapon.setOffsets(xo, yo);
    	bossWeapon.draw(g);
    	for (int i = 0; i < bossSprites.size(); i++) {
    		bossSprites.get(i).setOffsets(xo, yo);
    		bossSprites.get(i).draw(g);
    	}
    }
    
    public void drawTileMap (Graphics2D g, int xo, int yo) {
    	backgroundFire.setOffsets(xo, yo);
	    backgroundFire.draw(g);
	    backgroundFire2.setOffsets(xo, yo);
	    backgroundFire2.draw(g);
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
    	//zombie sprites
        sprites.get(0).setX(tmap.getTileXC(27, 15));
        sprites.get(0).setY(tmap.getTileYC(27, 15) - sprites.get(0).getHeight());
        
        sprites.get(1).setX(tmap.getTileXC(82, 15));
        sprites.get(1).setY(tmap.getTileYC(82, 15) - sprites.get(1).getHeight());
        
        sprites.get(2).setX(tmap.getTileXC(147, 12));
        sprites.get(2).setY(tmap.getTileYC(147, 12) - sprites.get(2).getHeight());
        
        sprites.get(3).setX(tmap.getTileXC(234, 16));
        sprites.get(3).setY(tmap.getTileYC(234, 16) - sprites.get(3).getHeight());
        //end of zombie sprites
        
        //spider sprites
        sprites.get(4).setX(tmap.getTileXC(41, 15));
        sprites.get(4).setY(tmap.getTileYC(41, 15) - sprites.get(4).getHeight());
        
        sprites.get(5).setX(tmap.getTileXC(115, 16));
        sprites.get(5).setY(tmap.getTileYC(115, 16) - sprites.get(5).getHeight());
        
        sprites.get(6).setX(tmap.getTileXC(163, 15));
        sprites.get(6).setY(tmap.getTileYC(163, 15) - sprites.get(6).getHeight());
        
        sprites.get(7).setX(tmap.getTileXC(229, 16));
        sprites.get(7).setY(tmap.getTileYC(229, 16) - sprites.get(7).getHeight());
        
        sprites.get(8).setX(tmap.getTileXC(239, 16));
        sprites.get(8).setY(tmap.getTileYC(239, 16) - sprites.get(8).getHeight());
        //end of spider sprites
        
        //worm sprites
        sprites.get(9).setX(tmap.getTileXC(55, 15) - 10);
        sprites.get(9).setY(tmap.getTileYC(55, 15));
        
        sprites.get(10).setX(tmap.getTileXC(100, 15) - 10);
        sprites.get(10).setY(tmap.getTileYC(100, 15));
        
        sprites.get(11).setX(tmap.getTileXC(137, 15) - 10);
        sprites.get(11).setY(tmap.getTileYC(137, 15));
        
        sprites.get(12).setX(tmap.getTileXC(179, 15) - 10);
        sprites.get(12).setY(tmap.getTileYC(179, 15));
        
        sprites.get(13).setX(tmap.getTileXC(193, 15) - 10);
        sprites.get(13).setY(tmap.getTileYC(193, 15));
        
        sprites.get(14).setX(tmap.getTileXC(203, 15) - 10);
        sprites.get(14).setY(tmap.getTileYC(203, 15));
        //end of worm sprites
        
        for (int i = 0; i <= 4; i++) {
        	weaponSprites.get(i).setX(sprites.get(i + 4).getX());
        	weaponSprites.get(i).setY(sprites.get(i + 4).getY());
        }
        
        for (int i = 5; i <= 10; i++) {
        	weaponSprites.get(i).setX(sprites.get(i + 4).getX());
        	weaponSprites.get(i).setY(sprites.get(i + 4).getY());
        }
        
        for (int i = 0; i < sprites.size(); i ++) {
        	sprites.get(i).show();
        }
        
        for (int i = 9; i <= 14; i ++) {
        	sprites.get(i).setVelocityX(0);
        	sprites.get(i).setScale(2);
        }
        
        backgroundFire.setX(0);
        backgroundFire.setY(0);
        backgroundFire.show();
        backgroundFire2.setX(0 + backgroundFire2.getWidth());
        backgroundFire2.setY(0);
        backgroundFire2.show();
        backgroundCave.setX(0);
        backgroundCave.setY(0);
        backgroundCave.show();        
        backgroundCave2.setX(0 + backgroundCave2.getWidth());
        backgroundCave2.setY(0);
        backgroundCave2.show();
        boss.setX(tmap.getTileXC(275, 0));
        boss.setY(tmap.getTileYC(0, 14));
        boss.show();
        bossWeapon.setX(boss.getX());
        bossWeapon.setY(boss.getY() + boss.getHeight() / 2);
        
        bossSprites.clear();
        for (int i = 0; i <= 2; i++) {
        	Sprite s;
        	s = new Sprite(spiderLeft);
        	s.setVelocityX(0);
        	s.setX(0);
        	s.setY(0);
        	s.setX(tmap.getTileXC(240, 0));
        	s.setY(tmap.getTileYC(0, 10));
        	bossSprites.add(s);
        }
        
        bossDeadCount = 4;
        bossDead = false;
        boss.setRotation(0);
        boss.setVelocityX(0);
        boss.setAnimation(leftBoss);
        leftBoss.setAnimationSpeed(50);
        rightBoss.setAnimationSpeed(50);
        
        for (int i = 0; i < deadSprites.length; i++) {
        	deadSprites[i] = false;
        }
        
        for (int i = 0; i <= 3; i ++) {
            sprites.get(i).setAnimation(zombieRight);
            sprites.get(i).setRotation(0);
            sprites.get(i).setVelocityX(0.1f);
        }
        
        for (int i = 4; i <= 8; i++) {
        	sprites.get(i).setAnimation(spiderRight);
        	sprites.get(i).setRotation(0);
        	sprites.get(i).setVelocityX(0.1f);
        }
        
        for (int i = 9; i < sprites.size(); i++) {
        	sprites.get(i).setAnimation(wormLeft);
        	sprites.get(i).setShearX(0);
        	sprites.get(i).setShearY(0);
        	sprites.get(i).setRotation(0);
        }
        
        for (int i = 0; i <= 10; i++) {
        	shootingSprites[i] = 0;
        }
    }
    
    public void initialBackgroundVelocity() {
    	backgroundCave.setVelocityX(0);
   	    backgroundFire.setVelocityX(0);
   	    backgroundCave2.setVelocityX(0);
	    backgroundFire2.setVelocityX(0);
    }
    
    public void updateBackground(long elapsed, Player player) {
    	if (player.getVelocityX() > 0) {
       	    backgroundCave.setVelocityX(-0.02f);
       	    backgroundFire.setVelocityX(-0.01f);
       	    backgroundCave2.setVelocityX(-0.02f);
    	    backgroundFire2.setVelocityX(-0.01f);
       	}
       	else if (player.getVelocityX() < 0) {
       		backgroundCave.setVelocityX(0.02f);
       	    backgroundFire.setVelocityX(0.01f);
       	    backgroundCave2.setVelocityX(0.02f);
    	    backgroundFire2.setVelocityX(0.01f);
       	}
       	else {
       		backgroundCave.setVelocityX(0);
       	    backgroundFire.setVelocityX(0);
       	    backgroundCave2.setVelocityX(0);
    	    backgroundFire2.setVelocityX(0);
       	}
       	if (player.getX() - Game.screenWidth / 2 <= 0) {
       		backgroundCave.setVelocityX(0);
       	    backgroundFire.setVelocityX(0);
       	    backgroundCave2.setVelocityX(0);
    	    backgroundFire2.setVelocityX(0);
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
       	if (player.getX() - Game.screenWidth / 2 >= backgroundFire2.getX()) {
       		backgroundFire.setX(backgroundFire2.getX() + backgroundFire2.getWidth());
       	}
       	else {
       		backgroundFire.setX(backgroundFire2.getX() - backgroundFire2.getWidth());
       	}
       	if (player.getX() - Game.screenWidth / 2 >= backgroundFire.getX()) {
       		backgroundFire2.setX(backgroundFire.getX() + backgroundFire.getWidth());
       	}
       	else {
       		backgroundFire2.setX(backgroundFire.getX() - backgroundFire.getWidth());
       	}
    }
    
    public void updateBoss(long elapsed, Sprite player) {
    	boss.update(elapsed);
    	bossWeapon.update(elapsed);
    	
    	for (int i = 0; i < bossSprites.size(); i++) {
    		bossSprites.get(i).update(elapsed);
    		collision.handleTileMapCollisions(bossSprites.get(i), elapsed, 31, 30, tmap, true);
    	}
    	
    	if (!bossWeapon.isVisible() && bossDeadCount != 0 && 
   		        bossShooting < 2000) {
    		bossWeapon.setX(boss.getX());
            bossWeapon.setY(boss.getY() + boss.getHeight() / 2);
    		bossWeapon.setVelocityX(0);
    		bossWeapon.hide();
    	    bossShooting = bossShooting + elapsed;
   		}
   		if ((Math.abs((boss.getX() - bossWeapon.getX())) >= 400)) {
   			bossWeapon.hide();
   		}
   		if (!bossWeapon.isVisible() && 
   				bossShooting >= 2000 && bossDeadCount != 0) {
   			if (boss.getAnimation().equals(leftBoss)){
   				bossWeapon.setVelocityX(-0.2f);
   			}
   			else if (boss.getAnimation().equals(rightBoss)) {
   				bossWeapon.setVelocityX(0.2f);
   			}
   			bossShooting = 0;
   			bossWeapon.show();
   		}   
   		
   		if (bossDeadCount != 0 && bossSprites.get(2).getX() <= tmap.getTileXC(256, 0)) {
       		if (boss.getX() >= player.getX()) {
       			boss.setAnimation(leftBoss);
       			boss.setVelocityX(-0.05f);
       		}
       		else {
       			boss.setAnimation(rightBoss);
       			boss.setVelocityX(0.05f);
       		}
   		}
   		else if (bossDeadCount != 0 && bossSprites.get(2).getX() > tmap.getTileXC(256, 0))
   			if (boss.getAnimation().equals(leftBoss)) {
   				boss.setVelocityX(0.02f);
   			}
   			else {
   				boss.setVelocityX(-0.02f);
   			}
   		
   		if (bossDeadCount == 0) {
    		boss.setVelocityX(0);
    		boss.setRotation(190);
    		boss.setAnimationSpeed(0);
    		bossDead = true;
    	}
   		
   		if (boss.getY() >= tmap.getTileYC(0, 14)) {
   			boss.setVelocityY(0);
   		}
   		if (boss.getX() <= tmap.getTileXC(261, 0) && boss.getX() >= tmap.getTileXC(260, 0) + 15 &&
   				boss.getAnimation().equals(leftBoss)) {
   			boss.setVelocityY(-0.2f);   			
   		}
   		if (boss.getX() >= tmap.getTileXC(273, 0) && boss.getX() <= tmap.getTileXC(274, 0) - 15 &&
   				boss.getAnimation().equals(rightBoss)) {
   			boss.setVelocityY(-0.2f);   			
   		}
   		if (boss.getX() >= tmap.getTileXC(261, 0) && boss.getX() <= tmap.getTileXC(262, 0) - 15 &&
   				boss.getAnimation().equals(rightBoss)) {
   			boss.setVelocityY(-0.2f);   			
   		}
   		if (boss.getX() <= tmap.getTileXC(275, 0) && boss.getX() >= tmap.getTileXC(274, 0) + 15 &&
   				boss.getAnimation().equals(leftBoss)) {
   			boss.setVelocityY(-0.2f);   			
   		}
   		if (boss.getY() < tmap.getTileYC(0, 14)) {
   		    boss.setVelocityY((float)(boss.getVelocityY() + 0.0015 * elapsed));
   		}
    }
    
    public void updateSprites(long elapsed, Sprite player) {
    	// Now update the sprites animation and position
       	for (int i = 0; i < sprites.size(); i++) {
       	    sprites.get(i).update(elapsed);
       	}
       	
       	for (int i = 0; i < weaponSprites.size(); i++) {
       		weaponSprites.get(i).update(elapsed);
       	}
       	
       	backgroundFire.update(elapsed);
       	backgroundCave.update(elapsed);
       	backgroundFire2.update(elapsed);
       	backgroundCave2.update(elapsed);
       	
       	
       	
       	for (int i = 9; i <= 14; i++) {
       		if (!deadSprites[i]) {
	       		if (sprites.get(i).getX() >= player.getX()) {
	       			sprites.get(i).setAnimation(wormLeft);
	       			weaponSprites.get(i - 4).setAnimation(fireLeft);
	       		}
	       		else {
	       			sprites.get(i).setAnimation(wormRight);
	       		    weaponSprites.get(i - 4).setAnimation(fireRight);
	       		}
       		}
       	}
       	
       	for (int i = 0; i <= 4; i++) {
       		if (!weaponSprites.get(i).isVisible() && !deadSprites[i + 4] && 
       		        shootingSprites[i] < 2000) {
        	    weaponSprites.get(i).setX(sprites.get(i + 4).getX());
        	    weaponSprites.get(i).setY(sprites.get(i + 4).getY());
        	    weaponSprites.get(i).setVelocityX(0);
        	    weaponSprites.get(i).hide();
        	    shootingSprites[i] = shootingSprites[i] + elapsed;
       		}
       		if ((Math.abs((sprites.get(i + 4).getX() - weaponSprites.get(i).getX())) >= 400)) {
       			weaponSprites.get(i).hide();
       		}
       		if (!weaponSprites.get(i).isVisible() && 
       				shootingSprites[i] >= 2000 && !deadSprites[i + 4]) {
       			if (sprites.get(i + 4).getAnimation().equals(spiderLeft)){
       			    weaponSprites.get(i).setVelocityX(-0.2f);
       			}
       			else if (sprites.get(i + 4).getAnimation().equals(spiderRight)) {
       				weaponSprites.get(i).setVelocityX(0.2f);
       			}
       			shootingSprites[i] = 0;
       			weaponSprites.get(i).show();
       		}      		
        }
       	
       	for (int i = 5; i <= 10; i++) {
       		if ((!weaponSprites.get(i).isVisible() && !deadSprites[i + 4] && 
       		        shootingSprites[i] < 2000)) {
            	    weaponSprites.get(i).setX(sprites.get(i + 4).getX());
            	    weaponSprites.get(i).setY(sprites.get(i + 4).getY() + 5);
            	    weaponSprites.get(i).setVelocityX(0);
            	    weaponSprites.get(i).hide();
            	    shootingSprites[i] = shootingSprites[i] + elapsed;
           		}
       		if ((Math.abs((sprites.get(i + 4).getX() - weaponSprites.get(i).getX())) >= 400)) {
       			weaponSprites.get(i).hide();
       		}
       		if (!weaponSprites.get(i).isVisible() && 
       				shootingSprites[i] >= 2000 && !deadSprites[i + 4]) {
       			if (sprites.get(i + 4).getAnimation().equals(wormLeft)){
       			    weaponSprites.get(i).setVelocityX(-0.2f);
       			}
       			else if (sprites.get(i + 4).getAnimation().equals(wormRight)) {
       				weaponSprites.get(i).setVelocityX(0.2f);
       			}
       			shootingSprites[i] = 0;
       			weaponSprites.get(i).show();
       		}
        }
       	
       	for (int i = 0; i <= 3; i++) {
        	if (deadSprites[i]) {
        		sprites.get(i).setVelocityX(0);
        		if (sprites.get(i).getAnimation().equals(zombieLeft)) {
        			sprites.get(i).setAnimation(deadZombieLeft);
        		}
        		else if (sprites.get(i).getAnimation().equals(zombieRight)){
        			sprites.get(i).setAnimation(deadZombieRight);
        		}
        		if (sprites.get(i).getRotation() < 100) {
        			sprites.get(i).setY(sprites.get(i).getY() +  1);
        		    sprites.get(i).setRotation(sprites.get(i).getRotation() + 6);
        		}
        	}
        }
        
        for (int i = 4; i <= 8; i++) {
        	if (deadSprites[i]) {
        		sprites.get(i).setAnimation(deadSpider);
        		sprites.get(i).setVelocityX(0);
        		sprites.get(i).setRotation(190);
        	}
        }
        
        for (int i = 9; i <= 14; i++) {
        	if (deadSprites[i]) {
        		sprites.get(i).setVelocityX(0);
        		if (sprites.get(i).getAnimation().equals(wormLeft)) {
        			sprites.get(i).setAnimation(deadWormLeft);
        		}
        		else if (sprites.get(i).getAnimation().equals(wormRight)){
        			sprites.get(i).setAnimation(deadWormRight);
        		}
        		sprites.get(i).getAnimation().setAnimationSpeed(0);
        		if (sprites.get(i).getShearY() <= 1.2) {
        			sprites.get(i).setShearY(sprites.get(i).getShearY() + 0.01);
        			sprites.get(i).setRotation(sprites.get(i).getRotation() - 0.4);
        			sprites.get(i).setX(sprites.get(i).getX() - 0.2f);
        		}
        	}
        }
    } // end of updateSprites()
    
    public ArrayList<Sprite> getSprites() {
    	return sprites;
    }
    
    public ArrayList<Sprite> getWeaponSprites() {
    	return weaponSprites;
    }
    
    public boolean[] getDeadSprites() {
    	return deadSprites;
    }
    
    public Animation getZombieLeft() {
    	return zombieLeft;
    }
    
    public Animation getZombieRight() {
    	return zombieRight;
    }
    
    public Animation getSpiderLeft() {
    	return spiderLeft;
    }
    
    public Animation getSpiderRight() {
    	return spiderRight;
    }
    
    public TileMap getTileMap() {
    	return tmap;
    }
    
    public int bossDeadCount() {
    	return bossDeadCount;
    }
    
    public void decrementBossDeadCount() {
    	bossDeadCount--;
    }
    
    public boolean bossDead() {
    	return bossDead;
    }
    
    public ArrayList<Sprite> getBossSprites() {
    	return bossSprites;
    }
    
    public Sprite getBoss() {
    	return boss;
    }
    
    public Sprite getBossWeapon() {
    	return bossWeapon;
    }
    
    public void includeBossSprites() {
    	bossSprites.get(0).setX(tmap.getTileXC(267, 0));
    	bossSprites.get(0).setY(tmap.getTileYC(0, 4));
    	bossSprites.get(0).setVelocityX(-0.1f);
    	bossSprites.get(0).setVelocityY(0.25f);
    	bossSprites.get(0).setAnimationSpeed(150);
    	bossSprites.get(0).show();
    	bossSprites.get(1).setX(tmap.getTileXC(275, 0));
    	bossSprites.get(1).setY(tmap.getTileYC(0, 7));
    	bossSprites.get(1).setVelocityX(-0.1f);
    	bossSprites.get(1).setVelocityY(0.25f);
    	bossSprites.get(1).setAnimationSpeed(150);
    	bossSprites.get(1).show();
    	bossSprites.get(2).setX(tmap.getTileXC(280, 0));
    	bossSprites.get(2).setY(tmap.getTileYC(0, 13));
    	bossSprites.get(2).setVelocityX(-0.1f);
    	bossSprites.get(2).setVelocityY(0.25f);
    	bossSprites.get(2).setAnimationSpeed(150);
    	bossSprites.get(2).show();
    }
    
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
}
