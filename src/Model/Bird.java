package Model;

import java.awt.Rectangle;
import java.util.List;

public class Bird {

	public float x, y;
	public float velX, velY;
	public float width = 50, height = 45;

	public float gravity;
	public float birdspeed;

	public boolean dead = false;

	public float rot;

	public Bird(float x, float y, float width, float height, float gravity, float birdspeed) {
		this.x = x;
		this.y = y;
		this.gravity = gravity;
		this.birdspeed = birdspeed;

		rot = 0;

	}

	public void tick(double dt, List<Obstacle> ObstacleList) {
		velY -= gravity * dt;

		rot = (float) Math.toDegrees(Math.atan((-velY / (birdspeed * dt * 100))));
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
				if (x + width / 10 * 9 <= tempObject.x) {
					x = tempObject.x - width;
				} else if (y < tempObject.y) {
					velY = 0;
					y = tempObject.y - height + 2;
				} else if (y > tempObject.y + tempObject.height) {
					y = tempObject.y + tempObject.height;
					y = velY = 0;
				}

			}

		}

	}

	public Rectangle getBound() {

		return new Rectangle((int) x + 1, (int) y + 1, (int) width - 2, (int) height - 2);

	}

}
