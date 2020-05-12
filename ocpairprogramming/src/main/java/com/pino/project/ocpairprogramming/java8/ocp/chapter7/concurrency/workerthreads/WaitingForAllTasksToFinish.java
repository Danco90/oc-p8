package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.workerthreads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class WaitingForAllTasksToFinish {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = null;
		try{
			service = Executors.newSingleThreadExecutor();
			// Add tasks to the thread executor
			//1- execute Runnable task
			service.execute(() -> System.out.println("Executing : Runnable-task-1"));//Submits and attemps to exec a Runnable task at some point in the future
			
			//2- submit Runnable task
			Future rawResult = service.submit(() -> System.out.println("Executing and returning: Runnable-task-2"));//Submits and attemps to exec a Runnable task in the future and returns a Future representing the task
			try { System.out.println("Runnable-task-2 result is : " + rawResult.get());
			} catch (ExecutionException e1) { e1.printStackTrace();}
			
			//3- submit callable task
			Future<?> genResult = service.submit(() -> "Executing and returning: Callable-task-1");//Submits and attemps to exec a Runnable task in the future and returns a Future representing the task
			try { System.out.println("Runnable-task-3 result is : " + (String) genResult.get()/*.toString()*/);
			} catch (ExecutionException e) { e.printStackTrace(); }
			
			//4- invoke All Callable tasks 
			List<Callable<String>> tasksList = new ArrayList<>();
        		tasksList.add(() -> "Exec & retur : Callable-task-2" );
        		tasksList.add(() -> "Exec & retur : Callable-task-3" );
        		tasksList.add(() -> "Exec & retur : Callable-task-4" );
			List<Future<String>> genResultList = service.invokeAll(tasksList);
			genResultList.forEach((Future<String> s) -> {//Consumer<T>
				try { System.out.println(s.get());//get() throws ExecutionException
				} catch (InterruptedException | ExecutionException e) {
					e.printStackTrace();
				}
			});
			
			//5- invoke Any Callable tasks
			List<Callable<String>> tasksList2 = new ArrayList<>();
	    		tasksList2.add(() -> "Exec & retur : Callable-task-6" );
	    		tasksList2.add(() -> "Exec & retur : Callable-task-7" );
			try { String genResult2 = service.invokeAny(tasksList2);//it throws Execution Exception
					System.out.println(genResult2);
			} catch (ExecutionException e1) { e1.printStackTrace();
			}
			
			//6- invoke a never ending task Runnable tasks
			service.execute(() ->  {while(true);});//Submits and attemps to exec a Runnable task at some point in the future
			
			
		} finally {
			if(service != null) service.shutdown();//it might fail the shutdown
		}
		if(service != null) {//if it's still up and running
			service.awaitTermination(1, TimeUnit.MINUTES);//If failed,it might throw an InterruptedException
//			// Check whether all tasks are finished
			if(service.isTerminated())
				System.out.print("All tasks finished");
			else
				System.out.print("There is at least one running task, which has not finished yet for such reason!");
		}
	}

}
