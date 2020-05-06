package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.parallelstreams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelStreams {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		for(int i=1; i<=10; i++) list.add(i);
		list
		//Arrays.asList(1,2,3,4,5)
		.parallelStream()
		.forEach(s -> System.out.print(s + ""));
		
		//Pooled thread executor
		System.out.println("\nPooled thread executor behaves the same");
		ExecutorService service = null;
		try {
			//3 threads
			service = Executors.newFixedThreadPool(3);	
	        // 6 Runnable tasks 
	        service.submit(() -> System.out.println(1 + " run. task executed by " +Thread.currentThread().getName()));
	        service.submit(() -> System.out.println(2 + " run. task executed by " +Thread.currentThread().getName()));
	        service.submit(() -> System.out.println(3 + " run. task executed by " +Thread.currentThread().getName()));
	        service.submit(() -> System.out.println(4 + " run. task executed by " +Thread.currentThread().getName()));
	        service.submit(() -> System.out.println(5 + " run. task executed by " +Thread.currentThread().getName()));
	        service.submit(() -> System.out.println(6 + " run. task executed by " +Thread.currentThread().getName()));
	        service.submit(() -> System.out.println(7 + " run. task executed by " +Thread.currentThread().getName()));
	        service.submit(() -> System.out.println(8 + " run. task executed by " +Thread.currentThread().getName()));
	        service.submit(() -> System.out.println(9 + " run. task executed by " +Thread.currentThread().getName()));
	        service.submit(() -> System.out.println(10 + " run. task executed by " +Thread.currentThread().getName()));
		}finally {
			if(service != null) service.shutdown();
		}
		
		//Understanding Performance Improvements
		
	}

}
