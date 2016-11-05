package javakit.flash.events;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
	
	public void addEventListener(String type, Class<?> funcAtClass, String funcName, Class<? extends Event> funcParamClazz)
	{
		addEventListener(type, funcAtClass, funcName, funcParamClazz, null, 0, false);
	}
	
	public void addEventListener(String type, Class<?> funcAtClass, String funcName, Class<? extends Event> funcParamClazz,
			Object owner)
	{
		addEventListener(type, funcAtClass, funcName, funcParamClazz, owner, 0, false);
	}
	
	@Override
	public void addEventListener(String type, Class<?> funcAtClass, String funcName, Class<? extends Event> funcParamClazz,
			Object owner,int priority, boolean useWeakReference)
	{
		ELP pack = new ELP(type, funcAtClass, funcName, funcParamClazz, owner, priority, useWeakReference);
		for(int i = strongPacks.size()-1; i >= 0; i--)
		{
			ELP inp = strongPacks.get(i);
			if(inp.similar(type, funcAtClass, funcName, owner))
			{ 
				strongPacks.remove(i);
			}
		}
		for(int i = weakPacks.size()-1; i >= 0; i--)
		{
			WeakReference<ELP> ref = weakPacks.get(i);
			ELP inp = ref.get();
			if(inp == null || inp.similar(type, funcAtClass, funcName, owner))
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
	public void removeEventListener(String type, Class<?> funcAtClass, String funcName, Object owner)
	{
		for(int i = strongPacks.size()-1; i >= 0; i--)
		{
			ELP inp = strongPacks.get(i);
			if(inp.similar(type, funcAtClass, funcName, owner))
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
				if(inp.similar(type, funcAtClass, funcName, owner))
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
		public Class<?> funcAtClass;
		public String funcName;
		public Class<? extends Event> funcParamClazz;
		public Object owner;
		public int priority;
		public boolean useWeakReference;
		
		private Method func;
		
		public ELP(String type, Class<?> funcAtClass, String funcName, Class<? extends Event> funcParamClazz,
				Object owner, int priority, boolean useWeakReference)
		{
			this.type = type;
			this.funcAtClass = funcAtClass;
			this.funcName = funcName;
			this.funcParamClazz = funcParamClazz;
			this.owner = owner;
			this.priority = priority;
			this.useWeakReference = useWeakReference;
			
			try
			{
				func = funcAtClass.getMethod(funcName, funcParamClazz);
			}
			catch (NoSuchMethodException e)
			{
				e.printStackTrace();
			}
			catch (SecurityException e)
			{
				e.printStackTrace();
			}
		}
		
		public void handle(Event event)
		{
			if(func != null)
			{
				try
				{
					func.invoke(this.owner, event);
				}
				catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e)
				{
					e.printStackTrace();
				}
			}
		}

		@Override
		public int compareTo(ELP o)
		{
			if(this.priority > o.priority) { return -1; }
			else if(this.priority < o.priority) { return 1; }
			return 0;
		}
		
		/** 是否相似 */
		public boolean similar(String type, Class<?> funcAtClass, String funcName, Object owner)
		{
			if(this.type == type && this.funcAtClass == funcAtClass
				&& this.funcName == funcName && this.owner == owner)
			{
				return true;
			}
			return false;
		}
		
	}

}
