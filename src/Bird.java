import java.awt.Rectangle;

public class Bird {

	private float x, y;
	private float velX, velY;
	private float width = 50, height = 45;

	public Bird(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(double dt) {
		velY -= 500f*dt;
		
		x -= velX*dt;
		y -= velY*dt;
	}

	private Rectangle getBound() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height);
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
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

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

}
