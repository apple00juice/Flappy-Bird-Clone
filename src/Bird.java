import java.awt.Rectangle;

public class Bird {

	float x,y;
	float velX,velY;
	
	public Bird(float x, float y) {
		
	}
	
	
	
	public float getVelX() {
		return velX;
	}



	public void setVelX(float velX) {
		this.velX = velX;
	}



	public float getVelY() {
		return velY;
	}



	public void setVelY(float velY) {
		this.velY = velY;
	}



	public void tick(double dt){
		
	}
	
	private Rectangle getBound(){
		return new Rectangle((int)x, (int)y, 0, 0);
	}
	
}
