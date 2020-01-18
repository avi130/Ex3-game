package gameClient;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jdi.Location;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import gameClient.*;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

public class algo  {
	
	
	public algo() {
	}
	
	
public static List<node_data> fruitLocation(game_service game, graph gg,int src,LinkedList<Integer> fruitLocation) {
		
		List<edge_data> fruitLocationList= new LinkedList<edge_data>();
		LinkedList<edge_data> fruitTypeList= new LinkedList<edge_data>();
		List<node_data> ans= new LinkedList<node_data>();

		Collection<node_data> nodeList = gg.getV();
		for(node_data node: nodeList) {
			Collection<edge_data> edgeList2 = gg.getE(node.getKey());
			for( edge_data edge: edgeList2 ) {
				int x= edge.getDest();
				double distPoints=Math.sqrt((gg.getNode(x).getLocation().y() - gg.getNode(node.getKey()).getLocation().y()) * (gg.getNode(x).getLocation().y() - gg.getNode(node.getKey()).getLocation().y()) + (gg.getNode(x).getLocation().x() - gg.getNode(node.getKey()).getLocation().x()) * (gg.getNode(x).getLocation().x() - gg.getNode(node.getKey()).getLocation().x()));

				for (int i = 0; i < fruitLocation.size()-3; i=i+4) {
					double xx=fruitLocation.get(i);
					double yy=fruitLocation.get(i+1);
					int type=fruitLocation.get(i+2);
					int value=fruitLocation.get(i+3);

					double dist1=Math.sqrt((gg.getNode(x).getLocation().y() - yy) * (gg.getNode(x).getLocation().y() - yy) + (gg.getNode(x).getLocation().x() - xx) * (gg.getNode(x).getLocation().x() - xx));
					double dist2=Math.sqrt((gg.getNode(node.getKey()).getLocation().y() - yy) * (gg.getNode(node.getKey()).getLocation().y() - yy) + (gg.getNode(node.getKey()).getLocation().x() - xx) * (gg.getNode(node.getKey()).getLocation().x() - xx));
					double cal=dist1+dist2-distPoints;
					if(Math.abs(cal)<Math.abs(0.01)) {
						fruitLocationList.add(edge);
						System.out.println("in in in in ");
						System.out.println("nextNode is"+edge.getDest());
					}

				}

			}
		}

		Graph_Algo ga = new Graph_Algo();
		ga.init(gg);
		double minApple= Integer.MAX_VALUE;
		int myApplekey=-1;
		double minBanana= Integer.MAX_VALUE;
		int myBananakey=-1;

		for(edge_data shortes :fruitLocationList ) {
			int mysrc=shortes.getSrc();
			int mydest=shortes.getDest();
			//	if(mysrc<=mydest)

			if(src==mysrc) {
				ans=ga.shortestPath(src, mydest);
				return ans;
			}



			double x=ga.shortestPathDist(src, mysrc);
			double y=ga.shortestPathDist(src, mydest);
			if(x<=y) {
				if(x<minApple) {
					minApple=x;
					myApplekey=mysrc;
				}

			}
			else {
				if(y<minApple) {
					minApple=y;
					myApplekey=mydest;
				}

			}		
		}
		if(myApplekey!=-1)
			ans=ga.shortestPath(src, myApplekey);

		else { System.out.println("blat");
		ans=null;
		}
		System.out.println(ans.get(1).getKey());
		return ans;
	}
	
	
	
public void insertRobots(game_service game, graph gg, int NumOfRobots ,LinkedList<Integer> fruitLocation) {

	
	List<edge_data> fruitLocationList= new LinkedList<edge_data>();
	List<Integer> fruitTypeList= new LinkedList<Integer>();
	List<Integer> fruitValueList= new LinkedList<Integer>();
	int minValue=Integer.MIN_VALUE;
	int mytype=-1;
	edge_data big=null;

	Collection<node_data> nodeList = gg.getV();
	for(node_data node: nodeList) {
		Collection<edge_data> edgeList2 = gg.getE(node.getKey());
		for( edge_data edge: edgeList2 ) {
			int x= edge.getDest();

			double distPoints=Math.sqrt((gg.getNode(x).getLocation().y() - gg.getNode(node.getKey()).getLocation().y()) * (gg.getNode(x).getLocation().y() - gg.getNode(node.getKey()).getLocation().y()) + (gg.getNode(x).getLocation().x() - gg.getNode(node.getKey()).getLocation().x()) * (gg.getNode(x).getLocation().x() - gg.getNode(node.getKey()).getLocation().x()));

			for (int i = 0; i < fruitLocation.size()-3; i=i+4) {
				double xx=fruitLocation.get(i);
				double yy=fruitLocation.get(i+1);
				int type=fruitLocation.get(i+2);
				int value=fruitLocation.get(i+3);

				double dist1=Math.sqrt((gg.getNode(x).getLocation().y() - yy) * (gg.getNode(x).getLocation().y() - yy) + (gg.getNode(x).getLocation().x() - xx) * (gg.getNode(x).getLocation().x() - xx));
				double dist2=Math.sqrt((gg.getNode(node.getKey()).getLocation().y() - yy) * (gg.getNode(node.getKey()).getLocation().y() - yy) + (gg.getNode(node.getKey()).getLocation().x() - xx) * (gg.getNode(node.getKey()).getLocation().x() - xx));
				double cal=dist1+dist2;
				double cal2=distPoints;

				if(Math.abs(cal-cal2)<Math.abs(0.1) && value>=minValue ) {

					big=edge;
					minValue=value;
					mytype=type;
					if(fruitLocationList.size()<NumOfRobots+1) {
						fruitLocationList.add(big);
						fruitTypeList.add(mytype);
						fruitValueList.add(minValue);
					}
					else {
						int min=Integer.MAX_VALUE;
						for (int j = 0; j < fruitTypeList.size(); j++) {
							if(fruitValueList.get(j)<min)
								min=fruitValueList.get(j);
						}
						fruitLocationList.remove(1);
						fruitTypeList.remove(1);
						fruitLocationList.add(big);
						fruitTypeList.add(mytype);
					}
				}
			}
		}
	}
	for (int i = 0; i < NumOfRobots; i++) {

		big=fruitLocationList.get(i);
		if(minValue!=Integer.MIN_VALUE && fruitTypeList.get(i)==-1 && big!=null){

			int max=Math.max(big.getSrc(), big.getDest());
			game.addRobot(max);
		}
		if(minValue!=Integer.MIN_VALUE && fruitTypeList.get(i)==1  && big!=null){
			int min=Math.min(big.getSrc(), big.getDest());
			game.addRobot(min);
		}

	}
}
	
	

public int nextNode(game_service game,graph g, int src ) {

	int ans = -1;
	Collection<edge_data> ee = g.getE(src);
	Iterator<edge_data> itr = ee.iterator();
	//edge_data a=fruitLocation(game,g,src);
	List<node_data> fruitLocation= fruitLocation(game,g,src,fruits.FruitInfo(game, g));
	if (fruitLocation!=null) {
		node_data nodeAns= fruitLocation.get(1);
		int keyInt=nodeAns.getKey();
		System.out.println("nextNode is"+keyInt);
		return keyInt;

	}


	if(fruitLocation==null)
		System.out.println("avi");

	int s = ee.size();
	int r = (int)(Math.random()*s);
	int i=0;
	while(i<r)
	{itr.next();i++;}
	ans = itr.next().getDest();

	return ans;
}




public void moveRobotsManual(game_service game, graph gg,Point3D location,int currentRobot) {
	
		double x=location.x();
		double y = location.y();
		int dest1=0;
		for(node_data node :gg.getV()) {
			if(Math.abs(node.getLocation().x()-x)<Math.abs(10)  ) {
				if(Math.abs(node.getLocation().y()-y)<Math.abs(10)) {
					dest1=node.getKey();
					break;
				}
			}
		}

		List<String> log = game.move();
		if(log!=null) {
			long t = game.timeToEnd();
			for(int i=0;i<log.size();i++) {
				String robot_json = log.get(i);
				try {
					JSONObject line = new JSONObject(robot_json);
					JSONObject ttt = line.getJSONObject("Robot");
					int rid = ttt.getInt("id");
					int dest = ttt.getInt("dest");

					if(rid==currentRobot) {
						if(dest==-1) {	
							game.chooseNextEdge(rid, dest1);
							//System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));

						}
					}
				} 
				catch (JSONException e) {e.printStackTrace();}
			}
		}
		
}





	
	
	

}
