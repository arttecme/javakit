package javakit.framework;

public class ErrorCodeSupport implements ErrorCode
{
	/** 成功。 */
	public static final int EC_SUCC = 0;
	/** 失败！ */
	public static final int EC_FAIL = -1;
	
	protected int errorcode = 0;
	protected String errormsg = "";
	
	public void setErrorcode(int errorcode)
	{
		this.errorcode = errorcode;
	}
	@Override
	public int getErrorcode() { return this.errorcode; }

	
	public void setErrormsg(String errormsg)
	{
		this.errormsg = errormsg;
	}
	@Override
	public String getErrormsg() { return this.errormsg; }
	
	public boolean isSucc()
	{
		return EC_SUCC == errorcode;
	}
	
	public void succ()
	{
		succ("成功");
	}
	
	public void succ(String errormsg)
	{
		this.errorcode = EC_SUCC;
		this.errormsg = errormsg;
	}
	
	public void fail()
	{
		fail("失败");
	}
	
	public void fail(String errormsg)
	{
		this.errorcode = EC_FAIL;
		this.errormsg = errormsg;
	}
}
