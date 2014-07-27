package ui;

import geom.Vector2D;

import java.awt.Graphics;
import java.util.ArrayList;

public class GravClusterPane_Point2D extends DrawPane_Point2D
{
	public ArrayList<Vector2D> outliers;
	public GravClusterPane_Point2D(int w, int h)
	{
		super(w, h);
		outliers = new ArrayList<Vector2D>();
	}
	
	public void clearPoints()
	{
		super.clearPoints();
		outliers.clear();
	}
	
	public void render(Graphics g)
	{
		super.render(g);
		g.setColor(clrDefault);
		for (Vector2D v : outliers) g.drawOval((int)(v.x - radius/2), (int)(v.y - radius/2), radius, radius);
	}

}
