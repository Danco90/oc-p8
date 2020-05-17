package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.workerthreads;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SubmittingTasks {

	public static void main(String[] args) {
		//Waiting for Results
		ExecutorService service = null;
		try 
		{   service = Executors.newSingleThreadExecutor();
			System.out.println("Thread Executor is now in 'Active' status, execute running tasks and accept new ones ( isShutdown()="+service.isShutdown()+"; isTerminated()="+service.isTerminated()+" ) ");
			
			//Executes a Runnable task at some point in the future and returns a Future representing the task
			System.out.println(Thread.currentThread().getName()+" is attempting to submit a Runnable task1 to the single thread of the Thread Executor Pool "); 
			Future<?> future = service.submit(() -> System.out.println(Thread.currentThread().getName()+" is executing a Runnable task1")); 
			
			//Future methods 
			//get()
			System.out.println(Thread.currentThread().getName()+" retrieved Runnable task1 result : "+future.get()
			                                                   +"(isDone()="+future.isDone()+"; "//Returns true if the task was completed, threw an exception, or was cancelled.
			                                                   + "isCancelled()="+future.isCancelled()+")");//returns true if task was cancelled before it complete normally 		
			Future<?> future2 = service.submit(() -> System.out.println(Thread.currentThread().getName()+" is executing a Runnable task2")); 
			
			/*cancel(boolean mayInterruptIfRunning) : attempts to cancel execution of the task.
	    		  This attempt will fail if the task has already completed, has already been cancelled,
		      or could not be cancelled for some other reason. May cause a concurrent CancellationException*/
			//System.out.println(Thread.currentThread().getName()+" attemps to cancel execution of Runnable task2 result : "
			//                    + future2.cancel(false)//MAY CAUSE concurrent CancellationException
			//                    + " (isDone="+future2.isDone()+"; isCancelled()="+future2.isCancelled()+")"); 	*/	
			
			System.out.println(Thread.currentThread().getName()+" retrieved Runnable task2 result : "+future2.get()+"(isDone="+future2.isDone()+"; isCancelled()="+future2.isDone()+")"); 		
		
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}finally {
			if(service != null) service.shutdown();//The ThreadExecutor enters in Shutting Down status
			System.out.println("Thread Executor enters in 'Shutting Down', keeps on executing running tasks and rejecting any new ones ( isShutdown()="+service.isShutdown()+"; isTerminated()="+service.isTerminated()+" ) ");
		}
		if(service != null)
		System.out.println("Thread Executor is in 'Shutdown' status, There are no tasks running and any new tasks are rejected ( isShutdown()="+service.isShutdown()+"; isTerminated()="+service.isTerminated()+" ) ");
		
	}

}
