package ui;

import geom.Vector2D;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class DrawPane_Point2D extends DrawPane
{
	protected Color clrDefault;
	
	public HashMap<Color, ArrayList<Vector2D>> points;
	public boolean bFilled;
	public int radius;
	
	public DrawPane_Point2D(int w, int h)
	{
		super(w, h);
		points = new HashMap<Color, ArrayList<Vector2D>>();
		clrDefault = Color.BLACK;
		bFilled = true;
		radius = 6;
	}
	
	public void addPoint(Vector2D v)
	{
		if (points.size() == 0) points.put(clrDefault, new ArrayList<Vector2D>());
		points.get(clrDefault).add(v);
	}
	public void addPoint(Color c, Vector2D v)
	{
		if (points.containsKey(c) == false) points.put(c, new ArrayList<Vector2D>());
		points.get(c).add(v);
	}
	public void clearPoints() { points.clear(); }

	@Override
	public void render(Graphics g)
	{
		for (Entry<Color, ArrayList<Vector2D>> entry : points.entrySet())
		{
			g.setColor(entry.getKey());
			ArrayList<Vector2D> pts = entry.getValue();
			for (Vector2D v : pts) g.fillOval((int)(v.x - radius/2), (int)(v.y - radius/2), radius, radius);
		}
	}
}
