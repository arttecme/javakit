package javakit.time;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class Timework implements Runnable
{
	public ScheduledExecutorService service;
	protected ScheduledFuture<?> future;
	
	/** 附带参数 */
	public Object[] params;
	
	/** 是否处于结束任务状态 */
	public boolean isOver()
	{
		return future == null;
	}
	
	public Timework()
	{
		service = Executors.newScheduledThreadPool(1);
	}
	
	public Timework(ScheduledExecutorService service)
	{
		this.service = service;
	}
	
	/** setInterval */
	public void setInterval(long initialDelay, long period)
	{
		if(this.service==null) { return; }
		future = service.scheduleAtFixedRate(this, initialDelay, period, TimeUnit.MILLISECONDS);
	}
	
	/** clearInterval */
	public void clearInterval()
	{
		if(future != null)
		{
			future.cancel(false);
			future = null;
		}
	}
	
	/** setTimeout */
	public void setTimeout(long delay)
	{
		if(this.service==null) { return; }
		future = service.schedule(this, delay, TimeUnit.MILLISECONDS);
	}
	
	/** clearTimeout */
	public void clearTimeout()
	{
		if(future != null)
		{
			future.cancel(false);
			future = null;
		}
	}
}

