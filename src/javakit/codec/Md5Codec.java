package javakit.codec;

public class Md5Codec extends Codec
{
	private static Md5Codec _me;

	public static Md5Codec me()
	{
		if (_me == null)
		{
			_me = new Md5Codec();
		}
		return _me;
	}

	/** 以MD5方式加密(32位) */
	public String encode32(String szIn)
	{
		return encrypt(szIn, "MD5", charsetName);
	}

	/** 以MD5方式加密(16位) */
	public String encode16(String szIn)
	{
		return encrypt(szIn, "MD5", charsetName).substring(8, 24);
	}

	

}
