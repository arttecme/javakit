package javakit.ds;

public class Pair<K,V>
{
	public K first;
	public V second;
	
	@Override
	public String toString() 
	{
		return "[first=" + first + ", second=" + second + "]";
	}
}
