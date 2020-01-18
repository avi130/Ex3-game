package gameElements;
import Server.game_service;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;




public interface robot_data {
	
	
	public LinkedList<Integer> robotsInfo(game_service game, graph p);
	
	
	public void locate(double xmin, double ymin, double ymax, double xmax, LinkedList<Integer> a, game_service game);

	

}
