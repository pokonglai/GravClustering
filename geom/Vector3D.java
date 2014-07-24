package geom;

public class Vector3D
{
	public double x;
	public double y;
	public double z;
	
	public Vector3D() { x = 0; y = 0; z = 0; }
	public Vector3D(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public double distSq(double x, double y, double z)
	{
		double dx = this.x - x;
		double dy = this.y - y;
		double dz = this.z - z;
		return dx*dx + dy*dy + dz*dz;
	}
	public double distSq(Vector3D v)
	{
		double dx = this.x - v.x;
		double dy = this.y - v.y;
		double dz = this.z - v.z;
		return dx*dx + dy*dy + dz*dz;
	}
	public double dist(Vector3D v) { return Math.sqrt(distSq(v)); }
	public double dist(double x, double y, double z) { return Math.sqrt(distSq(x,y,z)); }
	
	public String toString() { return "["+x+","+y+","+z+"]"; }
}
