package javakit.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ClassUtil 
{
	/** 实例化 */
	public static <T> T newInstance(Class<T> clazz, Object... args)
	{
		try
		{                             
		    Class<?>[] argsClass = new Class[args.length];                                   
		    for (int i = 0, j = args.length; i < j; i++)
		    {                              
		        argsClass[i] = args[i].getClass();                                        
		    }
			Constructor<T> con = clazz.getConstructor(argsClass);
			try
			{
				return con.newInstance(args);
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
		}
		catch (NoSuchMethodException e)
		{
			e.printStackTrace();
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/** 找到全部的自定义字段，包含继承的 */
	public static Field[] getUserDefinedField(Class<?> clazz)
	{
		UserDefinedFieldFinder udff = new UserDefinedFieldFinder(clazz);
		return udff.toFields();
	}
	
	private static class UserDefinedFieldFinder
	{
		private List<Field> list = new ArrayList<Field>();
		
		public UserDefinedFieldFinder(Class<?> clazz)
		{
			find(clazz);
		}
		
		private void find(Class<?> clazz)
		{
			if(Object.class.equals(clazz))
			{
				return;
			}
			Field[] fields = clazz.getDeclaredFields();
			for(Field f : fields)
			{
				list.add(f);
			}
			find(clazz.getSuperclass());
		}
		
		public Field[] toFields()
		{
			Field[] fields = new Field[list.size()];
			for(int i = 0; i < fields.length; i++)
			{
				fields[i] = list.get(i);
			}
			return fields;
		}
		
		@SuppressWarnings("unused")
		public List<Field> toFieldList()
		{
			return list;
		}
	}
}
