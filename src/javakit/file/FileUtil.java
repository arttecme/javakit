package javakit.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil
{
	/**
	 * 创建文件夹
	 * 
	 * @param path
	 * @return path
	 */
	public static String createDirectory(String path)
	{
		File f = new File(path);
		if (!f.exists())
		{
			f.mkdirs();
		}
		return f.getPath();
	}
	
	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean hasFile(String path)
	{
		File f = new File(path);
		return f.exists();
	}
	
	/**
	 * 获取文件类型
	 * 
	 * @param fileName
	 * @return find the file type
	 */
	public static String getFileType(String fileName)
	{
		int pos = fileName.lastIndexOf(".");
		if (pos < 0)
		{
			return "";
		}
		else
		{
			return fileName.substring(pos + 1);
		}
	}
	
	/**
	 * 文件拷贝
	 * 
	 * @param in
	 * @param out
	 * @throws Exception
	 */
	public static void copyFile(File in, File out) throws Exception
	{
		FileInputStream fis = new FileInputStream(in);
		FileOutputStream fos = new FileOutputStream(out);
		byte[] buf = new byte[1024];
		int i = 0;
		while ((i = fis.read(buf)) != -1)
		{
			fos.write(buf, 0, i);
		}
		fis.close();
		fos.close();
	}
	
	/** 将二进制数据方式写入到指定的文件，自动创建目录，默认为重写方式 */
	public static void bytes2File(String fileName, byte[] data)
			throws IOException
	{
		bytes2File(fileName, data, 0, data.length, false);
	}

	/** 将二进制数据方式写入到指定的文件，自动创建目录，默认为重写方式 */
	public static void bytes2File(String fileName, byte[] data, int offset,
			int len) throws IOException
	{
		bytes2File(fileName, data, offset, len, false);
	}

	/** 将二进制数据方式写入到指定的文件，自动创建目录，参数append为是否追加方式 */
	public static void bytes2File(String fileName, byte[] data,
			boolean append) throws IOException
	{
		bytes2File(fileName, data, 0, data.length, append);
	}

	/** 将二进制数据方式写入到指定的文件，自动创建目录，参数append为是否追加方式 */
	public static void bytes2File(String fileName, byte[] data, int offset,
			int len, boolean append) throws IOException
	{
		if (offset < 0 || offset >= data.length)
			throw new IllegalArgumentException(
					"FileKit byteArray2File, invalid offset:" + offset);
		if (len <= 0 || offset + len > data.length)
			throw new IllegalArgumentException(
					"FileKit byteArray2File, invalid length:" + len);
		File file = new File(fileName);
		String parent = file.getParent();
		if (parent != null)
		{
			File tree = new File(parent);
			if (!tree.exists())
				tree.mkdirs();
		}
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		try
		{
			fos = new FileOutputStream(file, append);
			bos = new BufferedOutputStream(fos);
			bos.write(data, offset, len);
			bos.flush();
		}
		finally
		{
			try
			{
				if (fos != null)
					fos.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/** 将一个字符串写入到指定的文本文件中，自动创建目录，默认为重写方式 */
	public static void string2File(String fileName, String str)
			throws IOException
	{
		string2File(fileName, str, false);
	}

	/** 将一个字符串写入到指定的文本文件中，自动创建目录，参数append为是否追加方式 */
	public static void string2File(String fileName, String str, boolean append)
			throws IOException
	{
		File file = new File(fileName);
		String parent = file.getParent();
		if (parent != null)
		{
			File tree = new File(parent);
			if (!tree.exists())
				tree.mkdirs();
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		try
		{
			fw = new FileWriter(fileName, append);
			bw = new BufferedWriter(fw);
			bw.write(str);
			bw.flush();
		}
		finally
		{
			try
			{
				if (fw != null)
					fw.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 得到文件Parent路径
	 * (file如果是文件，则得到目录，file如果是目录，则得到父目录)
	 * 
	 * @param file
	 * @return file real path
	 */
	public static String getFileParentPath(File file)
	{
		return getFileParentPath(file.getAbsolutePath());
	}

	/**
	 * 得到文件Parent路径
	 * (fileName 如果是  D:\\proj\\javakit\\source.txt 得到结果 D:\proj\javakit)
	 * (fileName 如果是  D:\\proj\\javakit 得到结果 D:\proj)
	 * 
	 * @param fileName
	 * @return file real path
	 */
	public static String getFileParentPath(String fileName)
	{
		int pos = fileName.lastIndexOf("\\");
		int pos2 = fileName.lastIndexOf("/");
		if (pos == -1 && pos2 == -1)
		{
			return "";
		}
		else
		{
			if (pos2 > pos)
			{
				return fileName.substring(0, pos2);
			}
			else
			{
				return fileName.substring(0, pos);
			}
		}
	}
	

	/**
	 * 持贝指定的目录下所有文件及子目录到目标文件夹
	 * 
	 * @param file
	 * @param tofile
	 */
	public static void CopyFolder(File file, File tofile)
	{
		createDirectory(tofile.getAbsolutePath());
		// 获取源目录下一级所有目录文件
		File[] files = file.listFiles();
		// 逐个判断，创建目录，执行递归调用
		for (int i = 0; i < files.length; i++)
		{
			if (files[i].isDirectory())
			{
				File copyPath = new File(tofile.getAbsolutePath() + "\\"
						+ files[i].getName());
				copyPath.mkdir();
				CopyFolder(files[i], copyPath);
			}
			else
			{ // 如果file为文件，读取字节流写入目标文件;
				try
				{
					FileInputStream fiStream = new FileInputStream(files[i]);
					BufferedInputStream biStream = new BufferedInputStream(
							fiStream);
					File copyFile = new File(tofile.getAbsolutePath() + "\\"
							+ files[i].getName());
					copyFile.createNewFile();
					FileOutputStream foStream = new FileOutputStream(copyFile);
					BufferedOutputStream boStream = new BufferedOutputStream(
							foStream);
					int j;
					while ((j = biStream.read()) != -1)
					{
						boStream.write(j);
					}
					/* 关闭流 */
					biStream.close();
					boStream.close();
					fiStream.close();
					foStream.close();
				}
				catch (FileNotFoundException ex)
				{
					ex.printStackTrace();
				}
				catch (IOException ex)
				{
					ex.printStackTrace();
				}
			}
		}
	}
}
