import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Model {

	List<Obstacle> ObstacleList = new ArrayList<Obstacle>();
	
	private Bird bird;
	
	private int distbetwObst = 10;
	
	public Model() {
		bird = new Bird(0,0);
	}
	
	public void tick(double dt){
		bird.tick(dt);
		updateMap();
	}
	
	private void updateMap(){
		
		for(int i = 0; i < ObstacleList.size(); i++){
			Obstacle tempObject = ObstacleList.get(i);
			if(tempObject.getX()+tempObject.getWidth()<0){
				ObstacleList.remove(tempObject);
				ObstacleList.add(new Obstacle(ObstacleList.get(ObstacleList.size()).getWidth()+distbetwObst, 5, 10, 10));
			}
		}
	}
	
}
