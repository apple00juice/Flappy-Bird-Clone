import java.awt.Rectangle;

public class Model {

	public Model() {
		
	}
	
	public void tick(double dt){
		
	}
	
	private class obstacle {
		
		float x,y;
		float width,height;
		
		public obstacle(float x, float y, float width, float height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}
		
		public Rectangle getBounds(){
			return new Rectangle((int) x, (int) y, (int) width, (int) height);
		}
		
	}
	
}
