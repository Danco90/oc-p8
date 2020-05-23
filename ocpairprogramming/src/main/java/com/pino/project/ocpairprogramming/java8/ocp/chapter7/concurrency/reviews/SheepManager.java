package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.reviews;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Question 17 of the Chapter 7 assessment in the book.
 * @author matteodaniele
 *
 */
public class SheepManager {
	private static AtomicInteger sheepCount1 = new AtomicInteger(0); // w1
	private static int sheepCount2 = 0;
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = null;
		try {
			service = Executors.newSingleThreadExecutor(); // w2 - 1 thread means result predictable, since NO task will be executed concurrently
			for(int i=0; i<100; i++)
			service.execute(() ->
			{sheepCount1.getAndIncrement(); sheepCount2++;}); // w3
			Thread.sleep(100);
			System.out.println(sheepCount1+" "+sheepCount2);
		} finally {
			if(service != null) service.shutdown();
		}
	}

}
