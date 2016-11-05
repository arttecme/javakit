package javakit.utils;

public class IdUtil 
{
	private static int autoIncrease = 0;
	
	/** 生成Long类型的uid[全局唯一性] */
	public static long createUidLong()
	{
		long ms = Math.abs(System.currentTimeMillis() << 48);
		long ns = System.nanoTime() << 16;
		long ret = ms | ns | autoIncrease;
		autoIncrease ++;
		return ret;
	}
	
	/** 生成Integer类型的tempId[全局唯一性] */
	public static int createTidInt()
	{
		return ++autoIncrease;
	}
}
