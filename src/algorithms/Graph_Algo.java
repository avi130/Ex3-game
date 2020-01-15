package algorithms;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import dataStructure.DGraph;
import dataStructure.edge;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node;
import dataStructure.node_data;
import utils.Point3D;
/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms, Serializable{

	graph mygraph; 


	public Graph_Algo ()
	{
		this.mygraph=new DGraph();
	}


	public Graph_Algo (graph g) {
		// TODO Auto-generated method stub
		mygraph=g;
	}


	/**
	* This method initializes a graph
	@param Graph represents the graph we received
	**/
	@Override
	public void init(graph g) {
		// TODO Auto-generated method stub
		mygraph=g;
	}
	
	/**
	* This method initializes a graph according to a String we received
	@param file_name represents the file we received
	**/
	@Override
	public void init(String file_name) {

		// TODO Auto-generated method stub
		try {
			FileInputStream fi = new FileInputStream(file_name);
			ObjectInputStream oi = new ObjectInputStream(fi);
			Graph_Algo ee = (Graph_Algo)oi.readObject();

			this.mygraph= ee.mygraph;

			System.out.println(this.mygraph.toString());

			oi.close();
			fi.close();			
		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found");
		}
		catch (IOException e)
		{
			System.out.println("Error initializing stream");
		}
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

	}


	/**
	* This method saves a graph as a file
	@param file_name represents the string we received
	**/
	@Override
	public void save(String file_name) {
		// TODO Auto-generated method stub
		try {
			FileOutputStream f = new FileOutputStream(new File(file_name));
			ObjectOutputStream o = new ObjectOutputStream(f);

			// Write objects to file
			o.writeObject(this);
			o.close();
			f.close();			

		}
		catch (FileNotFoundException e)
		{
			System.out.println("File not found");
		}
		catch (IOException e)
		{
			System.out.println("Error initializing stream");
		}

	}

	/**
	* This method checks if a graph is heavy connected
	**/
	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		Collection<node_data> nodes=this.mygraph.getV();

		for(node_data currentNode :nodes) { 

			for(node_data checkNode :nodes) {
				if(shortestPathDist(currentNode.getKey(),checkNode.getKey())== Integer.MAX_VALUE){
					System.out.println(currentNode.getKey());
					System.out.println(checkNode.getKey());
					return false;
				}

			}
		}
		return true;
	}


	/**
	* This method finds the Min Weight between all the Collection
	@param nodes represents the Collection of nodes
	**/
	public int getMInWeight(Collection<node_data> nodes) {
		// TODO Auto-generated method stub
		double x=0;
		double min=Integer.MAX_VALUE;
		int minkey=-1;
		for(node_data current :nodes) {
			if(current.getTag()!=1) {
				x=current.getWeight();
				if(x<min ) {
					min=x;
					minkey=current.getKey();
				}
			}
		}

		return minkey ;
	}



	/**
	* This method finds the shortest distance between 2 points
	@param src represents the starting point
	@param dest represents the ending point
	**/
	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		try {
			Collection<node_data> nodes=this.mygraph.getV();
			int size=nodes.size();
			int i=0;

			for(node_data currentNode :nodes) { //change the weigh to mat for all
				currentNode.setWeight(Integer.MAX_VALUE);
				currentNode.setTag(0);
			}
			this.mygraph.getNode(src).setWeight(0); //change the weigh to 0 in the src



			while(i!=size) {
				int MinNotVisited= getMInWeight(nodes); //gets the min weight node that we didnt visited already
				if(MinNotVisited !=-1 ) {
					mygraph.getNode(MinNotVisited).setTag(1);

					Collection<edge_data> edges=this.mygraph.getE(MinNotVisited);
					if(edges!=null) {
						for(edge_data currentEdge :edges) { //runs all over the adges that goes from current node
							if(currentEdge.getTag()!=1) { //change only the nodes that we didnt visited yet
								int destKey=currentEdge.getDest();
								double canEdgeWeight = currentEdge.getWeight()+ mygraph.getNode(currentEdge.getSrc()).getWeight();
								if(mygraph.getNode(destKey).getWeight() > canEdgeWeight) { //if the current weight is bigger then what i found so change
									//mygraph.getNode(destKey).setInfo(""+mygraph.getNode(currentEdge.getSrc()).getKey());
									mygraph.getNode(destKey).setWeight(canEdgeWeight);
								}//+mygraph.getNode(currentEdge.getSrc()).getWeight()
							}
						}
					}
				}
				i++;
			}

			if(mygraph.getNode(dest).getTag()!=0) {


				return mygraph.getNode(dest).getWeight();
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		return Integer.MAX_VALUE;
	}

	
	/**
	* This method returns the shortestPath between 2 points in List<node_data> 
	@param src represents the starting point
	@param dest represents the ending point
	**/
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		// TODO Auto-generated method stub
		try {
			List <node_data> ans=new LinkedList<node_data>();

			Collection<node_data> nodes=this.mygraph.getV();
			int size=nodes.size();
			int i=0;
			for(node_data currentNode :nodes) { //change the weigh to mat for all
				currentNode.setWeight(Integer.MAX_VALUE);
				currentNode.setTag(0);
				currentNode.setInfo("");
			}
			this.mygraph.getNode(src).setWeight(0); //change the weigh to 0 in the src
			this.mygraph.getNode(src).setInfo(""+this.mygraph.getNode(src).getKey());


			while(i!=size) {
				int MinNotVisited= getMInWeight(nodes); //gets the min weight node that we didnt visited already
				if(MinNotVisited !=-1 ) {
					mygraph.getNode(MinNotVisited).setTag(1);

					Collection<edge_data> edges=this.mygraph.getE(MinNotVisited);
					if(edges!=null) {
						for(edge_data currentEdge :edges) { //runs all over the adges that goes from current node
							if(currentEdge.getTag()!=1) { //change only the nodes that we didnt visited yet
								int destKey=currentEdge.getDest();
								double canEdgeWeight = currentEdge.getWeight()+ mygraph.getNode(currentEdge.getSrc()).getWeight();
								if(mygraph.getNode(destKey).getWeight() > canEdgeWeight) { //if the current weight is bigger then what i found so change
									mygraph.getNode(destKey).setWeight(canEdgeWeight);
									//			mygraph.getNode(destKey).setInfo(mygraph.getNode(currentEdge.getSrc()).getInfo());
									String temp=""+mygraph.getNode(MinNotVisited).getKey();
									mygraph.getNode(destKey).setInfo(temp);
								}
							}
						}
					}
				}
				i++;
			}

			if(mygraph.getNode(dest).getTag()!=0) {

				ans.add(mygraph.getNode(dest));

				while(mygraph.getNode(dest).getKey()!=mygraph.getNode(src).getKey()) {

					int addNode =Integer.parseInt(mygraph.getNode(dest).getInfo());
					ans.add(mygraph.getNode(addNode));
					dest=addNode;
				}
				List <node_data> ansRevers=new LinkedList<node_data>();

				for (int j = 0; j < ans.size(); j++) {
					ansRevers.add(ans.get(ans.size()-1-j));

				}
				return ansRevers;
			}
			else 
				ans=null;
			return ans;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}


		return null;
	}


	/**
	* check only if the targets List is connectet.
	@param targets represents a list of vertices
	**/
	private boolean ListIsConnected(List<Integer> target){
		graph newgraph=new DGraph();
		for (int i = 0; i <target.size() ; i++) {
			newgraph.addNode(mygraph.getNode(target.get(i)));
			try{
				for (edge_data ed: mygraph.getE(target.get(i)) ) {
					if(target.contains(ed.getDest())){
						newgraph.connect(ed.getSrc(),ed.getDest(),ed.getWeight());
					}
				}
			}
			catch (Exception e){
				return false;
			}
		}

		Collection<node_data> nodes=newgraph.getV();

		for(node_data currentNode :nodes) { 

			for(node_data checkNode :nodes) {
				if(shortestPathDist(currentNode.getKey(),checkNode.getKey())== Integer.MAX_VALUE){
					System.out.println(currentNode.getKey());
					System.out.println(checkNode.getKey());
					return false;
				}

			}
		}
		return true;


	}


	/**
	* computes a relatively short path which visit each node in the targets List.
	@param targets represents a list of vertices
	**/
	@Override
	public List<node_data> TSP(List<Integer> targets) {
		// TODO Auto-generated method stub
		try {
				if(this.ListIsConnected(targets)==true) {

					List <Integer> t= new LinkedList<Integer>();
					for(int i=0; i<targets.size(); i++) {
						if(!t.contains(targets.get(i))) {
							t.add(targets.get(i));
						}
					}

					double min=Integer.MAX_VALUE;
					double temp;
					//int i=-1;
					int index=0;
					List <node_data> ans= new LinkedList<node_data>();
					List <node_data> tempans= new LinkedList<node_data>();
					//	int one=t.get(0);
					//t.remove(0);
					//for(Integer mysrc :t ) {
					for(int i=0; i<t.size()-1 ;) {
						int mysrc= t.get(i);
						min=Integer.MAX_VALUE;

						for(Integer mydest :t ) {
							if(mydest!=mysrc) {
								temp=this.shortestPathDist(mysrc, mydest);

								if(temp<min) {
									min=temp;
									index=t.indexOf(mydest);
								}
							}
						}



						tempans=this.shortestPath(mysrc, t.get(index));
						if(tempans!=null)
							ans.addAll(tempans);
						//t.indexOf(mysrc);
						int temp_remove=t.get(index);
						t.remove(t.get(index));
						t.add(0, temp_remove);

						t.remove(	t.indexOf(mysrc));



					}
					for(int i=0 ; i<ans.size()-1;i++) {
						if(ans.get(i)==ans.get(i+1))
							ans.remove(i);
					}


					return ans;
				}
				else {
					return null;

				}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;

	}



	/**
	* This method Compute a deep copy of this graph.
	**/
	@Override
	public graph copy() {//made with Serializable copy(same as save\read from file)
		// TODO Auto-generated method stub
		Object obj = null;
		try {
			// Write the object out to a byte array
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bos);
			out.writeObject(this.mygraph);
			out.flush();
			out.close();

			// Make an input stream from the byte array and read
			// a copy of the object back in.
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			obj = in.readObject();

		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return (graph)obj;

	}



}
