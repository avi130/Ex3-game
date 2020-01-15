package dataStructure;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import utils.Point3D;


public class DGraph implements graph,Serializable {

	Map<Integer, node_data> hmap1 = new HashMap<Integer,node_data>();
	Map<Integer, HashMap<Integer, edge_data>> hmap2 = new HashMap<Integer,HashMap<Integer, edge_data>>();


	int MC=0;
	int edgesize=0;


	public DGraph() 
	{
		hmap1=new HashMap<Integer,node_data>();
		hmap2=new HashMap<Integer, HashMap<Integer,edge_data>>();
	}



	public DGraph(Collection<node_data> nodes, Collection<edge_data> edges) {

		for(node_data n : nodes ) {
			hmap1.put(n.getKey(),n);
		}
		for ( edge_data e :edges) {
			connect(e.getSrc(), e.getDest(), e.getWeight());
		}
	}

	
	private double scale(double data, double r_min, double r_max, 
			double t_min, double t_max)
	{
		
		double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
		return res;
	}


	
	
	public void init( String jsonString) {
		try {
			DGraph x=new DGraph();
			 double xscale=0;
			 double yscale=0;
			 double xmin=Integer.MAX_VALUE;
			 double ymin= Integer.MAX_VALUE;
			 double xmax=Integer.MIN_VALUE;
			 double ymax= Integer.MIN_VALUE;
			 
			 JSONObject graph = new JSONObject(jsonString);
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
			 
			 
			 
			for (int i = 0; i < nodes.length(); ++i) {
				 int id = nodes.getJSONObject(i).getInt("id");
				 String pos = nodes.getJSONObject(i).getString("pos");
				 String[] str = pos.split(",");
				 xscale=Double.parseDouble(str[0]);
				 
				 int xp=(int)scale(xscale, xmin, xmax, 0+40, 800-40);
				 
				 yscale=Double.parseDouble(str[1]);
				 int yp=(int)scale(yscale, ymin, ymax, 0+80, 600-40);
				 
				 
				 Point3D p = new Point3D(xp,yp);
				 
				x.addNode(new node(id, p));
			}
			for (int i = 0; i < edges.length(); ++i) {
				 int s = edges.getJSONObject(i).getInt("src");
				 int d = edges.getJSONObject(i).getInt("dest");
				 double w = edges.getJSONObject(i).getDouble("w");
				x.connect(s, d, w);
			}
			this.hmap1=x.hmap1;
			this.hmap2=x.hmap2;
			this.edgesize=x.edgesize;
			this.MC=x.MC;
		
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}




	/**
	 * This method return the node_data by the node_id. if not exist  returns null
	@param key represents the key by which to search for the vertex
	 **/
	@Override
	public node_data getNode(int key) {
		// TODO Auto-generated method stub	
		if(hmap1.containsKey(key))
			return hmap1.get(key);
		else
			return null;
	}

	/**
	 * This method searches for a edge by the src and dest and returns the edege if not exist - returns null
	@param src represents the first vertex
	@param src represents the second vertex
	 **/
	@Override
	public edge_data getEdge(int src, int dest) {
		// TODO Auto-generated method stub
		if( src == dest)
			return null;
		if((hmap1.get(src)==null) || (hmap1.get(dest)==null))
			return null;
		//node_data key=hmap1.get(src);
		if(hmap2.containsKey(src)) {
			if(hmap2.get(hmap1.get(src).getKey()).containsKey(dest)) 
				return hmap2.get(src).get(dest);
		}
		return null;


	}

	/**
	 * This method will add a new node to the graph with the given node_data
	@param n represents the details of the vertex we will add
	 **/
	@Override
	public void addNode(node_data n) {
		// TODO Auto-generated method stub
		if((!hmap1.containsKey(n.getKey()))) {
			hmap1.put(n.getKey(), n);
			MC++;
		}
	}


	/**
	 * This method connects 2 nodes
	@param src represents the start nodes
	@param dest represents the second nodes
	@param w represents the weight of the edge
	 **/
	@Override
	public void connect(int src, int dest, double w) {
		// TODO Auto-generated method stub
		try {
			if(src!=dest && w>=0) {

				if(hmap1.containsKey(src)&&hmap1.containsKey(dest)) {
					if(hmap2.containsKey(src)==true ) {
						if(hmap2.get(src).containsKey(dest)==true) {
							if(hmap2.get(src).get(dest).getWeight()!=w) {
								edge_data edge=new edge(src,dest,w);
								hmap2.get(src).put(dest,edge);
								MC++;
							}
						}
					}
					if(hmap2.containsKey(src)==false) //check if there is a hashmap for key src
					{
						HashMap<Integer, edge_data> edgesVer=new HashMap<Integer,edge_data> ();
						hmap2.put(src, edgesVer);

					}
					if(hmap2.get(src).containsKey(dest)==false)//check if the edge is already exist
					{
						edge_data edge=new edge(src,dest,w);
						hmap2.get(src).put(dest,edge);
						edgesize++;
						MC++;


					}

				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}



	/**
	 * This method return a pointer (shallow copy) for the
	 * collection representing all the nodes in the graph. 
	 * @return Collection<node_data>
	 */
	@Override
	public Collection<node_data> getV() {
		// TODO Auto-generated method stub	
		return hmap1.values();
	}

	/**
	 * This method return a pointer (shallow copy) for the
	 * collection representing all the edges getting out of 
	 * the given node (all the edges starting (source) at the given node). 
	 * @return Collection<edge_data>
	 */
	@Override
	public Collection<edge_data> getE(int node_id) {
		// TODO Auto-generated method stub
		if(hmap1.containsKey(node_id)&& hmap2.containsKey(node_id)) {
			return hmap2.get(node_id).values();	
		}
		return null;
	}

	/**
	 * Delete the node (with the given ID) from the graph -
	 * and removes all edges which starts or ends at this node.
	 * @return the data of the removed node (null if none). 
	 * @param key
	 */
	@Override
	public node_data removeNode(int key) {
		// TODO Auto-generated method stub

		try {	
			node_data temp;
			if(hmap1.containsKey(key)){
				temp = this.hmap1.get(key);
			}
			else{return null;}
			if (hmap1.get(key) != null) {
				int x = hmap2.get(key).size();
				hmap2.remove(key);
				edgesize -= x;
			}
			for (int it: hmap2.keySet()) {
				if (hmap2.get(it).containsKey(key)) {
					hmap2.get(it).remove(key);
					edgesize--;
				}
			}
			hmap1.remove(key);// remove the node of the key
			MC++;
			return temp;
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		return null;

	}

	/**
	 * Delete the edge from the graph, 
	 * @param src
	 * @param dest
	 * @return the data of the removed edge (null if none).
	 */
	@Override
	public edge_data removeEdge(int src, int dest) {
		// TODO Auto-generated method stub

		if(this.hmap1.containsKey(src)&&this.hmap1.containsKey(dest))
		{
			MC++;
			if(this.hmap2.get(src).containsKey(dest))
			{
				int a=this.hmap2.get(src).size();
				edgesize=edgesize-a;
				return this.hmap2.get(src).remove(dest);
				//	}

			}
			else 
				throw new RuntimeException ("this edge is not exist");
		}
		else
			throw new RuntimeException ("one of the nodes not exist");
	}

	/** return the number of vertices (nodes) in the graph.
	 *  @return
	 */
	@Override
	public int nodeSize() {
		// TODO Auto-generated method stub
		return hmap1.size();
	}

	/** 
	 * return the number of edges (assume directional graph).
	 * @return
	 */
	@Override
	public int edgeSize() {
		// TODO Auto-generated method stub

		return edgesize;

	}
	/**
	 * return the Mode Count - for testing changes in the graph.
	 * @return
	 */
	@Override
	public int getMC() {
		// TODO Auto-generated method stub
		return MC;
	}

}
