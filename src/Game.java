import game2D.Animation;
import game2D.GameCore;
import game2D.Sound;
import game2D.Sprite;
import game2D.TileMap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import javax.swing.ImageIcon;

// Game demonstrates how we can override the GameCore class
// to create our own 'game'. We usually need to implement at
// least 'draw' and 'update' (not including any local event handling)
// to begin the process. You should also add code to the 'init'
// method that will initialise event handlers etc. By default GameCore
// will handle the 'Escape' key to quit the game but you should
// override this with your own event handler.

/**
 * @author David Cairns
 *
 */
@SuppressWarnings("serial")

public class Game extends GameCore implements MouseListener, MouseMotionListener
{
	// Useful game constants
	static int screenWidth = 768;
    static int screenHeight = 570;

    float 	lift = 0.005f;
    float	gravity = 0.0001f;
    int playerDeadCount = 4;
    long playerInvinsibleTime = 0;
    
    Level1 firstLevel = new Level1();
    Level2 secondLevel = new Level2();
    
    //boolean values to check player moves
    boolean forward = false;
    boolean backwards = false;
    boolean jump = false;
    boolean dead = false;
    boolean shootingRight = false;
    boolean shootingLeft = false;
    boolean playerInvinsible = false;
    boolean mouseOverStart = false;
    boolean mouseOverControls = false;
    boolean mouseOverExit = false;
    boolean controls = false;
    boolean forwardControl = false;
    boolean backwardControl = false;
    boolean jumpControl = false;
    boolean shootControl = false;
    boolean intro = false;
    boolean betweenLevels = false;
    boolean end = false;
    
    // A parameter to count how many times the animation has looped
    int looped = 0;
    int looped2 = 0;
    int looped3 = 0;
    int looped4 = 0;
    int looped5 = 0;
    int looped6 = 0;
    int looped7 = 0;
    
    // Controls
    int forwardKey = KeyEvent.VK_RIGHT;
    int backwardKey = KeyEvent.VK_LEFT;
    int jumpKey = KeyEvent.VK_UP;
    int shootKey = KeyEvent.VK_SPACE;
    
    //boolean values to check what stage of the game you are on
    boolean level1 = false;
    boolean level1Boss = false;
    boolean level2 = false;
    boolean level2Boss = false;
    boolean mainMenu = true;

    // Game resources
    Animation walkingRight, walkingLeft, arrowRight, arrowLeft, shootLeft, shootRight;
    
    Collision collision = new Collision();
    
    static Player player;
    Arrow arrow;
    long total;         			// The score will be the total time elapsed since a crash
    
    // parameters for videos
    Image pic1, pic2, pic3, pic4, pic5;
    Font normal = null;


    /**
	 * The obligatory main method that creates
     * an instance of our class and starts it running
     * 
     * @param args	The list of parameters this program might use (ignored)
     */
    public static void main(String[] args) {

        Game gct = new Game();
        gct.init();
        // Start in windowed mode with the given screen height and width
        gct.run(false,screenWidth,screenHeight);
    }

    /**
     * Initialise the class, e.g. set up variables, load images,
     * create animations, register event handlers
     */
    public void init()
    {       
    	addMouseListener(this);
    	addMouseMotionListener(this);
        // creating the animations for the different sprites
        walkingRight = new Animation();
        createAnimation(walkingRight, "mainWalkRight.txt", 200);
        walkingLeft = new Animation();
        createAnimation(walkingLeft, "mainWalkLeft.txt", 200);
        arrowRight = new Animation();
        createAnimation(arrowRight, "arrowRight.txt", 200);
        arrowLeft = new Animation();
        createAnimation(arrowLeft, "arrowLeft.txt", 200);
        
        pic1 = new ImageIcon("images/pic1.png").getImage();
        pic2 = new ImageIcon("images/pic2.png").getImage();
        pic3 = new ImageIcon("images/pic3.png").getImage();
        pic4 = new ImageIcon("images/pic4.png").getImage();
        pic5 = new ImageIcon("images/pic5.png").getImage();
        
        player = new Player(walkingRight);
        arrow = new Arrow(arrowRight);

        firstLevel.initialise();
        secondLevel.initialise();

        initialiseGame();
    }

    /**
     * You will probably want to put code to restart a game in
     * a separate method so that you can call it to restart
     * the game.
     */
    public void initialiseGame()
    {
    	forward = false;
        backwards = false;
        jump = false;
        dead = false;
        shootingRight = false;
        shootingLeft = false;
        playerInvinsible = false;
        mouseOverStart = false;
        mouseOverControls = false;
        mouseOverExit = false;
        controls = false;
        forwardControl = false;
        backwardControl = false;
        jumpControl = false;
        shootControl = false;
        intro = false;
        betweenLevels = false;
        end = false;
        
        playerDeadCount = 4;
        
    	total = 0;
    	      
        player.setX(screenWidth / 3);
        player.setY(secondLevel.getTileMap().getTileYC(0, 12));
        player.setY(400);
        player.setVelocityX(0);
        player.setVelocityY(0.25f);
        
        arrow.setX(player.getX());
        arrow.setY(player.getY() + player.getHeight());
        arrow.setVelocityX(0);
        
        if (level1) {
            firstLevel.initializeGame();
        }
        
        if (level2) {
        	secondLevel.initializeGame();
        }
        
        player.show();
        player.setAnimationFrame(0);
        player.stop();
    }
    
    /**
     * Draw the current state of the game
     */
    public void draw(Graphics2D g)
    {    	
    	// Be careful about the order in which you draw objects - you
    	// should draw the background first, then work your way 'forward'

    	// First work out how much we need to shift the view 
    	// in order to see where the player is.
    	int xo, yo = 0;
    	if (player.getX() <= screenWidth / 2) {
    	    xo = 0;
    	}
    	else if ((level1 && player.getX() >= firstLevel.getTileMap().getTileXC(269, 0)) || level1Boss) {
        	xo = -firstLevel.getTileMap().getTileXC(256, 0);
        	level1Boss = true;
        	level1 = false;
        }
    	else if ((level2 && player.getX() >= secondLevel.getTileMap().getTileXC(154, 0)) || level2Boss) {
        	xo = -secondLevel.getTileMap().getTileXC(141, 0);
        	level2Boss = true;
        	level2 = false;
        }
    	else {
    	    xo = -(int)player.getX() + screenWidth / 2;
        }
    	if (player.getX() <= -xo + 5) {
    		player.setX(player.getX() + 4);
    	}
    	if (player.getX() >= -xo + screenWidth - 35) {
    		player.setX(player.getX() - 4);
    	}

        
        
        if (level1) {
        	firstLevel.drawTileMap(g, xo, yo);
            firstLevel.draw(g, xo, yo);
            firstLevel.drawBoss(g, xo, yo);
        }
        if (level1Boss) {
        	firstLevel.drawTileMap(g, xo, yo);
        	firstLevel.drawBoss(g, xo, yo);
        }
        if (level2) {
        	secondLevel.drawTileMap(g, xo, yo);
        	secondLevel.draw(g, xo, yo);
        	secondLevel.drawBoss(g, xo, yo);
        }
        if (level2Boss) {
        	secondLevel.drawTileMap(g, xo, yo);
        	secondLevel.drawBoss(g, xo, yo);
        }
        if ( level1 || level1Boss || level2 || level2Boss) {
	        player.setOffsets(xo, yo);
	        player.drawTransformed(g);
	        arrow.setOffsets(xo, yo);
	        arrow.drawTransformed(g);
        }
        
        if (!secondLevel.bossDead() && (level2Boss && collision.boundingBoxCollision(arrow, secondLevel.getBoss()) && secondLevel.bossDeadCount() != 0 && 
       		secondLevel.getBoss().getAnimation().equals(secondLevel.getOrcWalk())) || looped2 != 0) {
        	if (collision.boundingCircleCollision(arrow, secondLevel.getBoss())) {
        	    shootingRight = false;
       			g.setColor(Color.WHITE);
       			g.drawString("PARRY!!!", secondLevel.getBoss().getX(), secondLevel.getBoss().getY() - 20);
       			looped2++;
       			arrow.hide();
                if (looped2 == 10) {
                	looped2 = 0;
                }
       		}
        }
        
        if (level1 || level1Boss || level2 || level2Boss) {
	        g.setColor(Color.BLUE);
	        g.drawString("Lives left: ", 15, 45);
	        for (int i = 0 ; i < playerDeadCount; i++) {
	            g.setColor(Color.RED);
	            g.fillOval(15 + i * 15, 60, 10, 10);
	        }
        }
        
        //initiating game over screen
        if (dead) {
        	if (looped <= 15)
                looped++;
        	if (looped == 16) {
       			looped = 0;
       		}
        	
        	if (looped == 0 || looped == 17) {
        	   g.setColor(Color.black);
        	   g.fillRect(0, 0, screenWidth, screenHeight);
        	   g.setColor(Color.red);
        	   g.setFont(new Font("GAME OVER", Font.BOLD, 32));
        	   g.drawString("GAME OVER", 300, 300);
        	   g.setColor(Color.WHITE);
        	   g.setFont(new Font("normal", Font.PLAIN, 11));
        	   g.drawString("(Press \"Escape\" to continue)", 325, 320);
        	   looped = 17;
        	}
        }
        
        if (mainMenu) {
        	g.drawImage(new ImageIcon("images/mainMenu.png").getImage(), 7, -30, screenWidth, screenHeight + 80, null);
        	g.setColor(Color.BLUE);
        	if (mouseOverStart) {
        	    g.drawRect(97, 180, 150, 27);
        	}
        	if (mouseOverControls) {
        	    g.drawRect(67, 240, 215, 27);
        	}
        	if (mouseOverExit) {
        	    g.drawRect(117, 300, 110, 27);
        	}
        }
        
        if (controls) {
        	g.drawImage(new ImageIcon("images/controls.png").getImage(), 0, -30, screenWidth, screenHeight + 80, null);
        	g.setColor(Color.RED);
        	if (forwardControl) {
        		g.drawRect(290, 150, 210, 35);
        	}
        	if (backwardControl) {
        		g.drawRect(280, 215, 235, 35);
        	}
        	if (jumpControl) {
        		g.drawRect(340, 275, 120, 35);
        	}
        	if (shootControl) {
        	    g.drawRect(330, 340, 140, 35);
        	}
        	
        	g.setColor(Color.WHITE);
        	g.drawString("forward = " + KeyEvent.getKeyText(forwardKey), 600, 60);
        	g.drawString("backward = " + KeyEvent.getKeyText(backwardKey), 600, 80);
        	g.drawString("jump = " + KeyEvent.getKeyText(jumpKey), 600, 100);
        	g.drawString("shoot = " + KeyEvent.getKeyText(shootKey), 600, 120);
        	g.setColor(Color.RED);
        	g.drawString("Place the mouse over the command and press the key you need for it", 200, 60);
        	g.drawString("Press \"Escape\" to go back to main menu", 500, 530);
        }
        
        if (intro) {
        	if (looped4 >= 0 && looped4 <= 599) {
        		g.setColor(Color.BLACK);
        		g.fillRect(0, 0, screenWidth, screenHeight);
        		g.setColor(Color.WHITE);
        		g.drawString("Warning: the lore of this game has been based on the popular mmo", 250, 300);
        		g.drawString("World of Warcraft. A further reeding into its lore is recquired to", 250, 310); 
        		g.drawString("fully understand it", 360, 320);
        	}
        	else if (looped4 >= 600 && looped4 <= 1199) {
        		g.drawImage(pic1, 0, 0, screenWidth, screenHeight, null);
        	}
        	else if (looped4 >= 1200 && looped4 <= 1799) {
        		g.drawImage(pic2, 0, 0, screenWidth, screenHeight, null);
        		
        	}
        	else if (looped4 >= 1800 && looped4 <= 2399) {
        		g.drawImage(pic3, 0, 0, screenWidth, screenHeight, null);       		
        	}        	
        	else {
        		level1 = true;
        		initialiseGame();
        		intro = false;
        		looped4 = 0;
        	}
        	looped4++;      	
        }
        
        if (firstLevel.bossDead() && level1Boss) {
        	looped4++;
        	if (looped4 >= 600) {
        		betweenLevels = true;
        		level1Boss = false;
        		looped4 = 0;
        	}
        }
        
        if (betweenLevels) {
        	looped4++;
        	if (looped4 >= 0 && looped4 <= 599) {
        		g.drawImage(pic4, 0, 0, screenWidth, screenHeight, null);
        	}
        	else {
        		level2 = true;
        		initialiseGame();
        		betweenLevels = false;
        		looped4 = 0;
        	}
        }
        
        if (secondLevel.bossDead() && level2Boss) {
        	looped4++;
        	if (looped4 >= 600) {
        		end = true;
        		level2Boss = false;
        		looped4 = 0;
        	}
        }
        
        if (end) {
        	looped4++;
        	if (looped4 >= 0 && looped4 <= 599) {
        		g.drawImage(pic5, 0, 0, screenWidth, screenHeight, null);
        	}
        	else {
        		mainMenu = true;
        		end = false;
        		looped4 = 0;
        		looped3 = 0;
        	}
        }
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
    
    /**
     * Update any sprites and check for collisions
     * 
     * @param elapsed The elapsed time between this call and the previous call of elapsed
     */   
    
    // update()
    public void update(long elapsed)
    {
    	if (((level1 || level1Boss) && (player.getY() + player.getHeight()) > (firstLevel.getTileMap().getMapHeight() * 30 - 10)) ||
    			((level2 || level2Boss) && (player.getY() + player.getHeight()) > (secondLevel.getTileMap().getMapHeight() * 30 - 10))) {
    		dead = true;
    	}
    	//if the player is dead skip updating and go straight to game over screen
    	if (dead) {
    		return;
    	}
    	
    	
    	if (level1 && looped3 == 0) {
    		Sound bgSound = new Sound("sounds/backgroundSound1.wav");
    		bgSound.start();    		
    	}
    	looped3 += elapsed;
    	if (level1 && looped3 >= 31000) {
    		looped3 = 0;
    	}
    	
    	if (level1Boss && looped5 == 0) {
    		FilterSound bgSound = new FilterSound("sounds/backgroundSound1.wav");
    		bgSound.start();    		
    	}
    	looped5 += elapsed;
    	if (level1Boss && looped5 >= 31000) {
    		looped5 = 0;
    	}
    	
    	if (level2 && looped6 == 0) {
    		Sound bgSound = new Sound("sounds/backgroundSound1.wav");
    		bgSound.start();
    	}
    	looped6 += elapsed;
    	if (level2 && looped6 >= 31000) {
    		looped6 = 0;
    	}
    	
    	if (level2Boss && looped7 == 0) {
    		Sound bgSound = new Sound("sounds/backgroundSound1.wav");
    		bgSound.start();   		
    	}
    	looped7 += elapsed;
    	if (level2Boss && looped7 >= 31000) {
    		looped7 = 0;
    	}
    	
    	
       	updatePlayer(elapsed);      	
       	player.update(elapsed);
       	arrow.update(elapsed);
       	
       	if (playerInvinsible) {
       		if (player.isVisible()) {
       			player.hide();
       		}
       		else {
       			player.show();
       		}
       		playerInvinsibleTime += elapsed;     				
			if (playerInvinsibleTime >= 4000) {
				playerInvinsible = false;
				player.show();
			}
       	}
       	
       	if (level1) {
       		firstLevel.updateBackground(elapsed, player);
       	}
       	if (level1) {
	       	firstLevel.updateSprites(elapsed, player);
	       	updateCollisions(elapsed, firstLevel.getSprites(), firstLevel.getWeaponSprites(), 
	       			firstLevel.getDeadSprites());
       	}
       	if (level1Boss) {
       		firstLevel.initialBackgroundVelocity();
       		firstLevel.updateBoss(elapsed, player);
       	}
       	if (level1Boss) {
       		if (collision.boundingBoxCollision(player, firstLevel.getBoss()) && !firstLevel.bossDead() && playerDeadCount == 1) {
       			if (collision.boundingCircleCollision(player, firstLevel.getBoss())) {
	       			if (playerDeadCount == 1 && !playerInvinsible) {
	       			    dead = true;
	       			}
	       			else {
	       				if (!playerInvinsible) {
	       				    playerDeadCount--;
	       				    playerInvinsible = true;
	       				}
	       				else {
		       				playerInvinsibleTime += elapsed;     				
		       				if (playerInvinsibleTime >= 4000) {
		       					playerInvinsible = false;
		       				}
		       				else {
		       					if (player.isVisible()) {
		       						player.hide();
		       					}
		       					else {
		       						player.show();
		       					}
		       				}
	       				}
	       			}
       			}
       		}
       		if (collision.boundingBoxCollision(player, firstLevel.getBossWeapon()) && firstLevel.getBossWeapon().isVisible() && playerDeadCount == 1) {
       			if (collision.boundingCircleCollision(player, firstLevel.getBossWeapon())) {
	       			if (playerDeadCount == 1) {
	       			    dead = true;
	       			}
	       			else {
	       				playerDeadCount--;
	       			}
	       		}
       		}
       		for (int i = 0; i <= 2; i++) {
       			if (collision.boundingBoxCollision(player, firstLevel.getBossSprites().get(i)) && !firstLevel.bossDead()) {
       				if (collision.boundingCircleCollision(player, firstLevel.getBossSprites().get(i))) {
	       				if (playerDeadCount == 1 && !playerInvinsible) {
	           			    dead = true;
	           			}
	       				if (!playerInvinsible && playerDeadCount > 1) {
	       				    playerDeadCount--;
	       				    playerInvinsible = true;
	       				}
	           		}
	       		}
       		}
       		if (level1Boss && collision.boundingBoxCollision(arrow, firstLevel.getBoss()) && firstLevel.bossDeadCount() != 0 && 
       				firstLevel.getBossSprites().get(2).getX() <= firstLevel.getTileMap().getTileXC(256, 0)) {
       			if (collision.boundingCircleCollision(arrow, firstLevel.getBoss())) {
	       			firstLevel.decrementBossDeadCount();
	       			arrow.setAndHideArrow(player.getX(), player.getY() + player.getHeight() / 2);
	       			shootingLeft = false;
	       			shootingRight = false;
	       			firstLevel.includeBossSprites();
	       		}
	       	}
       	}
       	
       	if (level2) {
       		secondLevel.updateBackground(elapsed, player);
       		secondLevel.updateSprites(elapsed, player);
       		updateCollisions(elapsed, secondLevel.getSprites(), secondLevel.getWeaponSprites(), 
	       			secondLevel.getDeadSprites());
       	}
       	
       	if ( level2Boss) {
       		secondLevel.initialBackgroundVelocity();
       		secondLevel.updateBoss(elapsed, player);
       		if (collision.boundingBoxCollision(player, secondLevel.getBoss()) && !secondLevel.bossDead()) {
       			if (collision.boundingCircleCollision(player, secondLevel.getBoss())) {
	       			if (playerDeadCount == 1 && !playerInvinsible) {
	       			    dead = true;
	       			}
	   				if (!playerInvinsible && playerDeadCount > 1) {
	   				    playerDeadCount--;
	   				    playerInvinsible = true;
	   				}
	       		}
       		}
       	}
       	
       	if (!secondLevel.bossDead() && level2Boss && collision.boundingBoxCollision(arrow, secondLevel.getBoss()) && secondLevel.bossDeadCount() != 0 &&
       			arrow.isVisible()) {
       		if (collision.boundingCircleCollision(arrow, secondLevel.getBoss())) {
	       		if (!secondLevel.getBoss().getAnimation().equals(secondLevel.getOrcWalk())) {
	       			shootingRight = false;
	       			secondLevel.decrementBossDeadCount();
	       			arrow.hide();
	       			secondLevel.getBoss().setX(secondLevel.getBoss().getX() + 30);
	       			secondLevel.getBoss().setVelocityX(0.2f);
	       		}
	       	}
       	}
    } //end of update()
    
    //update player()
    private void updatePlayer(long elapsed) {
    	if (!arrow.isVisible()) {
    		arrow.setAndHideArrow(player.getX(), player.getY() + player.getHeight() / 2);
    	}
    	
    	if (arrow.getX() >= player.getX() + screenWidth / 2 || 
    	    arrow.getX() <= player.getX() - screenWidth / 2) {
	    	shootingRight = false;
    		shootingLeft = false;
    		looped = 0;
    		
        	//sets the normal coordinates of the arrow and makes it invisible
    		arrow.setAndHideArrow(player.getX(), player.getY() + player.getHeight() / 2);
    	    
    	    //sets the animation to normal walking animation
    	    if (player.getAnimation().equals(player.getShootRight())) {
    	    	player.setAnimation(player.getWalkRight());
    	    	player.setAnimationSpeed(0);
    	    }
    	    else if (player.getAnimation().equals(player.getShootLeft())) {
    	    	player.setAnimation(player.getWalkLeft());
    	    	player.setAnimationSpeed(0);
    	    }
    	}
    	
    	if(shootingRight) {
    		
    		shootingLeft = false;
      		if (((level1 || level1Boss) && collision.solidUnderPlayer(player, 31, 56, firstLevel.getTileMap())) ||
    				(level2 && collision.solidUnderPlayer(player, 31, 56, secondLevel.getTileMap()))) {
			    player.setVelocityX(0);
    		}
    		arrow.shootRight();
			player.shootRight();
       	}
    	
    	if(shootingLeft) {
    		shootingRight = false;
    		if (((level1 || level1Boss) && collision.solidUnderPlayer(player, 31, 56, firstLevel.getTileMap())) ||
    				(level2 && collision.solidUnderPlayer(player, 31, 56, secondLevel.getTileMap()))) {
			    player.setVelocityX(0);
    		}
    		arrow.shootLeft();
    		player.shootLeft();
    	}
    	
       	if(forward && !shootingRight && !shootingLeft) 
       	{
       		player.walkForward();
       		player.setAnimation(walkingRight);
       	}
       	if(backwards && !shootingLeft && !shootingRight) 
       	{
       		player.walkBackward();
       		player.setAnimation(walkingLeft);       		
       	}
       	
       	// the velocity change while jumping is a function of the time elapsed
       	if (player.getVelocityY() < 0.25f && elapsed != 0) {
       		player.jump(elapsed);
       	}
       	
       	if (level1 || level1Boss) {
            jump = collision.handleTileMapCollisions(player,elapsed, 31, 60, firstLevel.getTileMap(), jump);
       	}
       	else if (level2 || level2Boss) {
       		jump = collision.handleTileMapCollisions(player,elapsed, 31, 60, secondLevel.getTileMap(), jump);
       	}
       	
       	if (jump && (level1 || level1Boss || level2 || level2Boss)) {
       		Sound s = new Sound("sounds/jump.wav");
    		s.start();
       		player.setVelocityY(-0.5f);
       		jump = false;      		
       	}
    } //end of updatePlayer()
    
    private void updateCollisions(long elapsed, ArrayList<Sprite> sprites, 
    		ArrayList<Sprite> weaponSprites, boolean[] deadSprites) {
    	 // Then check for any collisions that may have occurred
    	
    	if (level1 || level1Boss) {
            jump = collision.handleTileMapCollisions(player,elapsed, 31, 60, firstLevel.getTileMap(), jump);
    	}
    	else if (level2 || level2Boss) {
    		jump = collision.handleTileMapCollisions(player,elapsed, 31, 60, secondLevel.getTileMap(), jump);
    	}
    	
        if (((level1 || level1Boss) && collision.collisionForArrow(arrow, firstLevel.getTileMap())) ||
        		((level2 || level2Boss) && collision.collisionForArrow(arrow, secondLevel.getTileMap()))) {
        	shootingRight = false;
    		shootingLeft = false;
    		looped = 0;
    		
        	//sets the normal coordinates of the arrow and makes it invisible
    		arrow.setAndHideArrow(player.getX(), player.getY() + player.getHeight() / 2);	
    	    
    	    //sets the animation to normal walking animation
    		if (player.getAnimation().equals(player.getShootRight())) {
    	    	player.setAnimation(player.getWalkRight());
    	    	player.setAnimationSpeed(0);
    	    }
    	    else if (player.getAnimation().equals(player.getShootLeft())) {
    	    	player.setAnimation(player.getWalkLeft());
    	    	player.setAnimationSpeed(0);
    	    }
        }
        if (level1) {
	        for (int i = 0; i <= 3; i++) {
	        	collision.simpleSpriteTileMapCollision(sprites.get(i), firstLevel.getZombieRight(), 
	        			firstLevel.getZombieLeft(), 30, 59, firstLevel.getTileMap());
	        }
	        for (int i = 4; i <= 8; i++) {
	        	collision.simpleSpriteTileMapCollision(sprites.get(i), firstLevel.getSpiderRight(), 
	        			firstLevel.getSpiderLeft(), 31, 30, firstLevel.getTileMap());
	        }
        }
        if (level2) {
        	for (int i = 0; i <= 1; i++) {
	        	collision.simpleSpriteTileMapCollision(sprites.get(i), secondLevel.getDinoRight(), 
	        			secondLevel.getDinoLeft(), 58, 42, secondLevel.getTileMap());
	        }
	        for (int i = 2; i <= 3; i++) {
	        	collision.simpleSpriteTileMapCollision(sprites.get(i), secondLevel.getGoblinRight(), 
	        			secondLevel.getGoblinLeft(), 83, 129, secondLevel.getTileMap());
	        }
	        collision.simpleSpriteTileMapCollision(sprites.get(4), secondLevel.getTigerRight(), 
        			secondLevel.getTigerLeft(), 125, 43, secondLevel.getTileMap());
        }
        for (int i = 0; i < weaponSprites.size(); i++) {
        	if (collision.boundingBoxCollision(player, weaponSprites.get(i)) && weaponSprites.get(i).isVisible()) {
        		if (collision.boundingCircleCollision(player, weaponSprites.get(i))) {
	        		if (playerDeadCount == 1 && !playerInvinsible) {
	       			    dead = true;
	       			}
	   				if (!playerInvinsible && playerDeadCount > 1) {
	   				    playerDeadCount--;
	   				    playerInvinsible = true;
	   				}
	        	}
	        }
        }
        for (int i = 0; i < sprites.size(); i++) {
        	if (collision.boundingBoxCollision(player, sprites.get(i)) && !deadSprites[i]) {
        		if (collision.boundingCircleCollision(player, sprites.get(i)) && !deadSprites[i]) {
	        		if (playerDeadCount == 1 && !playerInvinsible) {
	       			    dead = true;
	       			}
	   				if (!playerInvinsible && playerDeadCount > 1) {
	   				    playerDeadCount--;
	   				    playerInvinsible = true;
	   				}
	        	}
        	}
        	if (collision.boundingBoxCollision(arrow, sprites.get(i)) && arrow.isVisible() && !deadSprites[i]) {
	        		if (collision.boundingCircleCollision(arrow, sprites.get(i))) {
	        		shootingRight = false;
	        		shootingLeft = false;
	        		looped = 0;
	        		deadSprites[i] = true;
	        		
	        		//sets the normal coordinates of the arrow and makes it invisible
	        		arrow.setAndHideArrow(player.getX(), player.getY() + player.getHeight() / 2);	
	        	    
	        	    //sets the animation to normal walking animation
	        		if (player.getAnimation().equals(player.getShootRight())) {
	        	    	player.setAnimation(player.getWalkRight());
	        	    	player.setAnimationSpeed(0);
	        	    }
	        	    else if (player.getAnimation().equals(player.getShootLeft())) {
	        	    	player.setAnimation(player.getWalkLeft());
	        	    	player.setAnimationSpeed(0);
	        	    }
	        	}
        	}
        }    
    }
     
    /**
     * Override of the keyPressed event defined in GameCore to catch our
     * own events
     * 
     *  @param e The event that has been generated
     */
    
	public void keyPressed(KeyEvent e) 
    { 
    	
    	int key = e.getKeyCode();
    	if (controls) {
    		if (forwardControl && key != KeyEvent.VK_ESCAPE) {
    			forwardKey = key;
    		}
    		if (backwardControl && key != KeyEvent.VK_ESCAPE) {
    			backwardKey = key;
    		}
    		if (jumpControl && key != KeyEvent.VK_ESCAPE) {
    			jumpKey = key;
    		}
    		if (shootControl && key != KeyEvent.VK_ESCAPE) {
    			shootKey = key;
    		}
    	}
    	
    	if (key == KeyEvent.VK_ESCAPE) {
    		if (level1 || level1Boss || level2 || level2Boss || controls) {
	    		controls = false;
	    		level1 = false;
	    		level2 = false;
	    		level2Boss = false;
	    		level1Boss = false;
	    		mainMenu = true;
    		}
    		
        }
    	
    	if (key == KeyEvent.VK_UP);
    	
    	if (key == forwardKey)  {
    		forward = true;
    	}
    	
    	if (key == backwardKey)  {
    		backwards = true;
    		
    	}
    }

    


	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_ESCAPE) {
			if (level1 || level1Boss || level2 || level2Boss || controls) {
	    		controls = false;
	    		level1 = false;
	    		level2 = false;
	    		level2Boss = false;
	    		level1Boss = false;
	    		mainMenu = true;
    		}
			if (intro) {
				intro = false;
				level1 = true;
				initialiseGame();
			    looped3 = 0;
			    looped4 = 0;
			}
			if (betweenLevels) {
				betweenLevels = false;
				level2 = true;
				initialiseGame();
			    looped3 = 0;
			    looped4 = 0;
			}
			if (end) {
				end = false;
				mainMenu = true;
			    looped3 = 0;
			    looped4 = 0;
			}
		}
		if (key == forwardKey) {
			forward = false;
			player.setVelocityX(0);
			player.pauseAnimationAtFrame(0);
		}
		if (key == backwardKey) {
			backwards = false;
			player.setVelocityX(0);
			player.pauseAnimationAtFrame(0);
		}
		if (key == jumpKey) {
			if (player.getVelocityY() >= 0.24f) {
				   jump = true;
		    }
		}
		if (key == shootKey) {
			if (level1 || level1Boss || level2 || level2Boss) {
				Sound s = new Sound("sounds/bow.wav");
	    		s.start();
			}
			if(player.getAnimation().equals(walkingRight)) {
				if (!shootingLeft)
				   shootingRight = true;
			}
			else if(player.getAnimation().equals(walkingLeft)) {
				if (!shootingRight) 
				   shootingLeft = true;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (mainMenu && e.getX() >= 90 && e.getX() <= 240 && e.getY() >= 180 && e.getY() <= 207) {
			mainMenu = false;
			mouseOverStart = false;
			intro = true;
		}
		if (mainMenu && e.getX() >= 60 && e.getX() <= 275 && e.getY() >= 240 && e.getY() <= 267) {
			controls = true;
			mouseOverControls = false;
		}
		if (mainMenu && e.getX() >= 110 && e.getX() <= 220 && e.getY() >= 300 && e.getY() <= 327) {
			System.exit(0);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (mainMenu && e.getX() >= 90 && e.getX() <= 240 && e.getY() >= 180 && e.getY() <= 207) {
			mouseOverStart = true;
		}
		else {
			mouseOverStart = false;
		}
		
		if (mainMenu && e.getX() >= 60 && e.getX() <= 275 && e.getY() >= 240 && e.getY() <= 267) {
			mouseOverControls = true;
		}
		else {
			mouseOverControls = false;
		}
		
		if (mainMenu && e.getX() >= 110 && e.getX() <= 220 && e.getY() >= 300 && e.getY() <= 327) {
			mouseOverExit = true;
		}
		else {
			mouseOverExit = false;
		}
		
		if (controls && e.getX() >= 290 && e.getX() <= 500 && e.getY() >= 150 && e.getY() <= 185) {
			forwardControl = true;
		}
		else {
			forwardControl = false;
		}
		
		if (controls && e.getX() >= 280 && e.getX() <= 515 && e.getY() >= 215 && e.getY() <= 245) {
			backwardControl = true;
		}
		else {
			backwardControl = false;
		}
		
		if (controls && e.getX() >= 340 && e.getX() <= 460 && e.getY() >= 275 && e.getY() <= 300) {
			jumpControl = true;
		}
		else {
			jumpControl = false;
		}
		
		if (controls && e.getX() >= 330 && e.getX() <= 470 && e.getY() >= 340 && e.getY() <= 375) {
			shootControl = true;
		}
		else {
			shootControl = false;
		}
	}
}
