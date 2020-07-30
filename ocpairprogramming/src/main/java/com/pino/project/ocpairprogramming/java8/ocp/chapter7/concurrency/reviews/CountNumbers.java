package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.reviews;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * Question 12 of the Chapter 7 assessment in the book.
 * @author matteodaniele
 *
 */
public class CountNumbers extends RecursiveAction {
	private int start;
	private int end;
	public CountNumbers(int start, int end) {
		this.start = start;
		this.end = end;
	}
	protected void compute() {
		if (start<0) return;//base case NEVER reached. 
		else {//therefore, the recursive case will be called in an infinite loop, throwing probably a locking exception (first) and DEFINITELY a StackOverflowError at the end. 
			int middle = start + ((end-start) / 2);
			invokeAll(new CountNumbers(start, middle),
			new CountNumbers(middle, end)); // m1
		}
	}
	
	public static void main(String[] args) {
		ForkJoinTask<?> task = new CountNumbers(0, 4); // m2
		ForkJoinPool pool = new ForkJoinPool();
		Object result = pool.invoke(task); // m3
	}
		
}
