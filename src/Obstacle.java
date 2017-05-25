import java.awt.Rectangle;

class Obstacle {
		
		private float x,y;
		private float width,height;
		private boolean passed = false;
		
		public Obstacle(float x, float y, float width, float height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		public void tick(double dt){
			x -= 100*dt;
		}
		
		public Rectangle getBounds(){
			return new Rectangle((int) x, (int) y, (int) width, (int) height);
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

		public float getWidth() {
			return width;
		}

		public void setWidth(float width) {
			this.width = width;
		}

		public float getHeight() {
			return height;
		}

		public void setHeight(float height) {
			this.height = height;
		}

		public boolean isPassed() {
			return passed;
		}

		public void setPassed(boolean passed) {
			this.passed = passed;
		}
		
		
		
	}
