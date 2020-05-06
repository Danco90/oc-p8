package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ZooInfo {

	public static void main(String[] args) {
		ExecutorService service = null;
		try {
			
			service = Executors.newSingleThreadExecutor();// create pool-1-thread-1
			//With Single-thread exec. results are guaranteed to be executed in the order in which
			//they are added to the executor service.
			
			//'main' thread (still independent from pool-1-thread-1)
			System.out.println(Thread.currentThread().getName() + " begin");
			
			//Creating tasks using runnable-based lambda expression
			//pool-1-thread-1 executes task#1 
			service.execute(() -> System.out.println(Thread.currentThread().getName() + " Printing zoo inventory"));
			//pool-1-thread-1 executes task#1
			service.execute(() -> { for(int i=0; i<3; i++) 
					System.out.println(Thread.currentThread().getName() + " Printing record: "+i);
			});
			//pool-1-thread-1 executes task#1
			service.execute(() -> System.out.println(Thread.currentThread().getName() + " Printing zoo inventory"));
			
			//'main' thread
			System.out.println(Thread.currentThread().getName() + " end");
		}finally {
			if(service != null) service.shutdown();
		}

	}

}
