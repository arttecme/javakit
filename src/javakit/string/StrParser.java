package javakit.string;

import java.util.ArrayList;
import java.util.List;

import javakit.ds.Pair;

public class StrParser
{
	/** String变成byte[]
	 * 例:str2arr("2,4,8", ",", (byte)0) */
	public static byte[] str2arr(String src, String regex, byte arrType)
	{
		String[] strArr = src.split(regex);
		final int size = strArr.length;
		byte[] result = new byte[size];
		for(int i = 0; i < size; i++)
		{
			result[i] = Byte.parseByte(strArr[i]);
		}
		return result;
	}
	
	/** String变成int[]
	 * 例:str2arr("1024,-9,20", ",", (int)0) */
	public static int[] str2arr(String src, String regex, int arrType)
	{
		String[] strArr = src.split(regex);
		final int size = strArr.length;
		int[] result = new int[size];
		for(int i = 0; i < size; i++)
		{
			result[i] = Integer.parseInt(strArr[i]);
		}
		return result;
	}
	
	/** String变成double[]
	 * 例:str2arr("2,3.14,-0.0015699945", ",", (double)0) */
	public static double[] str2arr(String src, String regex, double arrType)
	{
		String[] strArr = src.split(regex);
		final int size = strArr.length;
		double[] result = new double[size];
		for(int i = 0; i < size; i++)
		{
			result[i] = Double.parseDouble(strArr[i]);
		}
		return result;
	}
	
	/** String变成float[]
	 * 例:str2arr("2,4.2,-18.9", ",", (float)0) */
	public static float[] str2arr(String src, String regex, float arrType)
	{
		String[] strArr = src.split(regex);
		final int size = strArr.length;
		float[] result = new float[size];
		for(int i = 0; i < size; i++)
		{
			result[i] = Float.parseFloat(strArr[i]);
		}
		return result;
	}
	
	/** String变成boolean[]
	 * 例:str2arr("true,4,-1,0,false", ",", true) */
	public static boolean[] str2arr(String src, String regex, boolean arrType)
	{
		String[] strArr = src.split(regex);
		final int size = strArr.length;
		boolean[] result = new boolean[size];
		for(int i = 0; i < size; i++)
		{
			if("false".equals(strArr[i]) || "0".equals(strArr[i]) )
			{
				result[i] = false;
			}
			else
			{
				result[i] = true;
			}
		}
		return result;
	}
	
	/** String 9100341:2#9100342:13
	 * 经过 toIntIntPair(str, "#", ":")
	 * 变成List<Pair<Integer, Integer>> */
	public static List<Pair<Integer, Integer>> toIntIntPair(String str, final String itemSpl, final String pairSpl)
	{
		List<Pair<Integer, Integer>> pairs = new ArrayList<>();
		String[] items = str.split(itemSpl);
		for(int i = 0; i < items.length; i++)
		{
			String itemStr = items[i];
			String[] kv = itemStr.split(pairSpl);
			if(kv.length < 2) { break; }
			Pair<Integer, Integer> p = new Pair<>();
			p.first = Integer.parseInt(kv[0]);
			p.second = Integer.parseInt(kv[1]);
			pairs.add(p);
		}
		return pairs;
	}
	
}
