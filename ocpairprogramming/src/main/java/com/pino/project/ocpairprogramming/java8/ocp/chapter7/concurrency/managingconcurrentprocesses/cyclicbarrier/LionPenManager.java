package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.managingconcurrentprocesses.cyclicbarrier;

import java.util.concurrent.*;
public class LionPenManager {
	private void removeAnimals() { System.out.println(Thread.currentThread().getName()+" is removing animals"); }
	private void cleanPen() { System.out.println(Thread.currentThread().getName()+" is cleaning the pen"); }
	private void addAnimals() { System.out.println(Thread.currentThread().getName()+" is adding animals"); }
	
	public void performTask(CyclicBarrier c1, CyclicBarrier c2) {
		try {
		removeAnimals();
		System.out.println(Thread.currentThread().getName()+" now attempts to break the barrier c1 down!");
		c1.await();//to break down, the first barrier requires that all the threads in the pool wait for one another after attempting to smash it
		cleanPen();
		System.out.println(Thread.currentThread().getName()+" now attempts to break the barrier c2 down!");
		c2.await();//likewise, the second barrier is broken down after both threads have called c2.await() 
		
		addAnimals();
		} catch (InterruptedException | BrokenBarrierException e) {
			// Handle checked exceptions 
		}
	}
	public static void main(String[] args) {
		ExecutorService service = null;
		try {
			service = Executors.newFixedThreadPool(4);
			LionPenManager manager = new LionPenManager();
			CyclicBarrier c1 = new CyclicBarrier(4,//AT MOST as many parties (to break down) as the threads into the Pool
					() -> System.out.println("*** first barrier c1 has finally broken down. Pen can now be cleaned"));
			CyclicBarrier c2 = new CyclicBarrier(4/*3*/,//if less barriers than threads, the program hangs
					() -> System.out.println("*** Pen cleaned and also barrier c2 has finally broken down. "));
			for(int i=0; i<4; i++)
				service.submit(() -> manager.performTask(c1,c2));
		} finally {
			if(service != null) service.shutdown();
		}
	}
	
}
