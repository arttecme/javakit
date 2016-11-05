package javakit.time;

public class IntervalCounter
{
	private int interval = 1;
	public void setInterval(int value)
	{
		this.interval = value;
		counter = 0;
	}
	public int getInterval() { return this.interval;  }
	
	private int counter = 0;
	
	/** interval:间隔  interval<=0 则 永不触发<br>
	 * interval=1 [每1次触发1次]; interval=2 [每2次触发1次];<br>
	 * triggerNow:是否立即触发第一次 */
	public IntervalCounter(int interval)
	{
		this.interval = interval;
	}
	
	public IntervalCounter()
	{
		this(0);
	}
	
	public boolean trigger()
	{
		if(interval <= 0) return false;
		counter++;
		if(counter >= interval)
		{
			counter = 0;
			return true;
		}
		return false;
	}
}
