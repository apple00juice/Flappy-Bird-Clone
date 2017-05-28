import java.util.ArrayList;
import java.util.List;

public class Model {

	Control c;

	public List<Obstacle> ObstacleList = new ArrayList<Obstacle>();

	public Bird bird;

	private int distbetwObst = 500;

	private boolean run;

	private int counter;

	int viewableObstacles;

	public Model(Control c) {
		this.c = c;
		bird = new Bird(c.getWidth() / 5, c.getHeight() / 3 * 2);
		viewableObstacles = Math.round(Math.round(Math.ceil((c.getWidth() * 1f) / distbetwObst))) + 1;
		resetMap();

	}

	public void tick(double dt) {
		if (run) {

			for (int i = 0; i < ObstacleList.size(); i += 2) {
				Obstacle tempObject = ObstacleList.get(i);
				if (!tempObject.isPassed() && tempObject.getX() < bird.getX()) {
					tempObject.setPassed(true);
					counter++;
				}
			}

			if (bird.getY() + bird.getHeight() >= c.getHeight()) {
				bird.setDead(true);
				bird.setVelY(0);
				bird.setY(c.getHeight() - bird.getHeight());
			} else if (bird.getY() <= 0) {
				bird.setDead(true);
			}

			bird.tick(dt, ObstacleList);

			if (!bird.isDead()) {
				for (int i = 0; i < ObstacleList.size(); i++) {
					ObstacleList.get(i).tick(dt);
				}
			}

			updateMap();
		}
	}

	public void SpacePressed() {
		if (!bird.isDead())
			bird.setVelY(150);
		run = true;
	}

	public void resetMap() {
		viewableObstacles = Math.round(Math.round(Math.ceil((c.getWidth() * 1f) / distbetwObst))) + 1;
		run = false;
		counter = 0;
		ObstacleList.clear();
		bird.setY(c.getHeight() / 3 * 2);
		bird.setDead(false);

		for (int i = 0; i < viewableObstacles; i++) {
			int gapHight = 200;
			int gapY = Math.round(
					Math.round((Math.random() * ((c.getHeight() - gapHight) / 2) + ((c.getHeight() - gapHight) / 2))));

			ObstacleList.add(new Obstacle(c.getWidth() / 2 + distbetwObst * i, 0, gapY));
			ObstacleList.add(new Obstacle(c.getWidth() / 2 + distbetwObst * i, gapY + gapHight,
					c.getHeight() - gapY - gapHight));
		}
	}

	private void updateMap() {

		int removed = 0;

		for (int i = 0; i < ObstacleList.size(); i++) {
			Obstacle tempObject = ObstacleList.get(i);
			if (tempObject.getX() + tempObject.getWidth() < 0) {

				// remove past obstacle
				ObstacleList.remove(tempObject);
				i--;

				removed++;
			}
		}

		for (int i = 0; i < removed / 2; i++) {

			int gapHight = 200;
			int gapY = Math.round(Math.round((Math.random() * (c.getHeight() - gapHight))));

			ObstacleList.add(new Obstacle(ObstacleList.get(ObstacleList.size() - 1).getX() + distbetwObst, 0, gapY));

			ObstacleList.add(new Obstacle(ObstacleList.get(ObstacleList.size() - 2).getX() + distbetwObst,
					gapY + gapHight, (c.getHeight() - gapY - gapHight)));
		}

	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getViewableObstacles() {
		return viewableObstacles;
	}

}
