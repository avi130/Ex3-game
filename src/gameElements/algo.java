package gameElements;
import Server.game_service;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;


/**
 * This interface represents the set of function needed for auto and manual version of the game
 * you may need to use EPSILON = 0.001
 * @author sarah-han
 */
public interface  algo {

	
	
	
	public  List<node_data> fruitLocation(game_service game, graph gg,int src);
    
    
    public void insertRobots(game_service game, graph gg, int NumOfRobots);
    
    
    
    public int nextNode(game_service game,graph g, int src );

    
    
    public void moveRobotsManual(game_service game, graph gg,Point3D location,int currentRobot);
	
    
    public void moveRobots(game_service game, graph gg);
	
	
	
	
	
	
	
	
	
	
	
	
	
}
