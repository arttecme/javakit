package javakit.utils;

public class ThreadUtil
{
	/** 让当前线程休眠指定的毫秒数，可以省去try,catch的代码 */
	public static void delay(long millis)
	{
		try { Thread.sleep(millis); }
		catch (InterruptedException e) { e.printStackTrace(); }
	}
}
