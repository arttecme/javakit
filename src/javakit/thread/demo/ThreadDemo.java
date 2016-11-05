package javakit.thread.demo;

public class ThreadDemo 
{
	private static StringBuffer logSb = new StringBuffer(); 
	private static void LOG(String msg)
	{
		logSb.append("LOG   " + msg);
		logSb.append("\n");
	}
	private static void PRINT()
	{
		System.out.println(logSb.toString());
	}
	private static void SLEEP(long ms)
	{
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		System.out.println("ThreadDemo");
		RecursionSyncTest();
		//NotifyAllTest();
		//NotifyTest();
		//DeadlockTest();
		//DirtyDataTest();
		//JoinThreadTest();
	}
	
	private static void RecursionSyncTest()
	{
		Thread a = new Thread(new RecursionSync());
		a.start();
	}
	
	private static class RecursionSync implements Runnable 
	{
		private synchronized void decrease(int i)
		{
			System.out.println("decrease "+i);
			if(i > 0)
			{
				decrease(--i);
			}
			else
			{
				System.out.println("decrease done " + i);
			}
		}
		
		public void run() 
		{
			decrease(3);
		}
	}
	
	//=========================================================================
	@SuppressWarnings("unused")
	private static void NotifyAllTest()
	{
		Calculator calculator = new Calculator();

        //启动三个线程，分别获取计算结果
        new ReaderResult(calculator).start();
        new ReaderResult(calculator).start();
        new ReaderResult(calculator).start();
        SLEEP(100);
        //启动计算线程
        calculator.start(); 
	}
	
	private static class Calculator extends Thread {
		int total;

		public void run() {
			System.out.println("Calculator:run()");
			synchronized (this) {
				for (int i = 0; i < 101; i++) {
					total += i;
				}
				System.out.println("Calculator 计算完成");
				// 通知所有在此对象上等待的线程
				notifyAll();
				//notify();
				System.out.println("Calculator 通知完成");
//				try {
//					this.wait();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				System.out.println("Calculator 等待结束");
			}
			System.out.println("Calculator 离开同步区");
		}
	}
	
	private static class ReaderResult extends Thread {
		Calculator c;

		public ReaderResult(Calculator c) {
			this.c = c;
		}

		public void run() {
			System.out.println("ReaderResult:run()"+Thread.currentThread().getName() + " " + this.getName());
			synchronized (c) {
				try {
					System.out.println(Thread.currentThread() + "等待计算结果。。。");
					c.wait();
				} catch (Exception e) {//InterruptedException
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread() + "计算结果为：" + c.total);
				try {
					c.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread() + "准备退出同步区");
			}
		}
	}
	
	//==========================================================
	@SuppressWarnings("unused")
	private static void NotifyTest()
	{
		CountThread b = new CountThread();
        //启动计算线程
        b.start();
        //SLEEP(100);
        //线程A拥有b对象上的锁。线程为了调用wait()或notify()方法，该线程必须是那个对象锁的拥有者
        synchronized (b) {
            try {
                System.out.println("等待对象b完成计算。。。");
                //当前线程A等待
                b.wait();
                System.out.println("b等待完了");
                b.wait();
                System.out.println("b等待完了again");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("b对象计算的总和是：" + b.total);
        } 
	}
	
	private static class CountThread extends Thread {
	    int total;
	    public void run() {
	    	System.out.println("CountThread:run()");
	        synchronized (this) {
	        	System.out.println("CountThread开始计算");
	        	SLEEP(100);
	            for (int i = 0; i < 11; i++) {
	                total += i;
	            }
	            System.out.println("CountThread计算完毕");
	            //（完成计算了）唤醒在此对象监视器上等待的单个线程，在本例中主线程被唤醒
	            this.notify();
	            try {
					this.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
	            System.out.println("这个线程也不想动了");
	            //notify();
	        }
	    }
	}
	
	//=================================================
	@SuppressWarnings("unused")
	private static void DeadlockTest() throws InterruptedException
	{
		final DeadlockRisk dlr =new DeadlockRisk();
        class WriterThread extends Thread
        {
            public void run()
            {
            	dlr.write(1, 2);
            }
        }
        class ReaderThread extends Thread
        {
            public void run()
            {
            	dlr.read();
            }
        }
        for(int i = 0; i < 1; i++)
        {
	        Thread wt = new WriterThread();
	        Thread rt = new ReaderThread();
	        wt.start();
	        rt.start();
	        wt.join();
	        rt.join();
        }
        System.out.println("读完了");
	}
	
	private static class DeadlockRisk {
	    private static class Resource {
	        public int value;
	    }

	    private Resource resourceA =new Resource();
	    private Resource resourceB =new Resource();

	    public int read() {
	    	System.out.println("read()");
	        synchronized (resourceA) {
	        	System.out.println("read--resourceA--enter");
	        	SLEEP(1);
	        	System.out.println("read--resourceA--sleep");
	            synchronized (resourceB) {
	            	System.out.println("read--resourceB--enter");
	                return resourceB.value + resourceA.value;
	            }
	        }
	    }

	    public void write(int a,int b) {
	    	System.out.println("write()");
	        synchronized (resourceB) {
	        	System.out.println("write--resourceB--enter");
	        	SLEEP(1);
	        	System.out.println("write--resourceB--sleep");
	            synchronized (resourceA) {
	            	System.out.println("write--resourceA--enter");
	                resourceA.value = a;
	                resourceB.value = b;
	            }
	        }
	    }
	}
	
	//================================================================
	@SuppressWarnings("unused")
	private static void DirtyDataTest() throws InterruptedException
	{
		DirtyRunnable r = new DirtyRunnable();
		for(int i = 0; i < 3; i++)
		{
			
	        Thread ta = new Thread(r,"Thread-A");
	        Thread tb = new Thread(r,"Thread-B");
	        ta.start();
	        tb.start(); 
	        ta.join();
	        tb.join();
	        System.out.println("r.foo.x="+r.foo.x);
		}
	}
	
	private static class Foo 
	{
		public int x = 0;
		
		public synchronized void add(int i)
		{
			x += i;
		}
	}
	
	private static class DirtyRunnable implements Runnable
	{
		public Foo foo = new Foo();

		public void run() 
		{
			for (int i = 0; i < 2000000; i++) 
			{
				foo.add(1);
//				try {
//					Thread.sleep(3);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
				//System.out.println(Thread.currentThread().getName() + " :当前foo对象的x值= " + foo.x);
			}
		}
	}
	
	//================================================================
	@SuppressWarnings("unused")
	private static void JoinThreadTest() throws Exception
	{
		Thread threads[] = new Thread[10];
        for (int i = 0; i < threads.length; i++)  // 建立100个线程
            threads[i] = new JoinThread("t"+i);
        for (int i = 0; i < threads.length; i++)   // 运行刚才建立的100个线程
            threads[i].start();
        if (true)
        {
            for (int i = 0; i < threads.length; i++)   // 100个线程都执行完后继续
                threads[i].join();
        }
        
        LOG("bef n=" + JoinThread.n);
        Thread.sleep(10);
        LOG("aft n=" + JoinThread.n);
        System.out.println("n=" + JoinThread.n);
        PRINT();
	}

	public static class JoinThread extends Thread
	{
		public static int n = 0;
		
		static void inc() 
		{
			n++;
		}
		
		public JoinThread(String name)
		{
			super(name);
		}
		
		public void run()
		{
			for(int i = 0; i < 10; i++)
			{
				try
				{
					LOG(this.getName() + " i=" + i);
					inc();
					sleep(1);
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
