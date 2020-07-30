package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.workerthreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Topic  : Waiting for Task termination
 * Details: ExecutorService will not be automatically destroyed when there is not task to process. It will stay alive and wait for new tasks to do.
 * 			Therefore, the ExecutorService interface provides 3 methods for controlling the termination of tasks submitted to executor: 
 * 			- void shutdown() initiates an orderly shutdown in which previously submitted tasks are executed, but no new tasks 
 * 			             will be accepted. This method does not wait for previously submitted tasks (but not started executing) to complete execution.
 * 			
 * 			- List<Runnable> shutdownNow(); attempts to stop all actively executing tasks, halts(blocks) the processing of waiting tasks, 
 * 			  and returns a list of the tasks that were awaiting execution.This method does not wait for actively executing tasks to terminate 
 * 			  and tries to stop them forcefully. There are no guarantees beyond best-effort attempts to stop processing actively executing tasks. 
 * 			  This implementation cancels tasks via Thread.interrupt(), so any task that fails to respond to interrupts may never terminate.
 * 			
 * 			- awaitTermination(long timeout, TimeUnit unit) blocks until all tasks have completed execution after a shutdown request, 
 * 			  or the timeout occurs, or the current thread is interrupted, whichever happens first.
 * @author matteodaniele
 *
 */
public class BaseShuttingDownUsecase {
	
	protected static void shutdown(ExecutorService pool) {
		if(pool != null) 
		   pool.shutdown();  // Disable new tasks from being submitted
	}
	
	protected static List<Runnable> shutdownNow(ExecutorService pool) {
		if(pool != null) 
			return pool.shutdownNow(); //List of Runnable tasks never started. 
		return null;
	}
	
	/** If the previously shutdown request (shutdown()) has not completed yet (ExecutorService still != null), 
	 *  it attempts to delay the operation by blocking until one of the following condition happens first
	    i) all tasks have completed execution  
		ii) or the timeout occurs, 
		iii) or the current thread is interrupted
		If failed,it might throw an InterruptedException
	 * NB: We had better CALL IT only AFTER simple shutdown() AND absolutely NOT AFTER shutdownNow()
	 * @param pool
	 * @param timeout
	 * @param unit
	 * @throws InterruptedException
	 */
	protected static void awaitTermination(ExecutorService pool,long timeout, TimeUnit unit) throws InterruptedException {
		if(pool != null) {/*if it's still up and running, after a shutdown request 
							we try to delay the shutdown request by calling awaittermination()*/
			pool.awaitTermination(timeout, unit);//it blocks until one of the following condition happens first
			/* i) all tasks have completed execution  
			   ii) or the timeout occurs, 
			   iii) or the current thread is interrupted
			   If failed,it might throw an InterruptedException
			   NB: TRICKY** after shutdownNow(), the calling to awaitTermination() method will definitely FAIL because of the never ending task but the InterruptedException is never THROWN! WHY ?
			*/
			// Check whether all tasks are finished
			if(pool.isTerminated())
				System.out.print("All tasks finished");
			else
				System.out.print("There is at least one running task, which has not finished yet for such reason!");
		}
	}
	
	protected static void awaitTerminationAndShutdownNow(ExecutorService pool, long timeout, TimeUnit unit) {
		   try {
		     // Wait a while for existing tasks to terminate
		     if (!pool.awaitTermination(timeout, unit)) {
		       pool.shutdownNow(); // Cancel currently executing tasks
		       // Wait a while for tasks to respond to being cancelled
		       if (!pool.awaitTermination(timeout, unit))
		           System.err.println("Pool did not terminate : There is at least one running task, which has not finished yet for such reason!");
		       else System.out.print("All tasks finished");
		     }
		   } catch (InterruptedException ie) {
		     // (Re-)Cancel if current thread also interrupted
		     pool.shutdownNow();
		     // Preserve interrupt status
		     Thread.currentThread().interrupt();
		   }
	}
	
	protected static void checkExecutorStatus(ExecutorService pool) {
		if(pool != null) {//if it's still up and running, after a shutdown request 
			// Check whether all tasks are finished
			if(pool.isTerminated())
				System.out.print("All tasks finished");
			else
				System.out.print("There is at least one running task, which has not finished yet for such reason!");
		}
	}
	
	protected static void submitRunnableDuringShuttingdown(ExecutorService pool) {
		if(pool != null) {//if it's still up and running, after a shutdown request 
			// Check whether all tasks are finished
			if(pool.isTerminated()) {
				System.out.print("\nShutdown : All tasks finished");
			}else {
				System.out.print("\nShutting Down : There is at least one running task, which has not finished yet for such reason!");//This will be Printed
				//From now on, submitting a new task It throws unchecked RejectedExecutionException
				pool.execute(() -> System.out.print("Runnable task-outsider submitted after calling shutdown()"));
			}		
		}
	}

}
