import java.awt.Rectangle;
import java.util.List;

public class Bird {

	private float x, y;
	private float velX, velY;
	private float width = 50, height = 45;

	private float gravity = 500;

	private boolean dead = false;

	public Bird(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void tick(double dt, List<Obstacle> ObstacleList) {
		velY -= gravity * dt;

		collision(ObstacleList);

		y -= velY * dt;

	}

	private void collision(List<Obstacle> ObstacleList) {

		for (int i = 0; i < ObstacleList.size(); i++) {
			Obstacle tempObject = ObstacleList.get(i);

			if (getBound().intersects(tempObject.getBounds())) {
				dead = true;
				if (x + getWidth() / 10 * 9 <= tempObject.getX()) {
					x = tempObject.getX() - width;
				} else if (y < tempObject.getY()) {
					velY = 0;
					y = tempObject.getY() - height;
				} else if (y > tempObject.getY() + tempObject.getHeight()) {
					y = tempObject.getY() + tempObject.getHeight();
					y = tempObject.getY() + tempObject.getHeight();
					y = velY = 0;
				}
			}

		}

	}

	private Rectangle getBound() {
		return new Rectangle((int) x, (int) y, (int) width, (int) height);
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
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

	public float getGravity() {
		return gravity;
	}

	public void setGravity(float gravity) {
		this.gravity = gravity;
	}

}
