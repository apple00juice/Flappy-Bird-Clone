package Model;

import java.awt.Rectangle;

public class Obstacle {
		
		public float x,y;
		public float width,height;
		public boolean passed = false;
		float speed;
		
		public Obstacle(float x, float y,float width,float height,float speed) {
			this.x = x;
			this.y = y;
			this.height = height;
			this.speed = speed;
			this.width = width;
			
		}
		
		public void tick(double dt){
			x -= speed*dt;
		}
		
		public Rectangle getBounds(){
			return new Rectangle((int) x, (int) y, (int) width, (int) height);
		}		
		
		
	}
