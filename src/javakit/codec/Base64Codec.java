package javakit.codec;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

public class Base64Codec extends Codec
{
	private static Base64Codec _me;
	public static Base64Codec me() 
	{
		if(_me == null) { _me = new Base64Codec(); }
		return _me;
	}
	
	/** 以base64方式解密 */
	public String encode(String szIn)
	{
		byte[] binIn = null;
		String szOut = null;
		try
		{
			binIn = szIn.getBytes(charsetName);
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		byte[] binOut = Base64.encodeBase64(binIn);
		try
		{
			szOut = new String(binOut, charsetName);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return szOut;
	}
	
	/** 以base64方式加密 */
	public String decode(String szIn)
	{
		byte[] binIn = null;
		String szOut = null;
		try
		{
			binIn = szIn.getBytes(charsetName);
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		if (binIn != null)
		{
			byte[] binOut = Base64.decodeBase64(binIn);
			try
			{
				szOut = new String(binOut, charsetName);
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
		}
		return szOut;
	}
	
}
