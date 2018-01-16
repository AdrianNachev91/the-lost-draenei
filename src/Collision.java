import game2D.Animation;
import game2D.Sprite;
import game2D.Tile;
import game2D.TileMap;


public class Collision {
	
	/**
     * Checks and handles collisions with the tile map for the
     * given sprite 's'. Initial functionality is limited...
     * 
     * @param s			The Sprite to check collisions for
     * @param elapsed	How time has gone by
     */
	public boolean handleTileMapCollisions(Sprite s, long elapsed, int widestFrame, 
			int highestFrame, TileMap tmap, boolean jump)
    {
    	
    	
    	int playerHeight = s.getHeight();
    	int playerWidth = s.getWidth() + (widestFrame - s.getWidth());
    	float playerX = s.getX();
    	float playerY = s.getY();
        
    	int tileWidth = tmap.getTileWidth();
    	int tileHeight = tmap.getTileHeight();
    	int tileNumberX = (int)(playerX / tileWidth);
    	int tileNumberY = (int)(((playerY + playerHeight) / tileHeight));
    	Tile t = tmap.getTile(tileNumberX, tileNumberY);
    	char tileChar = t.getCharacter();
    	
    	//checks if there is a solid tile under the player
        if (playerY + playerHeight >= tmap.getTileYC(tileNumberX, tileNumberY))
        {
        	if (!nonSolidTile(tileChar)) {
        		s.setY(tmap.getTileYC(tileNumberX, tileNumberY) - playerHeight);
        	}
        }
        
        //ensures that the player doesn't fall through a tile when there is an empty space to the left
        if (playerX + playerWidth - 10 >= tmap.getTileXC(tileNumberX + 1, tileNumberY) && 
       		 !nonSolidTile(tmap.getTile(tileNumberX + 1, tileNumberY).getCharacter()) && nonSolidTile(tileChar)) {
        	s.setY(tmap.getTileYC(tileNumberX + 1, tileNumberY) - playerHeight);
        }
        

        
        //checks if there is a solid tile  to the right of the player
        if ((playerX + playerWidth + 20 >= tmap.getTileXC(tileNumberX + 1, tileNumberY - 1) || 
        		playerX + playerWidth + 20 >= tmap.getTileXC(tileNumberX + 1, tileNumberY - 2)) &&
        		s.getVelocityY() >= 0.25 && s.getHeight() > 35) {
        	if (!nonSolidTile(tmap.getTile(tileNumberX + 1, tileNumberY - 1).getCharacter()) || 
        			!nonSolidTile(tmap.getTile(tileNumberX + 1, tileNumberY - 2).getCharacter()) &&
        			s.getHeight() > 35)
        	    s.setX(tmap.getTileXC(tileNumberX + 1, tileNumberY) - playerWidth);
        }
        
        //checks if there is a solid tile to the left of the player
        if ((playerX <= tmap.getTileXC(tileNumberX, tileNumberY) + 3 || 
        		playerX <= tmap.getTileXC(tileNumberX, tileNumberY - 1) + 3 && 
        		s.getHeight() > 35)) {
        	if (!nonSolidTile(tmap.getTile(tileNumberX - 1, tileNumberY - 1).getCharacter()) ||
        			!nonSolidTile(tmap.getTile(tileNumberX - 1, tileNumberY - 2).getCharacter()) &&
        			s.getHeight() > 35) {
        	    s.setX(tmap.getTileXC(tileNumberX, tileNumberY) + 4);
        	}
        }

        // collision checking for tiles over the player
        if (!nonSolidTile(tmap.getTile(tileNumberX, tileNumberY - 2).getCharacter()) || 
        		!nonSolidTile(tmap.getTile(tileNumberX + 1, tileNumberY - 2).getCharacter())) {
        	if (s.getVelocityY() < 0) {
        	    s.setVelocityY(-s.getVelocityY());
        		s.setY(playerY  + 2);
        	}
        }
        	
        
        //prevents from jumping if in the air
        if (nonSolidTile(tileChar) && nonSolidTile(tmap.getTileChar(tileNumberX + 1, tileNumberY))) {
        	jump = false;
        }
        return jump;
    } // end of handleTileMapCollisions
	
	private boolean nonSolidTile(char t) {
        return ((t == '.') || (t == 'y')
            	|| (t == 'u') || (t == 'i')
            	|| (t == 'o') || (t == 'p')
            	|| (t == 'a'));
    }
	
	// collisionForArrow
	public boolean collisionForArrow(Sprite s, TileMap tmap) {
    	int tileWidth = tmap.getTileWidth();
    	int tileHeight = tmap.getTileHeight();
    	
    	int tileNumberX = (int)(s.getX() / tileWidth);
    	int tileNumberY = (int)((s.getY() + s.getHeight()) / tileHeight);
    	
    	if (s.getX() + s.getWidth() >= tmap.getTileXC(tileNumberX + 1, tileNumberY - 1)) {
        	if (!nonSolidTile(tmap.getTile(tileNumberX + 1, tileNumberY).getCharacter())) {
        		return true;
        	}
    	}
    	
    	if (s.getX() <= tmap.getTileXC(tileNumberX, tileNumberY) + 7) {
        	if (!nonSolidTile(tmap.getTile(tileNumberX - 1, tileNumberY).getCharacter())) {
        		return true;
        	}
    	}
    	
    	return false;
    } // end of collisionForArrow
	
	// a simple collision with the map for the majority of NPC sprites
    public void simpleSpriteTileMapCollision(Sprite s, Animation a1, Animation a2, 
    		int frame1Width, int frame1Height, TileMap tmap) {
    	int tileWidth = tmap.getTileWidth();
    	int tileHeight = tmap.getTileHeight();
    	
    	int tileNumberX = (int)(s.getX() / tileWidth);
    	int tileNumberY = (int)(((s.getY() + s.getHeight() + (frame1Height - s.getHeight())) / tileHeight));
    	
    	if (s.getX() + s.getWidth() + (frame1Width - s.getWidth()) >= tmap.getTileXC(tileNumberX + 1, tileNumberY - 1)) {
        	if (!nonSolidTile(tmap.getTile(tileNumberX + 1, tileNumberY - 1).getCharacter()) || 
        		    nonSolidTile(tmap.getTile(tileNumberX + 1, tileNumberY).getCharacter())) {
                if (s.getVelocityX() > 0) {
        	       s.setVelocityX(-s.getVelocityX());
        	       if (s.getAnimation().equals(a1))
        	    	   s.setAnimation(a2);
        	       else
        	    	   s.setAnimation(a1);
                }
        	}
    	}
    	if (s.getX() <= tmap.getTileXC(tileNumberX, tileNumberY) + 2) {
        	if (!nonSolidTile(tmap.getTile(tileNumberX - 1, tileNumberY - 1).getCharacter()) ||
        			    nonSolidTile(tmap.getTile(tileNumberX - 1, tileNumberY).getCharacter())) {
        		if (s.getVelocityX() < 0) {
        		   s.setVelocityX(-s.getVelocityX());
        	       if (s.getAnimation().equals(a1))
        	    	   s.setAnimation(a2);
        	       else
        	    	   s.setAnimation(a1);
        		}
        	}
        }
    } // end of simpleSpriteTileMapCollision
    
    public boolean solidUnderPlayer (Sprite s, int highestFrame, int widestFrame, TileMap tmap) {
    	float playerY = s.getY();
    	int playerHeight = s.getHeight() + (highestFrame - s.getHeight());
    	float playerX = s.getX();
    	int tileWidth = tmap.getTileWidth();
    	int tileHeight = tmap.getTileHeight();
    	int tileNumberX = (int)(playerX / tileWidth);
    	int tileNumberY = (int)(((playerY + playerHeight) / tileHeight));
    	Tile t = tmap.getTile(tileNumberX, tileNumberY + 1);
    	char tileChar = t.getCharacter();
    	
    	
        if (!nonSolidTile(tileChar)) {
       		return true;
       	}
        
    	return false;
    }
    
    public boolean boundingBoxCollision(Sprite s1, Sprite s2)
    {
    	if ((s1.getX() + s1.getWidth() >= s2.getX()) && (s1.getX() <= s2.getX() + s2.getWidth()) &&
    	(s1.getY() + s1.getHeight() >= s2.getY()) && (s1.getY() <= s2.getY()+ s2.getHeight())) {
    		return true;
    	}
    	return false;
    }
    
    public boolean boundingCircleCollision(Sprite s1, Sprite s2) {
    	float r1 = -1;
    	float r2 = -1;
    	if (s1.getWidth() > s1.getHeight()) {
    		r1 = s1.getWidth() / 2;
    	}
    	else {
    		r1 = s1.getHeight() / 2;
    	}
    	if (s2.getWidth() > s2.getHeight()) {
    		r2 = s2.getWidth() / 2;
    	}
    	else {
    		r2 = s2.getHeight() / 2;
    	}
    	
    	float minimum = r1 + r2;
    	float dx = s1.getX() - s2.getX();
    	float dy = s1.getY() - s2.getY();
    	return (((dx * dx) + (dy * dy)) < (minimum * minimum));
    }
}
