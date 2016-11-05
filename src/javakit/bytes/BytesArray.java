package javakit.bytes;

import java.util.ArrayList;

public class BytesArray
{
	private ArrayList<byte[]> list;
	
	public BytesArray()
	{
		list = new ArrayList<byte[]>();
	}
	
	public BytesArray(byte[] ...bs)
	{
		list = new ArrayList<byte[]>();
		push(bs);
	}
	
	/** 获得总byte的集合 */
	public byte[] getBytes()
	{
		byte[] ret = new byte[getBytesLength()];
		int c = 0;
		for(int i = 0; i < list.size(); i++)
		{
			byte[] bytes = list.get(i);
			for(int m = 0; m < bytes.length; m++)
			{
				ret[c++] = bytes[m];
			}
		}
		return ret;
	}
	
	/** 获得总byte的集合长度 */
	public int getBytesLength()
	{
		int len = 0;
		for(int i = 0; i < list.size(); i++)
		{
			len += list.get(i).length;
		}
		return len;
	}

	/** 将一个或多个元素添加到数组的结尾，并返回该数组的新长度。<br>
	 * @param args 要追加到数组中的一个或多个值
	 * @return 一个表示新数组长度的整数 */
	public int push(byte[] ...args)
	{
		for(int i = 0; i < args.length; i++)
		{
			if(args[i] != null)
			{
				list.add(args[i]);
			}
		}
		return list.size();
	}
	
	/** 删除数组中最后一个元素，并返回该元素的值<br>
	 * @return 指定的数组中最后一个元素。  */
	public byte[] pop()
	{
		return list.remove(list.size()-1);
	}
	
	/** 删除数组中第一个元素，并返回该元素。其余数组元素将从其原始位置 i 移至 i-1。<br>
	 * @return 数组中的第一个元素  */
	public byte[] shift()
	{
		if(list.isEmpty()) { return null; }
		return list.remove(0);
	}
	
	/** 将一个或多个元素添加到数组的开头，并返回该数组的新长度。数组中的其他元素从其原始位置 i 移到 i+1。<br>
	 * @param args 一个或多个要插入到数组开头的元素
	 * @return 一个整数，表示该数组的新长度 */
	public int unshift(byte[] ...args)
	{
		for(int i = args.length-1; i >= 0; i--)
		{
			list.add(0, args[i]);
		}
		return list.size();
	}
	
	/** 打印每一个元素 */
	public void printBytes()
	{
		byte[] bs = getBytes();
		for(int i = 0; i < bs.length; i++)
		{
			System.out.print(bs[i] + " ");
		}
	}
	
	/** 试图返回对应的字符串 */
	public String toString()
	{
		return new String(getBytes());
	}
	
	/** 截取一部分byte[]
	 * @param src 源byte[]
	 * @param index 起始序号
	 * @param len 截取长度 */
	public static byte[] subBytes(byte[] src, int index, int len)
	{
		int maxLen = len > src.length ? src.length : len;
		byte[] ret = null;
		try
		{
			ret = new byte[maxLen];
		}
		catch(NegativeArraySizeException e)
		{
			System.out.println("BytesArray::subBytes异常: maxLen=" + maxLen + " src.length=" + src.length + " len=" + len + " msg:" + e.getMessage());
			e.printStackTrace();
			return null;
		}
		int minLen = len <= src.length-index ? len : src.length-index;
		int c = 0;
		for(int i = index; i < index+minLen; i++)
		{
			ret[c++] = src[i];
		}
		return ret;
	}
	
}
