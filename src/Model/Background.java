package Model;

import java.util.List;

public class Background {

	public float x, y, width, height;
	float bgSpeed;
	Model m;
	List<Background> list;

	public Background(Model m, List<Background> list, float x, float y, float width, float height, float bgSpeed) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.bgSpeed = bgSpeed;
		this.m = m;
		this.list = list;
	}

	public void tick(double dt) {
		x -= bgSpeed * dt;

		if (this.x + width <= 0) {
			m.updateBackground(this, list);
		}
	}

}
