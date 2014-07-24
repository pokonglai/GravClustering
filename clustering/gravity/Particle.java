package clustering.gravity;

import geom.VectorND;

public class Particle
{
	public VectorND pos;
	public double mass;
	
	public Particle(VectorND v)
	{
		pos = v;
		mass = 1.0;
	}
	
	public Particle(VectorND v, double m)
	{
		pos = v;
		mass = m;
	}
	
	public double distSq(Particle b) { return pos.distSq(b.pos); }
	
	public static double[] MoveVector(Particle a, Particle b, double G)
	{
		int dimension = a.pos.length();
		
		double magnitude = 0.0;
		double[] diff = new double[dimension];
		
		for (int i = 0; i < dimension; i++)	
		{
			diff[i] = b.pos.get(i) - a.pos.get(i);
			magnitude += (diff[i]*diff[i]);
		}
		magnitude = Math.sqrt(magnitude);
		
		double scale = 0.0;
		if (magnitude > 0) scale = (G*a.mass*b.mass)/(2.0*(Math.pow(magnitude, 3)));
		
		double[] move = new double[dimension];
		for (int i = 0; i < dimension; i++)	 move[i] = diff[i]*scale;
		return move;
	}
}
