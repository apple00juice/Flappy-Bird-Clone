package Model;

import java.util.ArrayList;
import java.util.List;

import Control.Control;

public class Model {

	Control c;

	public List<Obstacle> ObstacleList = new ArrayList<Obstacle>();
	public List<Background> BackgroundList = new ArrayList<Background>();
	public List<Background> GroundList = new ArrayList<Background>();

	public Bird bird;

	public boolean run;

	public int counter;

	int viewableObstacles;

	public boolean updatingMap = false;

	float birdSpeed = 200;
	float birdJump = 350;
	float gravity = 750;
	
	float birdWidth = 55;
	float birdHeight = 40;

	float bgWidth = 250;
	float bgHeight = 600;
	float bgSpeed = 50;

	float groundHeight = 50f;
	float groundWidth = 250;
	
	float obstacleWidth = 250;
	float gapHeight = 175;
	float minGapHeight = 50;
	
	int distbetwObst = 300;
	

	public Model(Control c) {
		this.c = c;
		bird = new Bird(c.width * 0.2f, c.height / 3 * 2,birdWidth,birdHeight, gravity, birdSpeed);
		
		viewableObstacles = Math.round(Math.round(Math.ceil((c.width * 1f) / distbetwObst))) + 1;

		int vieableBackgrounds = Math.round(Math.round(Math.ceil((c.width * 1f) / bgWidth))) + 2;
		int vieableGrounds = Math.round(Math.round(Math.ceil((c.width * 1f) / groundWidth))) + 2;

		generateBackground(BackgroundList, vieableBackgrounds, -groundHeight, bgSpeed, bgWidth, bgHeight);
		generateBackground(GroundList, vieableGrounds, c.height - groundHeight, birdSpeed, bgWidth, groundHeight);

		resetMap();

	}

	public void tick(double dt) {
		if (run) {

			if (bird.y + bird.height >= c.height-groundHeight) {
				bird.dead = true;
				bird.velY = 0;
				bird.y = (c.height - bird.height - groundHeight);
			} else if (bird.y <= 0) {
				bird.dead = true;
			}

			bird.tick(dt, ObstacleList);

			if (!bird.dead) {
				for (int i = 0; i < ObstacleList.size(); i++) {
					Obstacle tempObject = ObstacleList.get(i);
					tempObject.tick(dt);
					if (!tempObject.passed && tempObject.x < bird.x && tempObject.y == 0) {
						tempObject.passed = true;
						counter++;
					}
				}
				
				for (int i = 0; i < BackgroundList.size(); i++) {
					BackgroundList.get(i).tick(dt);

				}
				
				for (int i = 0; i < GroundList.size(); i++) {

					GroundList.get(i).tick(dt);
				}
			}
			updatingMap = true;
			updateMap();
		}
	}

	public void generateBackground(List<Background> list, int backgrounds, float y, float speed, float bgWidth,
			float bgHeight) {
		list.add(new Background(this, list, 0, y, bgWidth, bgHeight, speed));
		for (int i = 0; i < backgrounds - 1; i++) {
			list.add(new Background(this, list, list.get(0).x + list.size() * bgWidth, y, bgWidth, bgHeight, speed));
		}

	}

	public void updateBackground(Background bg, List<Background> list) {
		Background maxxBackground = list.get(0);

		for (int i = 1; i < list.size(); i++) {
			maxxBackground = list.get(i).x > maxxBackground.x ? list.get(i) : maxxBackground;
		}
		bg.x = maxxBackground.x + maxxBackground.width;

	}

	public void birdJump() {
		if (!bird.dead)
			bird.velY = birdJump;
		run = true;
	}

	public void resetMap() {
		viewableObstacles = Math.round(Math.round(Math.ceil((c.width * 1f) / distbetwObst))) + 1;
		run = false;
		counter = 0;
		ObstacleList.clear();
		bird.y = c.height / 3 * 2;
		bird.dead = false;
		bird.rot = 0;
		
		for (int i = 0; i < viewableObstacles; i++) {
			int gapY = Math.round(Math.round((Math.random() * (c.height/4*3 - gapHeight-minGapHeight-groundHeight)+c.height/4)));

			ObstacleList.add(new Obstacle(c.width / 2 + distbetwObst * i, 0, groundHeight, gapY, birdSpeed));
			ObstacleList.add(new Obstacle(c.width / 2 + distbetwObst * i, gapY + gapHeight, groundHeight,
					c.height - gapY - gapHeight, birdSpeed));
		}

	}

	private void updateMap() {

		int removed = 0;

		for (int i = 0; i < ObstacleList.size(); i++) {
			Obstacle tempObject = ObstacleList.get(i);
			if (tempObject.x + tempObject.width < 0) {

				// remove past obstacle
				ObstacleList.remove(tempObject);
				i--;

				removed++;
			}
		}

		for (int i = 0; i < removed / 2; i++) {

			int gapY = Math.round(Math.round((Math.random() * (c.height - gapHeight-minGapHeight*2-groundHeight)+minGapHeight)));

			ObstacleList.add(new Obstacle(ObstacleList.get(ObstacleList.size() - 1).x + distbetwObst, 0, groundHeight, gapY,
					birdSpeed));

			ObstacleList.add(new Obstacle(ObstacleList.get(ObstacleList.size() - 2).x + distbetwObst,
					gapY + gapHeight, groundHeight, (c.height - gapY - gapHeight), birdSpeed));
		}
		updatingMap = false;
	}

}
