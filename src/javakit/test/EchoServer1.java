package javakit.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EchoServer1
{
	
	public static void main(String[] args)
	{
		// try
		// {
		// testFileRead();
		// } catch (IOException e)
		// {
		// e.printStackTrace();
		// }
		ExecutorService tp = Executors.newCachedThreadPool();

		ServerSocket echoServer = null;
		Socket clientSocket = null;
		try
		{
			echoServer = new ServerSocket(8000);
			while (true)
			{
				try
				{
					System.out.println("echoServer.开始accept()");
					clientSocket = echoServer.accept();
					System.out.println("echoServer.accept()到了");
					System.out.println(clientSocket.getRemoteSocketAddress() + " connect!");
					tp.execute(new HandleMsg(clientSocket));
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}

			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private static class HandleMsg implements Runnable
	{
		Socket clientSocket;
		
		public HandleMsg(Socket clientSocket)
		{
			this.clientSocket = clientSocket;
		}
		
		@Override
		public void run()
		{
			InputStreamReader isr = null;
			BufferedReader is = null;
			PrintWriter os = null;
			try
			{
				isr = new InputStreamReader(clientSocket.getInputStream());
				is = new BufferedReader(isr);
				os = new PrintWriter(clientSocket.getOutputStream(), true);
				String inputLine = null;
				long b = System.currentTimeMillis();
				while((inputLine = is.readLine()) != null)
				{
					os.println(inputLine);
				}
				long e = System.currentTimeMillis();
				System.out.println("spend:"+(e-b)+"ms");
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(os != null) { os.close(); }
				try
				{
					if(is != null) { is.close(); }
					if(isr != null) { isr.close(); }
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings("unused")
	private static void testFileRead() throws IOException
	{
		FileInputStream fin = new FileInputStream(new File("test/word-utf-8.txt"));
		FileChannel fc = fin.getChannel();
		ByteBuffer buf = ByteBuffer.allocate(1024);
		int size = fc.read(buf);
		fc.close();
		fin.close();
		buf.flip();
		System.out.println("size=" + size);
		byte[] bytes = new byte[size];
		buf.get(bytes);
		System.out.println(new String(bytes, "UTF-8"));
	}
}
