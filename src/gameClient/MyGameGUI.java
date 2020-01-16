package gameClient;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.BasicStroke;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.JButton;

import javax.swing.JFrame;

import javax.swing.JOptionPane;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.DGraph;
import dataStructure.edge;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node;
import dataStructure.node_data;
import utils.Point3D;



import javax.swing.JFrame;
import gameClient.*;
public class MyGameGUI extends JFrame implements ActionListener, MouseListener ,Runnable{


	
	private int choose=0;
	private int type=-1;
	private int on=0;
	boolean draw;
	private Image dbImage;
	private Graphics dbg;
	private int currentRobot;
	private int prevtNode;
	private int nextNode;
	private int counter;

	JButton Buttons;
	JButton Start;
	JButton automatic;
	JButton manual;

	int play=-1;;
	int key;
	static DGraph dgraph;
	boolean draw_level=true;

	Point3D p;
	graph graph2;
	graph gg;
	game_service game;
	fruits frut;
	robots borot;



	public MyGameGUI(graph g) {
		
	}

	public MyGameGUI()
	{
		try {

			this.setSize(1300, 700);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setVisible(true);
			this.addMouseListener(this);
			this.setTitle("The Robots Game");

	
			if(choose==0) {
				String a[]= {"manual","outomatic"};
				type=JOptionPane.showOptionDialog(null, "choose your type of game", "Click a button", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, a, null);	
				String input = JOptionPane.showInputDialog(null,"Enter level");	
				int i=0;
				int inputfrom=-1;
				boolean flag=true;
				while(flag) {
					while(i<1) {
						try {
							inputfrom = Integer.parseInt(input);
							if(0<=inputfrom && 23>=inputfrom) {
								i++;
							}
						} catch (Exception ex) {
						}
						if(i==0) {
							input = JOptionPane.showInputDialog(null,"Enter level");	
						}
					}
					flag=false;
				}

				this.game = Game_Server.getServer(inputfrom); // you have [0,23] games



				String gr = game.getGraph(); //getGraph returns String of edges and nodes
				DGraph gg = new DGraph();
				gg.init(gr);
				this.graph2=gg;
				choose=1;
				//	frut= new fruits(game, graph2);

			}

			repaint();
		}
		catch(Exception ex) {

		} 
		while(choose==1) {}

		if(choose==2 &&type==1) {
			try {
				String info = this.game.toString();			
				JSONObject	line = new JSONObject(info);
				JSONObject ttt = line.getJSONObject("GameServer");
				int rs = ttt.getInt("robots");
				insertRobots(this.game,graph2,rs);
			}

			catch(Exception ex) {

			} 
		}






		if(choose==2 &&type==0) {
			try {
				String info = this.game.toString();			
				JSONObject	line = new JSONObject(info);
				JSONObject ttt = line.getJSONObject("GameServer");
				int rs = ttt.getInt("robots");
				int i=0;
				boolean flag=true;
				String input = JOptionPane.showInputDialog(null,"Enter were you want your robots(only nodes)");	
				while(flag) {
					while(i<rs) {
						try {

							int inputfrom = Integer.parseInt(input);
							if(graph2.getV().contains(graph2.getNode(inputfrom))) {
								this.game.addRobot(inputfrom);
								i++;
							}
						} catch (Exception ex) {
							System.out.println("eror");
							ex.printStackTrace();
						}
						if(i<rs) {
							input = JOptionPane.showInputDialog(null,"Enter were you want your robots(only nodes)");		
						}

					}
					flag=false;
				}
				choose=3;
			}

			catch(Exception ex) {

			} 
		}
		repaint();

		if(JOptionPane.showConfirmDialog(null, "press YES to start the game", "ready?", JOptionPane.YES_OPTION) != JOptionPane.YES_OPTION)
			System.exit(0);
		this.game.startGame();
		on=1;
		int ind=0;
		long dt=50;

		if(type==1) {
			while(this.game.isRunning()) {
				this.moveRobots(this.game, this.graph2);
				try {
					if(ind%2==0) {repaint();}
					TimeUnit.MILLISECONDS.sleep(dt);
					ind++;
				}
				catch(Exception e) {
					e.printStackTrace();
				}

			}

		}
		if(type==0) {
			repaint();
			while(this.game.isRunning()) {
				try {
					if(ind%2==0) {repaint(); game.move();}
					TimeUnit.MILLISECONDS.sleep(dt);
					ind++;
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}

		}
		try {
			counter=0;
			String info = this.game.toString();			
			JSONObject	line = new JSONObject(info);

			JSONObject	ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("grade");
			JOptionPane.showMessageDialog(null, "Game Over: your grade is "+ rs);
			String results = game.toString();
			System.out.println("Game Over: "+results);
			System.exit(0);

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


	}

	public void initGUI(graph g,game_service game) 
	{
		this.graph2=g;
		this.game=game;
		initGUI();
	}



	private void initGUI() 
	{
	}


	public void redraw() {


		try {
			TimeUnit.MILLISECONDS.sleep(100);
		}
		catch (InterruptedException e) {
			System.out.println("Interrupted");
		}
		repaint();
	}

	@Override
	public void run() {

	}








	public LinkedList<Integer> fruit(game_service game, graph p, String what) {

		LinkedList<Integer> a= new LinkedList<Integer>();
		try {
			String g = game.getGraph();
			double xscale=0;
			double yscale=0;
			double xmin=Integer.MAX_VALUE;
			double ymin= Integer.MAX_VALUE;
			double xmax=Integer.MIN_VALUE;
			double ymax= Integer.MIN_VALUE;

			JSONObject graph;

			graph = new JSONObject(g);

			JSONArray nodes = graph.getJSONArray("Nodes");

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
			if(what=="Fruit") {
				for( String fruit: game.getFruits())
				{


					JSONObject ff = new JSONObject(fruit);

					JSONObject ttt = ff.getJSONObject(what);
					String pos = ttt.getString("pos");
					int value = ttt.getInt("value");
					int type = ttt.getInt("type");
					String[] str = pos.split(",");
					double xxscale=Double.parseDouble(str[0]);
					double yyscale=Double.parseDouble(str[1]);

					int xres =(int) (((xxscale - xmin) / (xmax-xmin)) * (1260 - 40) + 40);
					int yres = (int)(((yyscale - ymin) / (ymax-ymin)) * (660 - 80) + 80);
					a.add(xres);
					a.add(yres);
					a.add(type);
					a.add(value);


				}
			}
			if(what=="Robot") {	


				for( String fruit: game.getRobots())
				{


					JSONObject ff = new JSONObject(fruit);

					JSONObject ttt = ff.getJSONObject(what);
					String pos = ttt.getString("pos");
					int id= ttt.getInt("id");
					String[] str = pos.split(",");
					double xxscale=Double.parseDouble(str[0]);
					double yyscale=Double.parseDouble(str[1]);

					int xres =(int) (((xxscale - xmin) / (xmax-xmin)) * (1260 - 40) + 40);
					int yres = (int)(((yyscale - ymin) / (ymax-ymin)) * (660 - 80) + 80);
					a.add(xres);
					a.add(yres);
					a.add(id);


				}
			}	 

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return a;
	}




	@Override
	public void paint (Graphics g)
	{
		dbImage=createImage(1300,700 );
		dbg = dbImage.getGraphics();
		paintComponents(dbg);
		g.drawImage(dbImage, 0, 0, this);

	}


	@Override
	public void paintComponents(Graphics g)
	{
		
		if(choose==1 || on==1) {
			

			if(game.getFruits()!=null) {
				try {
					choose=2;

					BufferedImage apple_image;	
					BufferedImage banana_image;
					try {
						apple_image = ImageIO.read(new File("data/apple.jpg"));
						banana_image= ImageIO.read(new File("data/banana.jpeg"));

						LinkedList<Integer> a= fruit(game, graph2,"Fruit");

						for(int i=0 ; i<a.size()-3; i=i+4) {

							if(a.get(i+2)==-1) {
								g.setColor(Color.gray);	
								g.drawImage(apple_image, (int)a.get(i)-5, (int)a.get(i+1)+5,30, 30, null);
							}


							if(a.get(i+2)==1) {
								g.setColor(Color.black);
								g.drawImage(banana_image, (int)a.get(i)-5, (int)a.get(i+1)+5,30, 30, null);
							}

						}	
						int i=0;
						for( String fruit: game.getFruits())
						{

							JSONObject	ff = new JSONObject(fruit);		
							JSONObject ttt = ff.getJSONObject("Fruit");
							int value = ttt.getInt("value");
							int type = ttt.getInt("type");


							g.setColor(Color.blue);
							g.drawString("value  "+value, a.get(i)+10 ,a.get(i+1)+15);
							g.drawString("type  "+type, a.get(i)+10 ,a.get(i+1)+30);
							i=i+4;


						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					//System.out.println("avar");
					e.printStackTrace();
				}

			}

		}

		if(game!=null) {
			try {	
				String info = game.toString();
				JSONObject line = new JSONObject(info);
				JSONObject t = line.getJSONObject("GameServer");
				int grade = t.getInt("grade");
				g.setColor(Color.black);
				long time= game.timeToEnd();
				Graphics2D g2=(Graphics2D) g;
				g2.setStroke(new BasicStroke(2));
				g2.drawString("Left  00:"+time/1000+"   score is: "+grade, 100 ,100);
				BufferedImage robot_image;

				
					robot_image = ImageIO.read(new File("data/robot.png"));


					LinkedList<Integer> a= fruit(game, graph2,"Robot");
					g.setColor(Color.red);
					for(int i=0 ; i<a.size()-2; i=i+3) {
						//	g.fillOval((int)a.get(i)-5, (int)a.get(i+1)+5, 15, 15);		
						g.drawImage(robot_image, (int)a.get(i)-5, (int)a.get(i+1)+5,40, 40, null);
					}	


					int i=0;
					for( String Robot: game.getRobots())
					{

						JSONObject	ff = new JSONObject(Robot);		
						JSONObject ttt = ff.getJSONObject("Robot");
						int src = ttt.getInt("src");
						int dest = ttt.getInt("dest");


						g.setColor(Color.blue);
						g.drawString("src  "+src, a.get(i)+10 ,a.get(i+1)+15);
						g.drawString("dest  "+dest, a.get(i)+10 ,a.get(i+1)+30);
						i=i+3;


					}
				

			} catch (Exception e ) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		//end of robots
		if(graph2!=null ) {
			for (node_data p : graph2.getV() ) 
			{
				g.setColor(Color.BLUE);
				Point3D srcPoint = p.getLocation();
				g.fillOval((int)srcPoint.x()-7, (int)srcPoint.y()-7, 12, 12);
				g.drawString(""+p.getKey(), (int)srcPoint.x() ,(int)srcPoint.y()-10);														


				if(graph2.getE(p.getKey())!=null) {

					for(edge_data e: graph2.getE(p.getKey())) {


						g.setColor(Color.magenta);
						if(e.getInfo()=="do" ) {
							e.setInfo("");
							g.setColor(Color.black);}
						Point3D destPoint = graph2.getNode(e.getDest()).getLocation();
						g.drawLine((int)srcPoint.x(), (int)srcPoint.y(), (int)destPoint.x(), (int)destPoint.y());

						g.setColor(Color.BLACK);

						g.setColor(Color.yellow);
						g.fillOval((int)((0.1*srcPoint.x()+0.9*destPoint.x())-5), (int)((0.1*srcPoint.y()+0.9*destPoint.y())-5), 10, 10);
						g.setColor(Color.BLUE);
						g.drawString(""+e.getDest() , (int)destPoint.x(), (int)destPoint.y()-10);

					}
				}
			}
		}

	}


	
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int x = e.getX();
		int y = e.getY();
		Point3D location=new Point3D(x, y);

		LinkedList<Integer> a= fruit(game, graph2,"Robot");

		for(int i=0 ; i<a.size()-2; i=i+3) {
			if(Math.abs(x- a.get(i))<20 && Math.abs(y-a.get(i+1))<20){
			
				currentRobot=a.get(i+2);
			}	
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		if(type==0) {
			draw=true;
			int x = e.getX();
			int y = e.getY();
			Point3D location=new Point3D(x, y);
			//System.out.println(x);
			//System.out.println(y);
			moveRobotsManual( game, graph2, location,currentRobot) ;
			repaint();
			draw=false;
		}
	}

	public void moveRobotsManual(game_service game, graph gg,Point3D location,int currentRobot) {
		if(type==0) {
			double x=location.x();
			double y = location.y();
			int dest1=0;
			for(node_data node :gg.getV()) {
				if(Math.abs(node.getLocation().x()-x)<Math.abs(10)  ) {
					if(Math.abs(node.getLocation().y()-y)<Math.abs(10)) {
						dest1=node.getKey();
						//	System.out.println(node.getKey()+"aa");
						//System.out.println(node.getLocation().x()+"   "+node.getLocation().y());
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
								//dest = nextNode(game,gg, src);
								game.chooseNextEdge(rid, dest1);
								//System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
								
							}
						}
					} 
					catch (JSONException e) {e.printStackTrace();}
				}
			}
			repaint();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}

	/** 
	 * Moves each of the robots along the edge, 
	 * in case the robot is on a node the next destination (next edge) is chosen (randomly).
	 * @param game
	 * @param gg
	 * @param log
	 */
	public void moveRobots(game_service game, graph gg) {

		
		if(type==1) {
			List<String> log = game.move();
			if(log!=null) {
				long t = game.timeToEnd();
				for(int i=0;i<log.size();i++) {
					String robot_json = log.get(i);
					try {
						JSONObject line = new JSONObject(robot_json);
						JSONObject ttt = line.getJSONObject("Robot");
						int rid = ttt.getInt("id");
						int src = ttt.getInt("src");
						int dest = ttt.getInt("dest");

					//	System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
						if(dest==-1) {	



							dest = nextNode(game,gg, src);

							if(prevtNode==dest) {
								counter++;
								if(counter==3) {
									System.out.println("wrong");
									Collection<edge_data> edges= gg.getE(src);
									for(edge_data newNext : edges) {
										if(newNext.getDest()!=dest) {
											dest=newNext.getDest();
											counter=0;
											break;
										}
									}
								}
							}

							prevtNode=src;
							nextNode=dest;

							game.chooseNextEdge(rid, dest);

						}
					} 
					catch (JSONException e) {e.printStackTrace();}
				}
			}
		}
		if(type==1) {
			this.addMouseListener(this);
		}

	}





	/**
	 * a very simple random walk implementation!
	 * @param g
	 * @param src
	 * @return
	 */
	public int nextNode(game_service game,graph g, int src) {

		int ans = -1;
		Collection<edge_data> ee = g.getE(src);
		Iterator<edge_data> itr = ee.iterator();
		//edge_data a=fruitLocation(game,g,src);

		List<node_data> a= fruitLocation(game,g,src);
		if (a!=null) {
			node_data nodeAns= a.get(1);
			int keyInt=nodeAns.getKey();
			//ans = a.getDest();
			//return ans;
			System.out.println(keyInt);
			return keyInt;
			
		}
		
		
		if(a==null)
			System.out.println("avi");

		int s = ee.size();
		int r = (int)(Math.random()*s);
		int i=0;
		while(i<r)
		{itr.next();i++;}
		ans = itr.next().getDest();

		return ans;



	}

	public void insertRobots(game_service game, graph gg, int NumOfRobots) {

		LinkedList<Integer> fruitLocation= fruit(game, gg,"Fruit");
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
	public List<node_data> fruitLocation(game_service game, graph gg,int src) {

		LinkedList<Integer> fruitLocation= fruit(game, gg,"Fruit");
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
					if(Math.abs(cal)<Math.abs(15)) {
						fruitLocationList.add(edge);
						System.out.println("in in in in ");
						System.out.println("in in in in ");
						System.out.println("in in in in ");
						System.out.println("in in in in ");
						System.out.println(edge.getDest());
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



	public static void main(String[] args) {
		// TODO Auto-generated method stub

		MyGameGUI g = new MyGameGUI();

	}
}
