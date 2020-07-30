package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.reviews;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

/**
 * Question 9 of the Chapter 7 assessment in the book.
 * Find Min of an array of Integers
 * TRICKY: It won't compile as is in the Assessment without a cast to int. 
 * @author matteodaniele
 *
 */
public class FindMin extends MyForkJoinTask {//MyForkJoinTask must inherit from RecursiveTask because compute() returns a value!
	private Integer[] elements;
	private int a;
	private int b;
	public FindMin(Integer[] elements, int a, int b) {
		this.elements = elements;
		this.a = a;
		this.b = b;
	}
	
	public Integer compute() {//OK. Although it is protected in the parent, you can override it with a more accessible modifier.
		if ((b-a) < 2)
		return Math.min(elements[a], elements[b]);
		else {
		int m = a + ((b-a) / 2);
		System.out.println(a + "," + m + "," + b);
		MyForkJoinTask t1 = new FindMin(elements, a, m);
		//int result = t1.fork().join();//* compilation ERROR because it doesn't convert Object to int 
		int result = (int) t1.fork().join();//it causes the process to wait, and DOES NOT perform any faster if there are 100 threads versus 1 thread
		return Math.min(new FindMin(elements, m, b).compute(), result);
		}
	}
	public static void main(String[] args) throws InterruptedException,
		ExecutionException {
		Integer[] elements = new Integer[] { 8, -3, 2, -54 };
		MyForkJoinTask task = new FindMin(elements, 0, elements.length-1);
		ForkJoinPool pool = new ForkJoinPool(1);//**WHY can't it produce a real ForkJoinPool At runtime ?
		//Integer sum = pool.invoke(task);//* AGAIN DOES NOT COMPILE because doesn't convert Object to Integer
		Integer sum =  (Integer) pool.invoke(task);//it DOES need an explicit cast to Integer
		System.out.println("Min: " + sum);//The code DOES correctly find the minimum.
	}
	//(*) NB : The book is one more time WRONG saying that the code compiles without issue!
	
	//(**) TRICKY assumption : Where're declaring, instantiating and using a ForkJoinPool instance, though! 

}
