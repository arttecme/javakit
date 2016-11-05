package javakit.time;

import java.util.Date;

public class TimeCostCounter
{
	private long absTime = 0;
	
	public TimeCostCounter()
	{
		reset();
	}
	
	/** 重置计时 */
	public void reset()
	{
		absTime = new Date().getTime();
	}
	
	/** 输出时间消耗 */
	public void echoCost()
	{
		echoCost("");
	}
	
	/** 输出时间消耗 */
	public void echoCost(String desc)
	{
		System.out.println(desc + " cost " + getElapsed() + " ms");
	}
	
	/** 获取消耗的时间(ms) */
	public long getElapsed()
	{
		return new Date().getTime() - absTime;
	}
	
}
