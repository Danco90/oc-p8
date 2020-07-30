package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.workerthreads;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class SubmittingTasks {

	public static void main(String[] args) {
		//Waiting for Results
		ExecutorService service = null;
		try 
		{   service = Executors.newSingleThreadExecutor();
			System.out.println("Thread Executor is now in 'Active' status, execute running tasks and accept new ones ( isShutdown()="+service.isShutdown()+"; isTerminated()="+service.isTerminated()+" ) ");
			
			/** submit(Runnable task) 
			 * executing a Runnable task at some point in the future and returns a Future representing the task*/
			System.out.println(Thread.currentThread().getName()+" is attempting to submit a Runnable task1 to the single thread of the Thread Executor Pool "); 
			Future<?> future = service.submit(() -> System.out.println(Thread.currentThread().getName()+" is executing a Runnable task1")); 
			
			/** TABLE 7.3 Future methods  pag.340
			 * boolean isDone() returns true if the task was completed, threw an exception, or was cancelled.
			 * boolean isCancelled() returns true if task was cancelled before it complete normally
			 * boolean cancel() attemps to cancel a running task. his attempt will fail if the task has already completed, has already been cancelled,
		       					or could not be cancelled for some other reason. May cause a concurrent CancellationException
			 * V get() retrieves the result of a task, waiting endlessly if it is not yet available.
			 * V get(long timeout, TimeUnit unit) it checks whether the result is available. If so, it returns it. If not it'll wait till the timeout. 
		     *                                    If after timeout the result is still unavailable, it throws a checked TimeOutException */
			 
			 /** Example of submit(Runnable task) executing and 
			  *  retrieving a Runnable task with get() */
			System.out.println(Thread.currentThread().getName() + " retrieved Runnable task1 result : "+future.get()
			                                                    + " (isDone()="+future.isDone()+";"//
			                                                    + " isCancelled()="+future.isCancelled()+")"); 		
			
			/** Example of Future<?> submit(Runnable task) executing aRunnable task, 
			 *  cancelling it and 
			 *  retrieving its result  with get(long timeout, TimeUnit unit) */
			Future<?> future2 = service.submit(() -> System.out.println(Thread.currentThread().getName()+" is executing a Runnable task2")); 
			System.out.println( Thread.currentThread().getName() + " attemps to cancel execution of Runnable task2 result : "
//			                    + future2.cancel(false)//To uncomment in order to see how it MIGHT CAUSE concurrent CancellationException
			                    + " (isDone="+future2.isDone() 
			                    + "; isCancelled()=" + future2.isCancelled() + ")" ); 	
			System.out.println(Thread.currentThread().getName() +" retrieved Runnable task2 result : " + future2.get(10, TimeUnit.SECONDS)
			                                                    +" (isDone="+future2.isDone() + "; isCancelled()=" + future2.isCancelled() + ")" ); 		
		   
			/**
			 * Example of <T> Future<T> submit(Callable<T> task)
			 *  get(long timeout, TimeUnit unit) */
			Future<?> future3 = service.submit(() -> /*{*/Thread.currentThread().getName()+" is executing a Callable task"/*;}*/ ); 
			System.out.println(Thread.currentThread().getName() +" retrieved Runnable task2 result : " + future3.get(1, TimeUnit.NANOSECONDS)//fails attempting to retrieve the result after 1 nanosec (way too fast) and throws a checked TimeOutException
                               +" (isDone="+future3.isDone() + "; isCancelled()=" + future2.isCancelled() + ")" ); 		
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}finally { if(service != null) service.shutdown();//The ThreadExecutor enters in Shutting Down status
			       System.out.println("Thread Executor enters in 'Shutting Down', keeps on executing running tasks and rejecting any new ones ( isShutdown()="+ service.isShutdown() +"; isTerminated()=" + service.isTerminated() +" ) " );
		}
		if(service != null) System.out.println("Thread Executor is in 'Shutdown' status, There are no tasks running and any new tasks are rejected ( isShutdown()="+ service.isShutdown() +"; isTerminated()=" + service.isTerminated() +" ) " );
	}

}
