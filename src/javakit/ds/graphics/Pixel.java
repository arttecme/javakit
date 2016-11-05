package javakit.ds.graphics;

public class Pixel
{
	public int x;

	public int y;

	public long value;
	
	public Pixel()
	{
	}

	public Pixel(Pixel pixel)
	{
		setLocation(pixel.x, pixel.y);
	}

	public Pixel(int x, int y)
	{
		setLocation(x, y);
	}

	public void setLocation(Pixel pixel)
	{
		setLocation(pixel.x, pixel.y);
	}

	public void setLocation(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.value = (this.y << 16) | this.x;
	}

	public void setLocation(double x, double y)
	{
		int tx = (int) x;
		int ty = (int) y;
		if (((x * 10) % 10) > 5)
			tx++;
		if (((y * 10) % 10) > 5)
			ty++;
		setLocation(tx, ty);
	}

	public int distanceSq(int x, int y)
	{
		return (this.x - x) * (this.x - x) + (this.y - y) * (this.y - y);
	}

	public int distanceSq(Pixel pixel)
	{
		return distanceSq(pixel.x, pixel.y);
	}

	public double distance(int x, int y)
	{
		int n = distanceSq(x, y);
		return Math.sqrt(n);
	}

	public double distance(Pixel pixel)
	{
		return distance(pixel.x, pixel.y);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof Pixel)
		{
			return equals((Pixel)obj);
		}
		return false;
	}
	
	public boolean equals(Pixel pixel)
	{
		if(pixel == null) { return false; }
		if(this.x != pixel.x) { return false; }
		if(this.y != pixel.y) { return false; }
		return true;
	}
	
	public boolean equals(int px, int py)
	{
		if(this.x != px) { return false; }
		if(this.y != py) { return false; }
		return true;
	}

	public String toString()
	{
		return "Pixel[x=" + this.x + ",y=" + this.y + "]";
	}
	
}