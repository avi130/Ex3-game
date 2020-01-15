package gameClient;

import java.util.Collection;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Server.game_service;
import dataStructure.graph;
import utils.Point3D;

public class robots {


	game_service game;
	graph praph;
	int id;
	int src;
	int dest;
	int speed;
	double value;
	Point3D pos;

	
	public robots(int id, int speed, int src, int dest, Point3D pos, double value) {
		this.id = id;
		this.speed = speed;
		this.src = src;
		this.dest = dest;
		this.pos = pos;
		this.value = value;
		
	}

	public robots(game_service game, graph p) {

		Collection<Integer> a= new LinkedList<Integer>();
		try {
			String g = game.getGraph();
			double xscale=0;
			double yscale=0;
			double xmin=Integer.MAX_VALUE;
			double ymin= Integer.MAX_VALUE;
			double xmax=Integer.MIN_VALUE;
			double ymax= Integer.MIN_VALUE;



			JSONObject	graph = new JSONObject(g);

			JSONArray nodes = graph.getJSONArray("Nodes");
			JSONArray edges = graph.getJSONArray("Edges");

			for (int i = 0; i < nodes.length(); ++i) {//find min x&y foe the scale func
				String pos = nodes.getJSONObject(i).getString("pos");
				String[] str = pos.split(",");
				xscale=Double.parseDouble(str[0]);
				if(xscale<xmin) {
					xmin=xscale;
				}
				if(xscale>xmax) {
					xmax=xscale;
				}

				yscale=Double.parseDouble(str[1]);
				if(yscale<ymin) {
					ymin=yscale;
				}		
				if(yscale>ymax) {
					ymax=yscale;
				}
			}

			int i=0;
			for( String fruit: game.getRobots())
			{


				JSONObject ff = new JSONObject(fruit);

				JSONObject ttt = ff.getJSONObject("Robot");
				String pos = ttt.getString("pos");
				int value = ttt.getInt("value");
				int speed = ttt.getInt("speed");
				int src = ttt.getInt("src");
				int dest = ttt.getInt("dest");
				String[] str = pos.split(",");
				double xxscale=Double.parseDouble(str[0]);
				double yyscale=Double.parseDouble(str[1]);

				int xres =(int) (((xxscale - xmin) / (xmax-xmin)) * (760 - 40) + 40);
				int yres = (int)(((yyscale - ymin) / (ymax-ymin)) * (520 - 80) + 80);
				this.id=i;
				this.pos=new Point3D(xres,yres);
				this.game=game;
				this.praph=p;
				this.value=value;
				this.dest=dest;	
				this.speed=speed;
				this.src=src;
				

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}







	public int getID(robots x) {
		return x.id;
	}

	public void setID(int id) {
		this.id=id;
	}


	public Point3D getPos(robots x) {
		return x.pos;
	}
	public void setPos(Point3D pos) {
		this.pos=pos;
	}

	public int getDest(robots x) {
		return x.dest;
	}
	public void setDest(int dest) {
		this.dest=dest;
	}

	public int getSrc(robots x) {
		return x.src;
	}
	public void setSrc(int src) {
		this.src=src;
	}

	public double getValue(robots x) {
		return x.value;
	}

	public void setValue(double value) {
		this.value=value;
	}

	public double getSpeed(robots x) {
		return x.speed;
	}

	public void setSpeed(int speed) {
		this.speed=speed;
	}









}
