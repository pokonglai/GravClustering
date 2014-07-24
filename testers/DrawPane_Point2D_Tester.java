package testers;

import geom.Vector2D;

import java.util.Random;

import javax.swing.JFrame;

import ui.DrawPane_Point2D;

public class DrawPane_Point2D_Tester 
{
	public static void generateRandomPoints(DrawPane_Point2D pane)
	{
		Random r = new Random();
		int numOfPoints = r.nextInt(900) + 100;
		for (int i = 0; i < numOfPoints; i++)
		{
			double x = r.nextDouble()*pane.width;
			double y = r.nextDouble()*pane.height;
			pane.addPoint(new Vector2D(x, y));
		}
	}
	
	public static void generateBoundingPoints(DrawPane_Point2D pane)
	{
		int w = pane.width;
		int h = pane.height;
		
		pane.addPoint(new Vector2D(0, 0));
		pane.addPoint(new Vector2D(w, 0));
		pane.addPoint(new Vector2D(0, h));
		pane.addPoint(new Vector2D(w, h));
		pane.addPoint(new Vector2D(w/2, h/2));
	}
	
	public static void main(String[] args)
	{
		int width = 800;
		int height = 600;
		
		DrawPane_Point2D pane = new DrawPane_Point2D(width, height);
//		generateRandomPoints(pane);
		generateBoundingPoints(pane);
		
		JFrame f = new JFrame();
		f.setContentPane(pane);
		f.setSize(pane.getSize());
		f.setVisible(true);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
