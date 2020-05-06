package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.parallelstreams;

import java.util.Arrays;

public class IndependentOperations {

	public static void main(String[] args) {
		Arrays.asList("jackal","kangaroo","lemur")
		.parallelStream()
//		.map(s -> s.toUpperCase())
		.map(s -> {System.out.println(s + "task executed by " + Thread.currentThread().getName()); 
					return (s.toUpperCase() + "task executed by " + Thread.currentThread().getName());})
					//print and return in seperate worker threads from a ForkJoinPool
					
		.forEach(System.out::println);
		
		
		
	}

}
