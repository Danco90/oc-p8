package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.reviews;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.DoubleStream;

/**
 * Question 16 of the Chapter 7 assessment in the book.
 * @author matteodaniele
 *
 */
public class PrintConstants {
	public static void main(String[] args) {
		ExecutorService service = Executors.newScheduledThreadPool(10);//(*) Hidden accesses 
		//Output nondeterministic  ahead of time and hanging thread executor service.
		DoubleStream.of(3.14159,2.71828) // b1 - Serial stream but ALWAYS causal order of execution for tasks submitted to a thread executor 
		.forEach(c -> service.submit( // b2
				() -> System.out.println(10*c))); // b3
		service.execute(() -> System.out.println("Printed")); // b4
		
		//Theread executor never shut down. Therefore, it will hangs (waits forever) at runtime.
		
		//(*) Tricky : This way, ExecutorService service doesn't have actually access to ScheduledExecutorService schedule methods!
	}

}
