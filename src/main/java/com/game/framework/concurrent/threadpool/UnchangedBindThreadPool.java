package com.game.framework.concurrent.threadpool;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * @Description 绑定关系不会改变的线程池. 池中线程与一个ID创建不会改变一对多的关系, 换句话说一个ID只让一个线程给他干活,
 *              这样可以避免数据的同步.
 */
public class UnchangedBindThreadPool implements Executor {
	
	/** 线程池 	*/
	private Thread[] threadPool = null;
	/** 解绑 		*/
	private UnchangedBindThreadPoolRunnable[] unchangedBindPool = null;
	/** 线程数 	*/
	private int count1 = 0;
	/** 线程数 	*/
	private int count2 = 0;
	
	public UnchangedBindThreadPool( int count1, int count2, String name) {
		this( count1, count2, name, null );
	}
	
	/**
	 * 创建线程池, 初始化输入数量的线程.
	 * <p>Title: </p>
	 * <p>Description: </p>
	 * @param count1 		绑定ID小于0的线程个数
	 * @param count2		绑定ID大于等于0的线程个数
	 * @param factory
	 */
	public UnchangedBindThreadPool( int count1, int count2, String name, UnchangedBindCreateThreadCallBack callBack  ) {
		
		threadPool = new Thread[count1 + count2];
		unchangedBindPool = new UnchangedBindThreadPoolRunnable[count1 + count2];
		
		for( int i = 0; i < threadPool.length; i++ ) {
			unchangedBindPool[i] = createUnchangedBindRunnable();
			threadPool[i] = new Thread(unchangedBindPool[i]);
			unchangedBindPool[i].callBack = callBack;
			unchangedBindPool[i].setPoolindex(i);
			
			if( i < count1 ) {
				threadPool[i].setName("UB-" + name + "LEFT-" + (i + 1) + "-" + threadPool[i].getId());
			} else {
				threadPool[i].setName("UB-" + name + "RIGHT-" + (i + 1) + "-" + threadPool[i].getId());
			}
			
			threadPool[i].start();
		}
		
		this.count1 = count1;
		this.count2 = count2;
		
	}
	
	protected UnchangedBindThreadPoolRunnable createUnchangedBindRunnable() {
		return new UnchangedBindThreadPoolRunnable();
	} 
	
	@Override
	public void execute( Runnable command ) {
		UnchangedBindRunnable runnable = (UnchangedBindRunnable) command;
		
		int bindId = runnable.getBindId();
		
		int bindIndex = getThreadIndex( bindId );
		
		unchangedBindPool[bindIndex].put(runnable);
	}
	
	public int getThreadIndex( int bindId ) {
		int bindIndex = 0;
		
		if ( bindId == 0 ) { 
			bindIndex = 0;
		} else if( bindId > 0 ) {
			bindIndex = count1 + (int)(bindId % count2);
		} else  {
			bindIndex = Math.abs((int)(bindId % count1));
		}
		
		return bindIndex;
	}
	
	public int getCount1() {
		return count1;
	}
	
	public int getCount2() {
		return count2;
	}
}

class UnchangedBindThreadPoolRunnable implements Runnable {
	
	/** 运行时环境 */
	private volatile boolean running = true;
	/** 工作队列 */
	private LinkedBlockingQueue<UnchangedBindRunnable> queue = new LinkedBlockingQueue<UnchangedBindRunnable>();
	/** 在池子中的索引号 */
	private int poolindex = 0;
	/** */
	UnchangedBindCreateThreadCallBack callBack = null;
	
	/**
	 * 
	 * @Description 剩余个数
	 * @author chen.su
	 * @date 2015-6-3 上午11:37:40 
	 * @return int
	 */
	public int size() {
		return queue.size();
	}
	
	/**
	 * 
	 * @Description 放入执行队列
	 * @author chen.su
	 * @date 2015-6-2 下午04:48:40 
	 * @param runnable void
	 */
	public void put( UnchangedBindRunnable runnable ) {
		if( runnable == null ) {
			return;
		}
		queue.offer(runnable);
	}
	
	/**
	 * 
	 * @Description 设置运行状态
	 * @author chen.su
	 * @date 2015-6-2 下午04:40:46 
	 * @param running void
	 */
	public void stop() {
		running = false;
	}
	
	public int getPoolindex() {
		return poolindex;
	}
	
	public void setPoolindex(int poolindex) {
		this.poolindex = poolindex;
	}
	
	@Override
	public void run() {
		
		UnchangedBindRunnable runnable = null;
		
		Thread currentThread = Thread.currentThread();
		
		if( callBack != null ) {
			callBack.leftThreadNotify(poolindex, currentThread);
			callBack.rightThreadNotify(poolindex, currentThread);
		}
		
		while( running ) {
			
			//	如果没有就阻塞避免空转
			try {
				if( (runnable = queue.take()) == null ) {
					continue;
				}
				
				runnable.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
