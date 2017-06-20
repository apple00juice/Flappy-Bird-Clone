package Model;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Bird {

	private float x, y;
	private float velX, velY;
	private float width = 50, height = 45;

	private float gravity;
	private float birdspeed;

	private boolean dead = false;

	private BufferedImage image;

	private float imagescale = 4.5f;

	private float rot;

	public Bird(float x, float y, float gravity, float birdspeed) {
		this.x = x;
		this.y = y;
		this.gravity = gravity;
		this.birdspeed = birdspeed;

		try {
			image = ImageIO.read(getClass().getResource("/FBC-Bird.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		width = image.getWidth() * imagescale;
		height = image.getHeight() * imagescale;

		rot = 0;

	}

	public void tick(double dt, List<Obstacle> ObstacleList) {
		velY -= gravity * dt;

		rot = (float) Math.toDegrees(Math.atan((-velY / (birdspeed * dt*100))));
		if (rot < -25)
			rot = -25;

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
					y = tempObject.getY() - height + 2;
				} else if (y > tempObject.getY() + tempObject.getHeight()) {
					y = tempObject.getY() + tempObject.getHeight();
					y = velY = 0;
				}
				
			}

		}

	}

	public Rectangle getBound() {
		
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

	public BufferedImage getImage() {
		return image;
	}

	public float getImagescale() {
		return imagescale;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getRot() {
		return rot;
	}

	public void setRot(float rot) {
		this.rot = rot;
	}

}
