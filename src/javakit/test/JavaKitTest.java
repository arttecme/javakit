package javakit.test;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javakit.codec.HtmlEscape;
import javakit.ds.Pair;
import javakit.file.FileLoader;
import javakit.file.FileUtil;
import javakit.math.AccurDouble;
import javakit.string.StrParser;

public class JavaKitTest 
{

	public static void main(String[] args) 
	{
		
		System.out.println(sz2dat("1990-10-01 17:18:19"));
		System.out.println(sz2dat("1990-10-01"));
		System.out.println(sz2dat("2015-10-01 "));
		System.out.println(sz2dat("2015-3-8 "));
		System.out.println(sz2dat("2015-3-8 19:2:1"));
		//testHtmlEscape();
		//testAccurDouble();
		//testStrParser();
		//testFileUtil();
		//testFileLoader();
	}
	
	private static Date sz2dat(String str)
	{
		if(-1 == str.indexOf(":"))
		{
			str = str.trim() + " 00:00:00";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dat = null;
		try 
		{
			dat = sdf.parse(str);
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		return dat;
	}
	
	public static void testHtmlEscape()
	{
		String original = "<a href='#'>链接this is a link!</a><br>hh1<br/>hh2\nGN";
		System.out.println("HtmlEscape.escape=>" + HtmlEscape.escape(original));
		System.out.println("HtmlEscape.escapeSpecial=>" + HtmlEscape.escapeSpecial(original));
		System.out.println("HtmlEscape.escapeBr=>" + HtmlEscape.escapeBr(original));
		System.out.println("HtmlEscape.escapeTags=>" + HtmlEscape.escapeTags(original));
		System.out.println("HtmlEscape.escapeTextArea=>" + HtmlEscape.escapeTextArea(original));
	}
	
	public static void testAccurDouble()
	{
		double v1 = 2.0;
		double v2 = 1.1;
		System.out.println("v1 + v2 = " + (v1+v2));
		System.out.println("AccurDouble.add = " + AccurDouble.add(v1, v2));
		System.out.println("v1 - v2 = " + (v1-v2));
		System.out.println("AccurDouble.sub = " + AccurDouble.sub(v1, v2));
		System.out.println("v1 * v2 = " + (v1*v2));
		System.out.println("AccurDouble.multi = " + AccurDouble.multi(v1, v2));
		System.out.println("v1 / v2 = " + (v1/v2));
		System.out.println("AccurDouble.div = " + AccurDouble.div(v1, v2));
		System.out.println("AccurDouble.div(v1,v2,3) = " + AccurDouble.div(v1, v2, 3));
		System.out.println("AccurDouble.round(1.3658, 3) = " + AccurDouble.round(1.3658, 3));
	}
	
	public static void testStrParser()
	{
		double[] arr = StrParser.str2arr("2,3.14,-0.0015699945", ",", (double)0);
		System.out.println("arr:" + arr[0] + " - " + arr[1] + " - " + arr[2]);
		List<Pair<Integer, Integer>> list = StrParser.toIntIntPair("9100341:2#9100342:13#9100343:24", "#", ":");
		System.out.println("list:" + list);
	}
	
	public static void testFileUtil()
	{
		String folderPath = FileUtil.createDirectory("test/newFolder/sub");
		System.out.println("folderPath=" + folderPath);//folderPath=test\newFolder\sub
		
		try
		{
			FileUtil.copyFile(new File("test/source.txt"), new File("test/target.txt"));
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		FileUtil.CopyFolder(new File("test/newFolder"), new File("test/newFolderCopy"));
	}
	
	public static void testFileLoader()
	{
		FileLoader fl = new FileLoader(FileLoader.MANY_SMALL_MODE);
		
		byte[] bytes_ansi = fl.loadBytes("test/word-ansi.txt");
		System.out.println("bytes_ansi.length=" + bytes_ansi.length);
		
		byte[] bytes_utf8 = fl.loadBytes("test/word-utf-8.txt");
		System.out.println("bytes_utf8.length=" + bytes_utf8.length);
		
		String str_ansi = fl.loadText("test/word-ansi.txt", "GB2312");
		System.out.println("str_ansi=" + str_ansi);
		
		String str_utf8 = fl.loadText("test/word-utf-8.txt", "UTF-8");
		System.out.println("str_utf8=" + str_utf8);
	}

}
