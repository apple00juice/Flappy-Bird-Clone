import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;

import javax.imageio.ImageIO;

class Obstacle {
		
		private float x,y;
		private float width,height;
		private boolean passed = false;
		private BufferedImage image;
		private float imagescale = 3;
		
		public Obstacle(float x, float y,float height) {
			this.x = x;
			this.y = y;
			this.height = height;
			
			try {
				image = ImageIO.read(getClass().getResource("FBC-Tube.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			width = image.getWidth()*imagescale;
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

		public BufferedImage getImage() {
			return image;
		}

		public float getImagescale() {
			return imagescale;
		}
		
		
		
	}
