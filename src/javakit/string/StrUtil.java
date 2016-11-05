package javakit.string;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StrUtil
{
	/** 判断两个值是否相同 */
	public static boolean same(String a, String b)
	{
		if(a==null && b==null) return true;
		if(a != null && b != null)
		{
			if(a.equals(b)) { return true; }
		}
		return false;
	}
	
	/** 判断字符串是否空（无意义） */
	public static boolean empty(String str)
	{
		if(str == null) { return true; }
		if("".equals(str.trim())) { return true; }
		return false;
	}
	
	/** 格式化成常见格式的日期字符串 yyyy-MM-dd */
	public static String toDateStr(Date date) 
	{
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
		return sdformat.format(date);
	}
	
	/** 格式化成常见格式的时间字符串 HH:mm:ss */
	public static String toTimeStr(Date date) 
	{
		SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm:ss");
		return sdformat.format(date);
	}
	/** 格式化成常见格式的时间字符串 HH:mm:ss */
	public static String toTimeStr(Time time)
	{
		Date date = new Date(time.getTime());
		return toTimeStr(date);
	}

	/** 格式化成常见格式的时间字符串 yyyy-MM-dd HH:mm:ss */
	public static String toDateTimeStr(Date date)
	{
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdformat.format(date);
	}
	/** 格式化成常见格式的时间字符串 yyyy-MM-dd HH:mm:ss */
	public static String toDateTimeStr(Timestamp timestamp)
	{
		Date date = new Date(timestamp.getTime());
		return toDateTimeStr(date);
	}
	
	/** 格式化成今天的日期字符串 yyyy-MM-dd */
	public static String toTodayStr()
	{
		return toDateStr(new Date());
	}
	/** 格式化成现在的时间字符串 HH:mm:ss */
	public static String toNowStr()
	{
		return toTimeStr(new Date());
	}
	/** 格式化成此刻的日期时间字符串 yyyy-MM-dd HH:mm:ss */
	public static String toMomentStr()
	{
		return toDateTimeStr(new Date());
	}
}
