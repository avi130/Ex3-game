package gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.MaskFormatter;

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


public class gui_graph extends JFrame implements ActionListener, MouseListener {

	private static boolean global_flag;
	private static int global_key;
	private int mc;
	private int choose=0;
	private int type=-1;

	MenuItem Node;
	//JTextField tf;
	JButton load;
	JButton save;
	JButton isConnected;
	JButton shortestpathdist;
	JButton shortestpath;
	JButton TSP;
	JButton removeedge;
	JButton removenode;
	JButton changeWaight;


	int key;
	//DGraph graph;

	Point3D p;
	graph graph2;
	game_service game;


	public gui_graph(graph g) {
		this.graph2=g;
		this.mc=g.getMC();
		initGUI();
	}
	public gui_graph(graph g,int x) {
		this.graph2=g;
		this.mc=g.getMC();
		this.game= Game_Server.getServer(x);
		initGUI();
	}


	public gui_graph()
	{
		initGUI();
	}

	public void initGUI(graph g) 
	{
		this.graph2=g;
		initGUI();
	}



	private void initGUI() 
	{
		
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MenuBar menuBar = new MenuBar();
		Menu menu = new Menu("Menu");
		menuBar.add(menu);
		this.setMenuBar(menuBar);

		MenuItem item1 = new MenuItem("load");
		item1.addActionListener(this);
		load= new JButton("load");

		MenuItem item2 = new MenuItem("save");
		item2.addActionListener(this);
		save = new JButton("save");



		menu.add(item1);
		menu.add(item2);



		Menu Algo = new Menu("Algo");
		menuBar.add(Algo);
		this.setMenuBar(menuBar);

		MenuItem item3 = new MenuItem("is Connected");
		item3.addActionListener(this);
		isConnected= new JButton("isConnected");

		MenuItem item4 = new MenuItem("shortest path dist");
		item4.addActionListener(this);
		shortestpathdist = new JButton("shortestpathdist");

		MenuItem item5 = new MenuItem("shortest path");
		item5.addActionListener(this);
		shortestpath = new JButton("shortestpath");

		MenuItem item6 = new MenuItem("TSP");
		item6.addActionListener(this);
		TSP = new JButton("TSP");

		Algo.add(item3);
		Algo.add(item4);
		Algo.add(item5);
		Algo.add(item6);

		Menu Remove = new Menu("Remove");
		menuBar.add(Remove);
		this.setMenuBar(menuBar);

		MenuItem item7 = new MenuItem("remove node");
		item7.addActionListener(this);
		isConnected= new JButton("removenode");

		MenuItem item8 = new MenuItem("remove edge");
		item8.addActionListener(this);
		isConnected= new JButton("removeedge");

		Remove.add(item7);
		Remove.add(item8);

		Menu Change = new Menu("Change/create");
		menuBar.add(Change);
		this.setMenuBar(menuBar);

		MenuItem item9 = new MenuItem("change weight of edeg OR create new edeg and choose weight");
		item9.addActionListener(this);
		isConnected= new JButton("changeWaight");

		Change.add(item9);
		this.addMouseListener(this);
		
}


	@Override
	public void paint(Graphics g)
	{
		//choose the type of game
		if(choose==0) {
		String a[]= {"manual","outomatic"};
		type=JOptionPane.showOptionDialog(null, "choose your type of game", "Click a button", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, a, null);	
		choose=1;
		}
		
		
		// manual mode
		if(choose==1&& type==0) {
			JOptionPane.showMessageDialog(null, "please choose on wich vertex to put your robots");
			
			
			
			choose=2;
		}
		
		
		// outomatic mode
		if(choose==1&& type==1) {
			
			
			
			choose=2; 
		}
		
		
		
		super.paint(g);

		if(graph2!=null) {
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
							e.setInfo("");;
							g.setColor(Color.black);}
						Point3D destPoint = graph2.getNode(e.getDest()).getLocation();
						g.drawLine((int)srcPoint.x(), (int)srcPoint.y(), (int)destPoint.x(), (int)destPoint.y());

						//g.fillOval((int)destPoint.x(), (int)destPoint.y(), 10, 10);
						g.setColor(Color.BLACK);
						g.drawString(""+e.getWeight(),(int)((0.1*srcPoint.x()+0.9*destPoint.x()+10)), (int)((0.1*srcPoint.y()+0.9*destPoint.y()+5)));														

						g.setColor(Color.yellow);
						g.fillOval((int)((0.1*srcPoint.x()+0.9*destPoint.x())-5), (int)((0.1*srcPoint.y()+0.9*destPoint.y())-5), 10, 10);
						g.setColor(Color.BLUE);
						g.drawString(""+e.getDest() , (int)destPoint.x(), (int)destPoint.y()-10);

					}
				}
			}
		}
	}

	private void TSP() {
		try {
			JFrame jinput2 = new JFrame();
			String nodes = JOptionPane.showInputDialog(jinput2,"Enter nodes whith  ,  between each one(without spaces) ");		
			List<Integer> nodesList= new LinkedList<Integer>();
			String temp;
			boolean flag=true;
			int input;
			for(int i=0;( i <nodes.length()) &&flag==true; i++) {
				temp="";
				if(nodes.charAt(i)!=',') {
					while(nodes.charAt(i)!=',') {
						temp+=nodes.charAt(i);
						if(i+1<nodes.length()) {
							i++;
						}
						else {
							flag=false;
							break;
						}

					}
					i--;
				}

				if(temp!="") {
					input = Integer.parseInt(temp);
					nodesList.add(input);

				}
				if(flag==false)
					break;
			}


			Graph_Algo ga = new Graph_Algo();
			ga.init(graph2);
			List <node_data> ans=ga.TSP(nodesList);
			if(ans==null) {
				JOptionPane.showMessageDialog(null, "the graph is not connected");
				return;
			}
			String final_ans="";
			for (int i=0; i<ans.size(); i++) {
				if(i!=0)
					final_ans+=" -> "+ans.get(i).getKey();
				else
					final_ans+=""+ans.get(i).getKey();
			}
			JOptionPane.showMessageDialog(jinput2, "the path is: "+final_ans);
			repaint();

		} catch (Exception ex) {
			System.out.println("eror");
			ex.printStackTrace();
		}

	}


	private void removenode() {
		String input = JOptionPane.showInputDialog(null,"Enter Node");
		int inputfrom = Integer.parseInt(input);
		Graph_Algo ga = new Graph_Algo();
		ga.init(graph2);
		if(graph2.getNode(inputfrom)!=null)
			graph2.removeNode(inputfrom);
		else
			JOptionPane.showMessageDialog(null, "there is no such node");


		repaint();

	}

	
	private void removeedge() {
		String src = JOptionPane.showInputDialog(null,"Enter Node");
		String dest = JOptionPane.showInputDialog(null,"Enter To");
		int inputsrc = Integer.parseInt(src);
		int inputdest = Integer.parseInt(dest);
		Graph_Algo ga = new Graph_Algo();
		ga.init(graph2);
		if(graph2.getEdge(inputsrc, inputdest)!=null && graph2.getNode(inputsrc)!=null )
			graph2.removeEdge(inputsrc, inputdest);
		else 
			JOptionPane.showMessageDialog(null, "there is no such edge");


		repaint();

	}



	private void changeWaight() {
		String src = JOptionPane.showInputDialog(null,"Enter src");
		String dest = JOptionPane.showInputDialog(null,"Enter dest");
		String weight = JOptionPane.showInputDialog(null,"Enter weight");
		int inputsrc = Integer.parseInt(src);
		int inputdest = Integer.parseInt(dest);
		int inputweight = Integer.parseInt(weight);
		Graph_Algo ga = new Graph_Algo();
		ga.init(graph2);
		//graph2.getEdge(inputsrc, inputdest).
		graph2.connect( inputsrc,inputdest, inputweight);

		repaint();



	}



	private void shortestpath() {
		try {

			String fromS = JOptionPane.showInputDialog(null,"Enter From");		
			String to = JOptionPane.showInputDialog(null,"Enter To");


			int inputfrom = Integer.parseInt(fromS);
			int inputto = Integer.parseInt(to);
			Graph_Algo ga = new Graph_Algo();
			ga.init(graph2);
			int x=0;

			boolean contains1=false;
			boolean contains2=false;
			List<node_data> nodesList= new LinkedList<node_data>(graph2.getV());
			for(node_data a : nodesList) {
				if(a.getKey()==inputfrom) {
					contains1=true;
				}
				if(a.getKey()==inputto) {
					contains2=true;
				}
				if(contains1 && contains2) {
					break;
				}

			}
			if(!contains1 || !contains2) {
				JOptionPane.showMessageDialog(null, "you insert a node number that not exist. try again. ");
				return;
			}

			List <node_data> ans= ga.shortestPath(inputfrom, inputto);
			if(ans!=null) {
				x= ans.size();
			}
			else {
				
					JOptionPane.showMessageDialog(null, "there is no way from  "+inputfrom+"  to  "+inputto);
			}
			System.out.println(x);
			
			//	System.out.println(x);

			for (int j = 0; j < x-1; j++) {

				graph2.getEdge(ans.get(j).getKey(), ans.get(j+1).getKey()).setInfo("do");
				if(graph2.getEdge(ans.get(j+1).getKey(), ans.get(j).getKey())!=null)
					graph2.getEdge( ans.get(j+1).getKey(),ans.get(j).getKey()).setInfo("do");

			}
			
			String final_ans="";
			for (int i=0; i<ans.size(); i++) {
				if(i!=0)
					final_ans+=" -> "+ans.get(i).getKey();
				else
					final_ans+=""+ans.get(i).getKey();
			}
			JOptionPane.showMessageDialog(null, "the shortest path is: "+final_ans);
			
			
			
			
			
			
			repaint();
		} catch (Exception ex) {
			//	System.out.println("eror");
			ex.printStackTrace();
		}

	}


	private void shortestpathdist() {
		try {
			JFrame jinput2 = new JFrame();
			String fromS = JOptionPane.showInputDialog(jinput2,"Enter From");		
			String to = JOptionPane.showInputDialog(jinput2,"Enter To");
			int inputfrom = Integer.parseInt(fromS);
			int inputto = Integer.parseInt(to);
			Graph_Algo ga = new Graph_Algo();
			ga.init(graph2);


			boolean contains1=false;
			boolean contains2=false;
			List<node_data> nodesList= new LinkedList<node_data>(graph2.getV());
			for(node_data a : nodesList) {
				if(a.getKey()==inputfrom) {
					contains1=true;
				}
				if(a.getKey()==inputto) {
					contains2=true;
				}
				if(contains1 && contains2) {
					break;
				}

			}
			if(!contains1 || !contains2) {
				JOptionPane.showMessageDialog(null, "you insert a node number that not exist. try again  ");
				return;
			}


			double ans= ga.shortestPathDist(inputfrom, inputto);
			if(ans==Integer.MAX_VALUE) {
				JOptionPane.showMessageDialog(jinput2, "there is no way from  "+inputfrom+"  to  "+inputto);
			}
			else {
				JOptionPane.showMessageDialog(jinput2, "the shortest way takes: "+ans);
				repaint();
			}
		} catch (Exception ex) {
			System.out.println("eror");
			ex.printStackTrace();
		}

	}


	private void isConnected() {
		try {
			JFrame jinput2 = new JFrame();
			Graph_Algo ga = new Graph_Algo();
			ga.init(graph2);

			boolean ans= ga.isConnected();
			if (ans==true)
				JOptionPane.showMessageDialog(jinput2, "the graph is connected");
			else
				JOptionPane.showMessageDialog(jinput2, "the graph is not connected ! !");



		} catch (Exception ex) {
			System.out.println("eror");
			ex.printStackTrace();
		}

	}







	private void save()
	{
		JFileChooser fileChooser = new JFileChooser();
		Graph_Algo savaGraph = new Graph_Algo();
		savaGraph.init(graph2);
		int retval = fileChooser.showSaveDialog(save);
		if (retval == JFileChooser.APPROVE_OPTION) {
			try {
				savaGraph.save(fileChooser.getSelectedFile()+".txt");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

	}


	private void load()
	{
		JFileChooser fileChooser = new JFileChooser();
		Graph_Algo loadGraph = new Graph_Algo();
		int retval = fileChooser.showOpenDialog(load);
		if (retval == JFileChooser.APPROVE_OPTION) {
			try {
				File selectedFile = fileChooser.getSelectedFile();
				loadGraph.init(selectedFile.getAbsolutePath());
				this.graph2 = loadGraph.copy();
				repaint();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		try {
			
			if(choose==2 && type==0) {
			
				
				
			}
					
			global_flag=true;
			int x = e.getX();
			int y = e.getY();
			Point3D location=new Point3D(x, y);
			//node p = new node(location);
			List<node_data> nodes= new LinkedList<node_data>(graph2.getV());
			boolean flag=false;

			//int temp=nodes.get(nodes.size()).getKey();
			int temp=nodes.get(nodes.size()-1).getKey();
			int key=temp;

			while(flag==false) {
				key++;
				for(node_data nodekey: nodes)	{
					if(nodekey.getKey()==key) {
						flag=false;
						break;
					}
					else
						flag=true;
				}

			}
			boolean flag2=true;
			for(node_data nodekey: nodes)	{
				if(Math.abs(x-nodekey.getLocation().x())<30 && Math.abs(y-nodekey.getLocation().y())<30) {
					node p = new node(nodekey.getKey(),nodekey.getLocation());
					graph2.addNode(p);
					flag2=false;
					global_key= nodekey.getKey();
					global_flag=false;
					break;
				}


			}

			if(flag2) {
				node p = new node(key,location);
				graph2.addNode(p);
			}

			repaint();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		try {
			int inputweight=10;
			int x = e.getX();
			int y = e.getY();
			Point3D location=new Point3D(x, y);
			
			
			List<node_data> nodes= new LinkedList<node_data>(graph2.getV());
			boolean flag=false;
		
				
			
			int temp=nodes.get(nodes.size()-1).getKey();
			int key=temp;

			while(flag==false) {
				key++;
				for(node_data nodekey: nodes)	{
					if(nodekey.getKey()==key) {
						flag=false;
						break;
					}
					else
						flag=true;
				}

			}
			
			
			boolean flag2=true;
			
			for(node_data nodekey: nodes)	{
				if(global_flag && Math.abs(x-nodekey.getLocation().x())<20 && Math.abs(y-nodekey.getLocation().y())<20) {					
					graph2.connect( key-1,nodekey.getKey(), inputweight);
					flag2=false;
					break;
				}
				else if(global_flag==false && Math.abs(x-nodekey.getLocation().x())<20 && Math.abs(y-nodekey.getLocation().y())<20) {					
					graph2.connect( global_key ,nodekey.getKey(), inputweight);
					flag2=false;
					break;


				}
			}
			if(global_flag==false && flag2) {
				node p = new node(key,location);
				graph2.addNode(p);
				graph2.connect(global_key, key, inputweight);
			}

			else if(flag2) {
				node p = new node(key,location);
				graph2.addNode(p);
				graph2.connect(key-1, key, inputweight);
			}

			repaint();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String str = e.getActionCommand();

		if(str.equals("save"))
		{
			save();
		}
		if(str.equals("load"))
		{
			load();
		}

		if(str.equals("shortest path"))
		{
			shortestpath();
		}

		if(str.equals("shortest path dist"))
		{

			shortestpathdist();
		}


		if(str.equals("is Connected"))
		{

			isConnected();
		}
		if(str.equals("TSP"))
		{

			TSP();
		}
		if(str.equals("remove node"))
		{

			removenode();
		}
		if(str.equals("remove edge"))
		{

			removeedge();
		}
		if(str.equals("change weight of edeg OR create new edeg and choose weight"))
		{

			changeWaight();
		}
		
	}
	
	
	public int getMc() {
		return this.mc;
	}
	

	


	public static void main(String[] args) {
		// TODO Auto-generated method stub
			graph graph=new DGraph();
			gui_graph g = new gui_graph();
	       
			/*       
	       graph.addNode(new node(1, new Point3D(350, 400, 350), 0));
	        graph.addNode(new node(2, new Point3D(900, 325, 50), 0));
	        graph.addNode(new node(3, new Point3D(250, 700, 10), 0));
	        graph.addNode(new node(4, new Point3D(550, 400, 140), 0));
	        graph.addNode(new node(5, new Point3D(135, 600, 250), 0));
	        graph.addNode(new node(6, new Point3D(930, 850, 350), 0));
	        graph.addNode(new node(7, new Point3D(630, 650, 350), 0));
	        graph.addNode(new node(8, new Point3D(530, 100, 350), 0));
	        graph.addNode(new node(9, new Point3D(870, 570, 370), 0));
	        graph.addNode(new node(10, new Point3D(100, 100, 250), 0));
	      
	        graph.connect(1,2,8);
	        graph.connect(2,1,10);
	        graph.connect(2,3,1);
	        graph.connect(3,2,2);
	        graph.connect(3,4,2);
	        graph.connect(4,5,2);
	        graph.connect(5,3,15);
	        graph.connect(2,6,20);
	        graph.connect(1,10,30);
	        graph.connect(6,7,22);
	        graph.connect(6,8,60);
	        graph.connect(7,3,7);
	        graph.connect(8,9,7);
	        graph.connect(9,2,19);
	        graph.connect(9,8,8);
	        graph.connect(10,8,7);
	        graph.connect(10,6,2);
	        graph.connect(9,5,3);
	        graph.connect(2,5,21);
	 */        
	        g.initGUI(graph);
	        g.setVisible(true);
	     
	       
	    }
		
}
