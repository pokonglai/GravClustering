package testers;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;

import geom.Vector2D;
import geom.VectorND;
import clustering.gravity.GravityClustering;
import clustering.gravity.GravityClustering.ParticleCluster;
import clustering.gravity.Particle;
import ui.DrawPane_Point2D;
import ui.GravClusterPane_Point2D;

public class GravClustering_Tester 
{
	
	public static Random random = new Random(System.currentTimeMillis());
	public static GravityClustering gravClust = new GravityClustering();
	
	// create a cluster containing minN to maxN points that are at most radius away from (x,y)
	public static ArrayList<VectorND> generateCluster_Radial(double x, double y, double radius, int minN, int maxN)
	{
		ArrayList<VectorND> cluster = new ArrayList<VectorND>();
		
		int n = random.nextInt(maxN - minN) + minN + 1;
		
		for (int i = 0; i < n; i++)
		{
			double angle = 2* Math.PI * random.nextDouble();
			double range = radius * Math.sqrt(random.nextDouble());
			
			double dx = x + range * Math.sin(angle);
			double dy = y + range * Math.cos(angle);
			double[] comp = { dx, dy } ;
			cluster.add(new VectorND(comp));
		}
		
		return cluster;
	}
	
	// generate a set clusters
	public static void generateGravTest(int width, int height, int numberOfClusters, GravClusterPane_Point2D pane)
	{
		if (pane.points.size() > 0) pane.clearPoints();
		for (int i = 0; i < numberOfClusters; i++)
		{
			double x = random.nextDouble()*width;
			double y = random.nextDouble()*height;
			double radius = random.nextDouble()*100 + 30; // min 10 pixel radius, max 50 pixel radius
			
			int minN = random.nextInt(50) + 25 + 1;
			int maxN = random.nextInt(200) + 100 + 1;
			
			ArrayList<VectorND> cluster = generateCluster_Radial(x, y, radius, minN, maxN);
			for (VectorND vn : cluster) pane.addPoint(new Vector2D(vn.get(0), vn.get(1)));
		}
		pane.repaint();
	}
	
	public static void runGravTest(GravClusterPane_Point2D pane, int iter)
	{
		ArrayList<VectorND> dataset = new ArrayList<VectorND>();
		for (ArrayList<Vector2D> pt_sets : pane.points.values())
		{
			for (Vector2D pt : pt_sets)
			{
				double[] data = new double[2];
				data[0] = pt.x;
				data[1] = pt.y;
				dataset.add(new VectorND(data));
			}
		}
		gravClust.set(dataset);
		
		HashMap<Integer, ParticleCluster> clusters = gravClust.run(iter);
		pane.clearPoints();
		
		// add all the clustered elements
		for(Entry<Integer, ParticleCluster> entry : clusters.entrySet())
		{
			ParticleCluster cluster = entry.getValue();
			
			Color clr = new Color(random.nextFloat(), random.nextFloat(), random.nextFloat());
			for (Particle p : cluster.elements)
			{
				Vector2D vec = new Vector2D(p.pos.get(0), p.pos.get(1));
				pane.addPoint(clr, vec);
			}
		}
		
		// add the outliers as black
		pane.outliers.clear();
		for (Particle p : gravClust.outliers) pane.outliers.add(new Vector2D(p.pos.get(0), p.pos.get(1)));
		pane.repaint();
		
	}
	
	public static void main(String[] args)
	{
		final int width = 800;
		final int height = 600;
		
		final GravClusterPane_Point2D pane = new GravClusterPane_Point2D(width, height);
		
		JFrame f = new JFrame();
		f.setLayout(new BorderLayout(2,2));
		f.add(pane, BorderLayout.CENTER);
		f.setSize(pane.getSize());
		f.setResizable(false);
		f.addKeyListener(new KeyListener()
		{
			public void keyTyped(KeyEvent e) { }
			public void keyReleased(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_SPACE)
				{
					int clusterN = random.nextInt(10) + 5 + 1;
					generateGravTest(width, height, clusterN, pane);
				}
				
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					runGravTest(pane, 1000);
				}
			}
			public void keyPressed(KeyEvent e) { }
		});
		
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
