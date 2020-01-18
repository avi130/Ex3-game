package gameElements;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jdi.Location;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import gameClient.*;
import dataStructure.graph;
import utils.Point3D;

public class fruits implements fruit_data  {

	game_service game;
	graph praph;
	int id;
	double value;
	int type;
	Point3D pos;

	public fruits() {
	}

	public fruits(int type, double value, Point3D p) {
		this.type = type;
		this.value = value;
		this.pos = p;

	}


	public void init(String jsonString) {
		try {
			JSONObject fruit = new JSONObject(jsonString);
			JSONObject ttt = fruit.getJSONObject("Fruit");
			double value = ttt.getDouble("value");
			int type = ttt.getInt("type");
			String position = ttt.getString("pos");
			String[] arrOfStr = position.split(",", 0);
			double x = Double.parseDouble(arrOfStr[0]);
			double y = Double.parseDouble(arrOfStr[1]);
			Point3D pos = new Point3D(x, y);
			this.value = value;
			this.pos = pos;
			this.type = type;

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	public void locate(double xmin, double ymin, double ymax, double xmax, LinkedList<Integer> a, game_service game) {
		for (String fruit : game.getFruits()) {

			JSONObject ff;
			try {
				ff = new JSONObject(fruit);

				JSONObject ttt = ff.getJSONObject("fruit");
				String pos = ttt.getString("pos");
				int value = ttt.getInt("value");
				int type = ttt.getInt("type");

				String[] str = pos.split(",");
				double xxscale = Double.parseDouble(str[0]);
				double yyscale = Double.parseDouble(str[1]);

				int xres = (int) (((xxscale - xmin) / (xmax - xmin)) * (1260 - 40) + 40);
				int yres = (int) (((yyscale - ymin) / (ymax - ymin)) * (660 - 80) + 80);
				a.add(xres);
				a.add(yres);
				a.add(type);
				a.add(value);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

		*/

	public int getID(fruits x) {
		return x.id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public Point3D getPos(fruits x) {
		return x.pos;
	}

	public void setPos(Point3D pos) {
		this.pos = pos;
	}

	public int getType(fruits x) {
		return x.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getValue(fruits x) {
		return x.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	
	
	
	public static LinkedList<Integer> FruitInfo(game_service game, graph p) {

		LinkedList<Integer> a= new LinkedList<Integer>();
		try {
			String g = game.getGraph();
			double xscale = 0;
			double yscale = 0;
			double xmin = Integer.MAX_VALUE;
			double ymin = Integer.MAX_VALUE;
			double xmax = Integer.MIN_VALUE;
			double ymax = Integer.MIN_VALUE;

			JSONObject graph;

			graph = new JSONObject(g);

			JSONArray nodes = graph.getJSONArray("Nodes");
			JSONArray edges = graph.getJSONArray("Edges");

			for (int i = 0; i < nodes.length(); ++i) {// find min x&y foe the scale func
				String pos = nodes.getJSONObject(i).getString("pos");
				String[] str = pos.split(",");
				xscale = Double.parseDouble(str[0]);
				if (xscale < xmin) {
					xmin = xscale;
				}
				if (xscale > xmax) {
					xmax = xscale;
				}

				yscale = Double.parseDouble(str[1]);
				if (yscale < ymin) {
					ymin = yscale;
				}
				if (yscale > ymax) {
					ymax = yscale;
				}
			}

			for (String fruit : game.getFruits()) {

				JSONObject ff = new JSONObject(fruit);

				JSONObject ttt = ff.getJSONObject("Fruit");
				String pos = ttt.getString("pos");
				int value = ttt.getInt("value");
				int type = ttt.getInt("type");
				String[] str = pos.split(",");
				double xxscale = Double.parseDouble(str[0]);
				double yyscale = Double.parseDouble(str[1]);

				int xres =(int) (((xxscale - xmin) / (xmax-xmin)) * (1260 - 40) + 40);
				int yres = (int)(((yyscale - ymin) / (ymax-ymin)) * (660 - 80) + 80);
				a.add(xres);
				a.add(yres);
				a.add(type);
				a.add(value);

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
	}

}
