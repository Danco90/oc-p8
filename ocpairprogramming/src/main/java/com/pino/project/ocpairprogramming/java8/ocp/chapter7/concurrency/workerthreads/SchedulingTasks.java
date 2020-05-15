package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.workerthreads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SchedulingTasks {

	public static void main(String[] args) {
//		Runnable task1 = () -> System.out.println("Hello Zoo");
//		Callable<String> task2 = () -> "Monkey";
		ScheduledExecutorService service = null;
		try 
		{   service = Executors.newSingleThreadScheduledExecutor();
			System.out.println("Thread Scheduled Executor is now in 'Active' status, execute running tasks and accept new ones ( isShutdown()="+service.isShutdown()+"; isTerminated()="+service.isTerminated()+" ) ");
		
		/** schedule(Callable<V> callable, long delay, TimeUnit unit) 
		 * Example1 : scheduling (Creates and executes) a Callable task to happen after the given delay */
		System.out.println(Thread.currentThread().getName()+" is attempting to schedule a Callable task1 to the single thread of the Thread Scheduled Executor Pool "); 
		Future<?> result0 = service.schedule(() -> System.out.println(Thread.currentThread().getName()+" is executing a Runnable task1"),
											1, TimeUnit.SECONDS ); 
		System.out.println(Thread.currentThread().getName() 
			+ " retrieved Callable task1 result : " +result0.get()
	        + " ( isDone()=" +result0.isDone()+ ";"
	        + " isCancelled()=" +result0.isCancelled()+ " )");
		
		/** schedule(Runnable command, long delay, TimeUnit unit)
		 * Example2 : scheduling (Creates and executes) a Runnable task to happen after the given delay */
		System.out.println(Thread.currentThread().getName()+" is attempting to schedule a Runnable task2 to the single thread of the Thread Scheduled Executor Pool "); 
		Future<?> result1 = service.schedule(() -> System.out.println(Thread.currentThread().getName()+" is executing a Runnable task2 'Hello Zoo' "),
											10, TimeUnit.SECONDS ); 
		System.out.println(Thread.currentThread().getName() 
			+ " retrieved Runnable task2 result : " +result1.get()
	        + " ( isDone()=" +result1.isDone()+ ";"
	        + " isCancelled()=" +result1.isCancelled()+ " )");
		
		// checking the supply of food for zoo animals once an hour (in our case every 10 seconds)
		/** scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit) 
		 * Example3 : scheduling (Creates and executes) a Runnable task to happen after the given initial delay, and then repeatedly at some set interval, creating a new task every period value that passes,  */
		System.out.println(Thread.currentThread().getName()+" is attempting to schedule a recurring Runnable task3 at a fixed rate to the single thread of the Thread Scheduled Executor Pool "); 
		Future<?> result2 = service.scheduleAtFixedRate(() -> System.out.println(Thread.currentThread().getName()+" is executing a recurring Runnable task3 at a fixed rate"),
											2,//initial delay  
											5,//recurring period for a new execution
											TimeUnit.SECONDS ); 
		System.out.println(Thread.currentThread().getName() 
			+ " retrieved Callable task3 result : " +result2.get()
	        + " ( isDone()=" +result2.isDone()+ ";"
	        + " isCancelled()=" +result2.isCancelled()+ " )");
		
		/** scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit)
		 * Example4 : scheduling (Creates and executes) a Runnable task to happen after the given 
		 * initial delay and subsequently with the given delay between the termination of one execution 
		 * and the beginning of the next*/
		System.out.println(Thread.currentThread().getName()+" is attempting to schedule a recurring Runnable task4 at a fixed delay to the single thread of the Thread Scheduled Executor Pool "); 
		Future<?> result3 = service
				//.scheduleAtFixedDelay //DOESN'T EXIST . There's a syntax error in the Book
				.scheduleWithFixedDelay(() -> System.out.println(Thread.currentThread().getName()+" is executing a recurring Runnable task4 at a fixed delay"),
											3,
											6,
											TimeUnit.SECONDS); 
		System.out.println(Thread.currentThread().getName() 
			+ " retrieved Callable task4 result : " +result3.get()
	        + " ( isDone()=" +result3.isDone()+ ";"
	        + " isCancelled()=" +result3.isCancelled()+ " )");
		
		//NB : the last two scheduling methods can't be called together because the one which is called before will be continuosly create and run task every declared period/delay
		// leaving the second method unreachable

		}catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}finally { if(service != null) service.shutdown();//The ThreadExecutor enters in Shutting Down status
			       System.out.println("Thread Executor enters in 'Shutting Down', keeps on executing running tasks and rejecting any new ones ( isShutdown()="+ service.isShutdown() +"; isTerminated()=" + service.isTerminated() +" ) " );
		}
		if(service != null) System.out.println("Thread Executor is in 'Shutdown' status, There are no tasks running and any new tasks are rejected ( isShutdown()="+ service.isShutdown() +"; isTerminated()=" + service.isTerminated() +" ) " );
		
	}

}
