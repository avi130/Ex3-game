package dataStructure;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;

import utils.Point3D;

public class node implements node_data,Serializable {
	int key;
	Point3D location;
	int value;
	double weight;
	String info;
	int tag;
	
	public node(node_data nodedata) {
		this.key=nodedata.getKey();
		this.location=nodedata.getLocation();
		this.weight=nodedata.getWeight();
		this.info=nodedata.getInfo();
		this.tag=nodedata.getTag();
	
	
	}
	

	public node() {
		this.key=0; 
		this.weight=0; 
		this.info=""; 
		this.tag=0;
	}
	
	public node(int key,Point3D location) {
		this.key=key;
		this.location=location;
		this.value=Integer.MAX_VALUE;
		this.weight=0;
		this.info="";
		this.tag=0;
	}
	
	
	
	public node(int key) {
		this.key=key;
		this.location=null;
		this.value=Integer.MAX_VALUE;
		this.weight=0;
		this.info="";
		this.tag=0;
	}
	
	public node(Point3D location) {
		this.key=0;
		this.location=location;
		this.value=Integer.MAX_VALUE;
		this.weight=0;
		this.info="";
		this.tag=0;
	}
	
	
	public node(int key,int value,double weight, String info) {
		this.key=key;
		this.location=null;
		this.value=value;
		this.weight=weight;
		this.info=info;
		this.tag=0;
	}

	
	public node(int key, Point3D location ,double weight ) {
		this.key=key;
		this.location=location;
		this.value=0;
		this.weight=weight;
		this.info="";
		this.tag=0;
	}

	
	
	/**
	 * Return the key (id) associated with this node.
	 * @return
	 */
	@Override
	public int getKey() {
		// TODO Auto-generated method stub
		return this.key ;
	}

	/** Return the location (of applicable) of this node, if
	 * none return null.
	 * @return
	 */
	@Override
	public Point3D getLocation() {
		// TODO Auto-generated method stub
		return this.location;
	}
	/** Allows changing this node's location.
	 * @param p - new new location  (position) of this node.
	 */
	@Override
	public void setLocation(Point3D p) {
		// TODO Auto-generated method stub
		this.location=p;
	}

	/**
	 * Return the weight associated with this node.
	 * @return
	 */
	@Override
	public double getWeight() {
		// TODO Auto-generated method stub
		return this.weight;
	}
	/**
	 * Allows changing this node's weight.
	 * @param w - the new weight
	 */
	@Override
	public void setWeight(double w) {
		// TODO Auto-generated method stub
		this.weight=w;
	}

	/**
	 * return the remark (meta data) associated with this node.
	 * @return
	 */
	@Override
	public String getInfo() {
		// TODO Auto-generated method stub
		return this.info;
	}

	/**
	 * Allows changing the remark (meta data) associated with this node.
	 * @param s
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
	 * Allow setting the "tag" value for temporal marking an node - common 
	 * practice for marking by algorithms.
	 * @param t - the new value of the tag
	 */
	@Override
	public void setTag(int t) {
		// TODO Auto-generated method stub
		this.tag=t;
	}
	

   
}