package javakit.utils;

import java.util.ArrayList;
import java.util.List;

/** 韦恩图工具 */
public class VennUtil 
{
	/** 合并 */
	public static <E> List<E> merge(List<E> leftSrc, List<E> rightSrc)
	{
		List<E> result = new ArrayList<>();
		result.addAll(leftSrc);
		for(E re : rightSrc)
		{
			if(!result.contains(re))
			{
				result.add(re);
			}
		}
		return result;
	}
	
	public static <E> OverlapResult<E> overlap(List<E> leftSrc, List<E> rightSrc)
	{
		OverlapResult<E> result = new OverlapResult<>();
		
		int totalSize = leftSrc.size() + rightSrc.size();
		for(int i = 0; i < totalSize; i++)
		{
			E element = null;
			if( i < leftSrc.size() )
			{
				element = leftSrc.get(i);
			}
			else
			{
				element = rightSrc.get(i-leftSrc.size());
			}
			boolean inLeft = leftSrc.contains(element);
			boolean inRight = rightSrc.contains(element);
			if( inLeft && inRight )
			{
				if( ! result.overlap.contains(element) )
				{
					result.overlap.add(element);
				}
			}
			else if( inLeft )
			{
				result.left.add(element);
			}
			else if( inRight )
			{
				result.right.add(element);
			}
			else
			{
				System.out.println("VennUtil::overlap出现了奇怪的故障");
			}
		}
		return result;
	}
	
//	private static <E> boolean contains(List<E> list, E element)
//	{
//		for(int i = 0; i < list.size(); i++)
//		{
//			E ie = list.get(i);
//			System.err.println("比较" + ie + " ??? " + element);
//			if(ie.equals(element)) { return true; }
//		}
//		return false;
//	}
	
	public static class OverlapResult<E>
	{
		public List<E> left;
		public List<E> overlap;
		public List<E> right;
		
		public OverlapResult()
		{
			left = new ArrayList<>();
			overlap = new ArrayList<>();
			right = new ArrayList<>();
		}
	}
	
}
