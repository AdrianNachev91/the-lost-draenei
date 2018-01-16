import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.ImageIcon;

import game2D.Sprite;
import game2D.Animation;


public class Player extends Sprite{

	private Animation walkingLeft, walkingRight, shootLeft, shootRight;
	
	public Player(Animation anim) {
		super(anim);
        walkingRight = new Animation();
        createAnimation(walkingRight, "mainWalkRight.txt", 200);
        walkingLeft = new Animation();
        createAnimation(walkingLeft, "mainWalkLeft.txt", 200);
        shootLeft = new Animation();
        createAnimation(shootLeft, "shootLeft.txt", 1500);
        shootRight = new Animation();
        createAnimation(shootRight, "shootRight.txt", 1500);
	}
	
	public Animation getWalkRight() {
		return walkingRight;
	}
	
	public Animation getWalkLeft() {
		return walkingLeft;
	}
	
	public Animation getShootLeft() {
		return shootLeft;
	}
	
	public Animation getShootRight() {
		return shootRight;
	}
	
	public void walkForward() {
		playAnimation();
   		setAnimationSpeed(1.8f);
   		setVelocityX(0.16f);
	}
	
	public void walkBackward() {
		playAnimation();       		
   	    setAnimationSpeed(1.8f);
   		setVelocityX(-0.16f);
	}
	
	public void shootLeft() {
		this.pauseAnimationAtFrame(0);
		this.setAnimation(shootLeft);
	}
	
	public void shootRight() {
		this.pauseAnimationAtFrame(0);
		this.setAnimation(shootRight);
	}
	
	public void jump(long elapsed) {
		setAnimationFrame(0);
        setAnimationSpeed(0);
        setVelocityY((float)(getVelocityY() + 0.0015 * elapsed));
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
}
