package gameElements;

import Server.game_service;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;




public interface fruit_data {

	
	
	
	public void init(String jsonString);
	
	
	
	public static LinkedList<Integer> FruitInfo(game_service game, graph p) {
		return null;
	}



	public int getType();



	public double getValue();



	public Point3D getPos();
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
