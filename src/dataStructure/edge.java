package dataStructure;

import java.io.Serializable;

public class edge implements edge_data ,Serializable{

	int src;
	int dest;
	double weight;
	int tag;
	String info;
	
	 public edge(int src, int dest) {
		 this.src=src;
		 this.dest=dest;
		 this.weight=0;
		 this.tag=0;
		 this.info="";
		
	}
	 public edge(int src, int dest,double weight,String info, int tag) {
		 this.src=src;
		 this.dest=dest;
		 this.weight=weight;
		 this.tag=tag;
		 this.info=info;
		
	}
	 
	 public edge(int src, int dest,double weight) {
		 this.src=src;
		 this.dest=dest;
		 this.weight=weight;
		 this.tag=0;
		 this.info="";
		
	}
	 public edge(int src, int dest,double weight,String info) {
		 this.src=src;
		 this.dest=dest;
		 this.weight=weight;
		 this.tag=0;
		 this.info=info;
		
	}
	 
	
	/**
	 * The id of the source node of this edge.
	 * @return
	*/
	@Override
	public int getSrc() {
		// TODO Auto-generated method stub
		return this.src;
	}

	/**
	 * The id of the destination node of this edge
	 * @return
	 */
	@Override
	public int getDest() {
		// TODO Auto-generated method stub
		return this.dest;
	}

	/**
	 * @return the weight of this edge (positive value).
	 */
	@Override
	public double getWeight() {
		// TODO Auto-generated method stub
		return this.weight;
	}

	/**
	 * return the remark (meta data) associated with this edge.
	 * @return
	 */
	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return this.info;
	}

	/**
	 * Allows changing the remark (meta data) associated with this edge.
	 * @param String s
	 */
	@Override
	public void setInfo(String s) {
		// TODO Auto-generated method stub
		this.info=s;
	}

	/**
	 * Temporal data (aka color: e,g, white, gray, black) 
	 * which can be used be algorithms 
	 * @return
	 */
	@Override
	public int getTag() {
		// TODO Auto-generated method stub
		return this.tag;
	}

	/** 
	 * Allow setting the "tag" value for temporal marking an edge 
	 * @param t - the new value of the tag
	 */
	@Override
	public void setTag(int t) {
		// TODO Auto-generated method stub
		this.tag=t;
	}
	/*
	 * string to string function
	 */
	@Override
	public String toString() {
		 
		return src + "-----" + weight + "----->" +dest;
	}
	
	
	 public void setWeight(double x) {
			// TODO Auto-generated method stub
		 this.weight=x;
	}

}
