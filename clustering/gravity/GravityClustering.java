package clustering.gravity;

import geom.VectorND;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import util.UnionFind;

/**
 * Based on the gravitational clustering algorithm described by Jonatan Gomez.
 * 
 * See the paper entitled: "A New Gravitational Clustering Algorithm"
 * 
 * @author Po
 *
 */
public class GravityClustering
{
	/**
	 * Single cluster of particles within the input dataset.
	 */
	public class ParticleCluster
	{
		public ArrayList<Particle> elements;
		
		public ParticleCluster()
		{
			elements = new ArrayList<Particle>();
		}
		
		public ParticleCluster(Particle p)
		{
			elements = new ArrayList<Particle>();
			elements.add(p);
		}
		
		public double mass()
		{
			if (USE_UNIT_MASS) return elements.size();
			
			double ret = 0.0;
			for (Particle p : elements) ret += p.mass;
			return ret;
		}
	}
	
	public static boolean USE_UNIT_MASS = true;
	public static double G = 1*Math.pow(10, -4); // gravitational constant
	public static double DELTA_G = 0.001; // constant to avoid the big crunch
	public static double EPS = 1000.0; // minimum distance for two particles to merge
	
	public ArrayList<Particle> outliers; // after clustering this will contain all of the outliers
	public ArrayList<Particle> particles; // all dataset elements
	
	public UnionFind uf;
	
	public GravityClustering()
	{
		particles = new ArrayList<Particle>();
	}
	
	/**
	 * Each VectorND needs to has the same length. Initilize the gravitational clustering algorithm.
	 * @param vectors
	 */
	public boolean set(ArrayList<VectorND> vectors)
	{
		if (vectors.size() == 0) return false;
		particles.clear();
		
		double[] minValues = new double[vectors.size()];
		double[] maxValues = new double[vectors.size()];
		
		int dimensions = vectors.get(0).length();
		int num = vectors.size();
		for (int i = 0; i < dimensions; i++)
		{
			minValues[i] = Double.MAX_VALUE;
			maxValues[i] = Double.MIN_VALUE;
		}
		
		uf = new UnionFind(vectors.size());
		for (int i = 0; i < num; i++)
		{
			VectorND vec = vectors.get(i);
			
			if (dimensions != vec.length()) return false;
			
			for (int j = 0; j < dimensions; j++)
			{
				double comp = vec.get(j);
				minValues[j] = Math.min(minValues[j], comp);
				maxValues[j] = Math.max(maxValues[j], comp);
			}
			
			Particle p = new Particle(new VectorND(vec.elements));
			particles.add(p);
		}
		
		double dist = 0.0;
		for (int i = 0; i < dimensions; i++)
		{
			double min = minValues[i];
			double max = maxValues[i];
			dist += ((max - min) * (max - min));
		}
		EPS = Math.sqrt(dist);
		
		return true;
	}
	
	
	/**
	 * Move two particles closer to each other based on input gravitational constant.
	 * @param a
	 * @param b
	 * @param grav_const
	 */
	private void move(Particle a, Particle b, double grav_const)
	{
		double[] a_move = Particle.MoveVector(a, b, grav_const);
		double[] b_move = Particle.MoveVector(a, b, grav_const);
		
		for (int i = 0; i < a.pos.length(); i++) a.pos.elements[i] += a_move[i];
		for (int i = 0; i < b.pos.length(); i++) b.pos.elements[i] += b_move[i];
	}
	
	
	/**
	 * Merging particles p from cluster B to A
	 * @param a
	 * @param b
	 */
	private void merge(int a, int b) { uf.union(a, b); }
	
	public HashMap<Integer, ParticleCluster> run(int iterations)
	{
		double grav_const = G;
		Random r = new Random (System.currentTimeMillis());
		
		for (int i = 0; i < iterations; i++)
		{
			for (int j = 0; j < particles.size(); j++)
			{
				// a random x_k such that k != j
				int k = r.nextInt(particles.size());
				while (k == j) k = r.nextInt(particles.size());
				
				Particle x_k = particles.get(k);
				Particle x_j = particles.get(j);
				move(x_k, x_j, grav_const);
				
				// update UnionFind data structure if x_j and x_k are 
				// close enough to merge and form a new cluster
				if (x_j.distSq(x_k) < EPS) merge(j, k);
			}
			grav_const = (1-DELTA_G)*grav_const;
		}
		
		// search the union find data structure to determine the actual clusters
		HashMap<Integer, ParticleCluster> clusters = new HashMap<Integer, ParticleCluster>();
		for (int i = 0; i < particles.size(); i++)
		{
			Particle p = particles.get(i);
			int key = uf.find(i);
			
			if (clusters.containsKey(key))
			{
				ParticleCluster c = clusters.get(key);
				c.elements.add(p);
			}
			else
			{
				ParticleCluster c = new ParticleCluster(p);
				clusters.put(key, c);
			}
		}
		
		// run through the clusters and ensure that they are of a certain size
		
		return clusters;
	}
}
