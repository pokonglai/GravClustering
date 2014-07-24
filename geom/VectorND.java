package geom;

/**
 * Operations on two VectorND will return -1 if they are not equal in size.
 * @author Po
 *
 */
public class VectorND 
{
	public double[] elements = null;
	public VectorND(int dim) { elements = new double[dim]; }
	public VectorND(double[] comp)
	{
		elements = new double[comp.length];
		set(comp);
	}
	
	public int length() { return elements == null? 0 : elements.length; }
	
	public double get(int i) 
	{
		if (i >= elements.length) return Double.NaN;
		return elements[i];
	}
	
	public void set(double[] comp)
	{
		if (length() != comp.length) return;
		for (int i = 0; i < comp.length; i++) elements[i] = comp[i];
	}
	
	public double distSq(double[] components)
	{
		if (components.length != length()) return -1;
		else
		{
			double ret = 0;
			for (int i = 0; i < length(); i++)
			{
				double di = components[i] - elements[i];
				ret += (di*di);
			}
			return ret;
		}
	}
	public double distSq(VectorND v)
	{
		if (v.length() != length()) return -1;
		return distSq(v.elements);
	}
	public double dist(VectorND v) { return Math.sqrt(distSq(v)); }
	public double dist(double[] v) { return Math.sqrt(distSq(v)); }
	
	public String toString() 
	{
		StringBuilder sb = new StringBuilder(length() * 2 + 2);
		if (length() > 0)
		{
			sb.append("[");
			for (int i = 0; i < length(); i++)
			{
				sb.append(elements[i]);
				if (i <= length() - 2) sb.append(",");
			}
			sb.append("]");
			return sb.toString();
		}
		else return "[]";
	}
}
