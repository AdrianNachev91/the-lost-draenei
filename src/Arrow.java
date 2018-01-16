import game2D.Sprite;
import game2D.Animation;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.ImageIcon;

public class Arrow extends Sprite{
	
	private Animation arrowRight, arrowLeft;
	
	public Arrow(Animation anim) {
		super(anim);
		arrowRight = new Animation();
        createAnimation(arrowRight, "arrowRight.txt", 200);
        arrowLeft = new Animation();
        createAnimation(arrowLeft, "arrowLeft.txt", 200);
	}
	
	public void setAndHideArrow(float x, float y) {
		setX(x);
	    setY(y);
	    hide();
	}
	
	public void shootRight() {
		setVelocityX(0.5f);
		setVelocityY(0);
		show();
	}
	
	public void shootLeft() {
		setVelocityX(-0.5f);
		setVelocityY(0);
		show();
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
