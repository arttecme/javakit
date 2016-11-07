package javakit.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.locks.LockSupport;

public class EchoClient1
{

	public static void main(String[] args)
	{
		final int sleep_time = 1000*1000*1000;  
		
		Socket client = null;
		PrintWriter writer = null;
		BufferedReader reader = null;
		try
		{
			client = new Socket();
			client.connect(new InetSocketAddress("localhost", 80));
			writer = new PrintWriter(client.getOutputStream(), true);
			//writer.println("Hello!");
			
			writer.print("I");
			LockSupport.parkNanos(sleep_time);
			writer.print("a");
			LockSupport.parkNanos(sleep_time);
			writer.print("m");
			LockSupport.parkNanos(sleep_time);
			writer.print("5");
			LockSupport.parkNanos(sleep_time);
			writer.println();
			
			writer.flush();
			reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
			
			System.out.println("from server:");
			String ret = null;
			while((ret = reader.readLine())!=null)
			{
				System.out.println(ret);
			}
			
			reader.close();
			writer.close();
			client.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
