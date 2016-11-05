package javakit.events;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;

public class EventDispatcher implements IEventDispatcher
{
	protected IEventDispatcher target;
	
	protected ArrayList<ELP> strongPacks = new ArrayList<>();
	
	protected ArrayList<WeakReference<ELP>> weakPacks = new ArrayList<>();
	
	public EventDispatcher()
	{
		this(null);
	}
	
	/** 聚合 EventDispatcher 类的实例。 <br>
	 * EventDispatcher 类通常用作基类，这意味着大多数开发人员都无需使用此构造函数。<br>
	 * 但是，实现 IEventDispatcher 接口的高级开发人员则需要使用此构造函数。<br>
	 * 如果您无法扩展 EventDispatcher 类并且必须实现 IEventDispatcher 接口，请使用此构造函数来聚合 EventDispatcher 类的实例。
	 * @param target 调度到 EventDispatcher 对象的事件的目标对象。<br>
	 * 当 EventDispatcher 实例由实现 IEventDispatcher 的类聚合时，使用此参数；此参数是必需的，以便包含对象可以是事件的目标。<br>
	 * 请勿在类扩展了 EventDispatcher 的简单情况下使用此参数。 */
	public EventDispatcher(IEventDispatcher target)
	{
		if(target != null)
		{
			this.target = target;
		}
		else
		{
			this.target = this;
		}
	}
	
	public void addEventListener(String type, EventListener listener)
	{
		addEventListener(type, listener, 0, false);
	}
	
	@Override
	public void addEventListener(String type, EventListener listener, int priority, boolean useWeakReference)
	{
		ELP pack = new ELP(type, listener, priority, useWeakReference);
		for(int i = strongPacks.size()-1; i >= 0; i--)
		{
			ELP inp = strongPacks.get(i);
			if(inp.similar(type, listener))
			{ 
				strongPacks.remove(i);
			}
		}
		for(int i = weakPacks.size()-1; i >= 0; i--)
		{
			WeakReference<ELP> ref = weakPacks.get(i);
			ELP inp = ref.get();
			if(inp == null || inp.similar(type, listener))
			{
				ref.clear();
				weakPacks.remove(i);
			}
		}
		if(useWeakReference)
		{
			weakPacks.add(new WeakReference<EventDispatcher.ELP>(pack));
		}
		else
		{
			strongPacks.add(pack);
		}
	}

	@Override
	public boolean dispatchEvent(Event event)
	{
		ArrayList<ELP> elps = new ArrayList<>();
		for(int i = strongPacks.size()-1; i >= 0; i--)
		{
			ELP inp = strongPacks.get(i);
			if(event.type == inp.type)
			{
				elps.add(inp);
			}
		}
		for(int i = weakPacks.size()-1; i >= 0; i--)
		{
			WeakReference<ELP> ref = weakPacks.get(i);
			ELP inp = ref.get();
			if(inp == null)
			{
				ref.clear();
				weakPacks.remove(i);
			}
			else
			{
				if(event.type == inp.type)
				{
					elps.add(inp);
				}
			}
		}
		
		Collections.sort(elps);
		
		for(int i = 0; i < elps.size(); i++)
		{
			ELP pack = elps.get(i);
			pack.handle(event);
		}
		return true;
	}

	@Override
	public boolean hasEventListener(String type)
	{
		for(int i = strongPacks.size()-1; i >= 0; i--)
		{
			ELP inp = strongPacks.get(i);
			if(type == inp.type)
			{
				return true;
			}
		}
		for(int i = weakPacks.size()-1; i >= 0; i--)
		{
			WeakReference<ELP> ref = weakPacks.get(i);
			ELP inp = ref.get();
			if(inp == null)
			{
				ref.clear();
				weakPacks.remove(i);
			}
			else
			{
				if(type == inp.type)
				{
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public void removeEventListener(String type, EventListener listener)
	{
		for(int i = strongPacks.size()-1; i >= 0; i--)
		{
			ELP inp = strongPacks.get(i);
			if(inp.similar(type, listener))
			{
				strongPacks.remove(i);
			}
		}
		for(int i = weakPacks.size()-1; i >= 0; i--)
		{
			WeakReference<ELP> ref = weakPacks.get(i);
			ELP inp = ref.get();
			if(inp == null)
			{
				ref.clear();
				weakPacks.remove(i);
			}
			else
			{
				if(inp.similar(type, listener))
				{
					ref.clear();
					weakPacks.remove(i);
				}
			}
		}
	}
	
	/** Event Listener Pack */
	public static class ELP implements Comparable<ELP>
	{
		public String type;
		public EventListener listener;
		public int priority;
		public boolean useWeakReference;
		
		public ELP(String type, EventListener listener, int priority, boolean useWeakReference)
		{
			this.type = type;
			this.listener = listener;
			this.priority = priority;
			this.useWeakReference = useWeakReference;
		}
		
		public void handle(Event event)
		{
			this.listener.handleEvent(event);
		}

		@Override
		public int compareTo(ELP o)
		{
			if(this.priority > o.priority) { return -1; }
			else if(this.priority < o.priority) { return 1; }
			return 0;
		}
		
		/** 是否相似 */
		public boolean similar(String type, EventListener listener)
		{
			if(this.type == type && this.listener == listener)
			{
				return true;
			}
			return false;
		}
		
	}
}
