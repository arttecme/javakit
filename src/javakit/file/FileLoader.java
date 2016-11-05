package javakit.file;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class FileLoader
{
	/** 适合大文件模式 */
	public static final byte BIG_FILE_MODE = 0;
	/** 适合很多小文件模式 */
	public static final byte MANY_SMALL_MODE = 1;

	/** 加载策略：FileLoader.BIG_FILE_MODE or FileLoader.MANY_SMALL_MODE */
	public byte strategy = BIG_FILE_MODE;

	public FileLoader()
	{
	}

	public FileLoader(byte strategy)
	{
		this.strategy = strategy;
	}

	public byte[] loadBytes(String filePath)
	{
		File file = new File(filePath);
		return loadBytes(file);
	}

	public byte[] loadBytes(File file)
	{
		try
		{
			return loadBytes(new FileInputStream(file));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public byte[] loadBytes(InputStream ips)
	{
		switch (strategy)
		{
		case BIG_FILE_MODE:
			return __load_bytes_with_buffered_ips__(ips);
		case MANY_SMALL_MODE:
			return __load_bytes_with_arraycopy__(ips);

		default:
			return __load_bytes_with_buffered_ips__(ips);
		}
	}

	public String loadText(String filePath, String charsetName)
	{
		File file = new File(filePath);
		return loadText(file, charsetName);
	}

	public String loadText(File file, String charsetName)
	{
		try
		{
			return loadText(new FileInputStream(file), charsetName);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public String loadText(InputStream ips, String charsetName)
	{
		if (strategy == MANY_SMALL_MODE)
		{
			byte[] bytes = __load_bytes_with_arraycopy__(ips);
			try
			{
				return new String(bytes, charsetName);
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			return null;
		}
		else
		{
			return __load_string_with_buffered_ips__(ips, charsetName);
		}
	}

	private byte[] __load_bytes_with_buffered_ips__(InputStream ips)// String
																	// fileName
	{
		byte[] bytes = new byte[0];
		try
		{
			BufferedInputStream bis = new BufferedInputStream(ips);
			int leng = bis.available();
			bytes = new byte[leng];
			bis.read(bytes);
			ips.close();
			bis.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return bytes;
	}

	private String __load_string_with_buffered_ips__(InputStream ips, String encoding)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(ips, encoding));
			sb = new StringBuffer();
			while (reader.ready())
			{
				String line = reader.readLine();
				sb.append(line);
				sb.append("\r\n");
			}
			reader.close();
			ips.close();
			return sb.toString();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	// 依赖 System.arraycopy 的加载方式
	private byte[] __load_bytes_with_arraycopy__(InputStream ips)
	{
		List<byte[]> bytes = new ArrayList<byte[]>();
		List<Integer> availSizes = new ArrayList<Integer>();
		int total = 0;
		try
		{
			final int BUFF_SIZE = 256;
			byte[] buff = new byte[BUFF_SIZE];
			int n = 0;
			while ((n = ips.read(buff)) > 0)
			{
				total += n;
				availSizes.add(n);
				bytes.add(buff);
				buff = new byte[BUFF_SIZE];
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{
			ips.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		byte[] result = new byte[total];
		int pos = 0;
		for (int i = 0; i < bytes.size(); i++)
		{
			byte[] b = bytes.get(i);
			int availsize = availSizes.get(i);
			System.arraycopy(b, 0, result, pos, availsize);
			pos += availsize;
		}
		return result;
	}

}
