package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.reviews;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.IntStream;

/**
 * Question 21 of the Chapter 7 assessment in the book.
 * @author matteodaniele
 *
 */
public class CountZooAnimals {
	public static Integer performCount(int exhibitNumber) {
		// IMPLEMENTATION OMITTED
		return null;//just to avoid compilation error. But normally it should return a number according to the calculation
	}
	public static void printResults(Future<?> f) {
		try {
			/*
			 * ALL can happen in here : even liveness problem 
			 */
		} catch (Exception e) {//But only runtime exceptions can be really thrown in our case! 
			System.out.println("Exception!");
		}
	}
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService service = Executors.newSingleThreadExecutor();
		final List<Future<?>> results = new ArrayList<>();
		IntStream.range(0, 10)
			.forEach(i -> results.add(
					service.submit(() -> performCount(i)))); // o2 - (*)
		results.stream().forEach(f -> printResults(f));
		service.shutdown();
		
		/* (*) Possible output (x10 Times) of Future.get() 
		 * 	   A) a number
		 *     C) a null 
		 *     D) RuntimeException 
		 *     E) hangs indefinitely at runtime in case of DEADLOCK
		 */                        
	}

}
