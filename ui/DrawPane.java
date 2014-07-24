package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public abstract class DrawPane extends JPanel
{
	public int width;
	public int height;
	
	public Color clrBG;
	
	public DrawPane(int w, int h)
	{
		setupGUI(w, h);
	}
	
	private void setupGUI(int w, int h)
	{
		setLayout(new BorderLayout());
		
		width = w;
		height = h;
		
		clrBG = Color.WHITE;

		setSize(width, height);
	}
	
	public abstract void render(Graphics g);
	public void paintComponent(Graphics g)
	{
		g.setColor(clrBG);
		g.fillRect(0, 0, width, height);
		render(g);
	}
}
