package javakit.codec;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public abstract class Codec
{
	protected String charsetName = "UTF-8";
	public String charsetName() { return this.charsetName; }
	public void charsetName(String value) { this.charsetName = value; }
	
	protected static String bytes2hex(byte[] bytes) 
	{
		String result = "";
		String tmp = null;
		for (int i = 0; i < bytes.length; i++) 
		{
			tmp = (Integer.toHexString(bytes[i] & 0xFF));
			if (tmp.length() == 1) 
			{
				result += "0";
			}
			result += tmp;
		}
		return result;
	}
	
	protected static String encrypt(String strSrc, String encName, String charsetName)
	{
		MessageDigest md = null;
		String strDes = null;
		try
		{
			byte[] bt = strSrc.getBytes(charsetName);
			if (encName == null || encName.trim().equals(""))
			{
				encName = "MD5";
			}
			md = MessageDigest.getInstance(encName.toUpperCase());
			md.update(bt);
			strDes = bytes2hex(md.digest());// to HexString
		} 
		catch (NoSuchAlgorithmException e)
		{
			e.printStackTrace();
		} 
		catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return strDes;
	}
}
