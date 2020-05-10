package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.workerthreads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZooInfo {
	
	public static void main(String[] args) {
		ExecutorService service = null;
		try {
			service = Executors.newSingleThreadExecutor();//Active
			System.out.println("Thread Executor is now in 'Active' status, execute running tasks and accept new ones ( isShutdown()="+service.isShutdown()+"; isTerminated()="+service.isTerminated()+" ) ");
			System.out.println(Thread.currentThread().getName()+" begins");
			service.execute(() -> System.out.println(Thread.currentThread().getName()+" is now Printing zoo inventory"));
			service.execute(() -> {for(int i=0; i<3; i++)
			System.out.println(Thread.currentThread().getName()+" is now Printing record: "+i);}
			);
			service.execute(() -> System.out.println(Thread.currentThread().getName()+" is now Printing zoo inventory"));
			System.out.println(Thread.currentThread().getName()+" ends");
		} finally {
			if(service != null) service.shutdown();//The ThreadExecutor enters in Shutting Down status
			System.out.println("Thread Executor enters in 'Shutting Down', keeps on executing running tasks and rejecting any new ones ( isShutdown()="+service.isShutdown()+"; isTerminated()="+service.isTerminated()+" ) ");
		}
		if(service != null)
		System.out.println("Thread Executor is in 'Shutdown' status, There are no tasks running and any new tasks are rejected ( isShutdown()="+service.isShutdown()+"; isTerminated()="+service.isTerminated()+" ) ");
		
		
	}
}
