package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.intro;

public class CheckResults {
	private static int counter = 0;
	public static void main(String[] args) throws InterruptedException {
		//Creating a thread by passing in a Runnable lambda
		new Thread(() -> {
			for(int i=0; i<500; i++) CheckResults.counter++;
		}).start();
		
		//Example 1) Hanging in Infinite Loop
		/*while(CheckResults.counter<100) {
			System.out.println("Not reached yet");//WE DO NOT KNOW how many times this code executes
		}//It could hang, by operating infinitely, if our thread scheduler is particularly poor
		System.out.println("Reached!");
		*/
		
		//Example 2) implementing POLLING : NO LONGER Infinite loop. Yeah!
		//Result might be improved by using the Thread.sleep() method to 
		while(CheckResults.counter<100) {//Times of loop execution still unknown!
			System.out.println(Thread.currentThread().getName()+" is polling data.. Not reached yet..(counter="+counter+")");
			long start = System.currentTimeMillis();
			Thread.sleep(1000); //but we prevented a possibly infinite loop 
			//from executing and locking up our main program.
			System.out.println("Sleep time in ms = "+(System.currentTimeMillis()-start));
	         
		}
		System.out.println(".. Polling data Reached by "+Thread.currentThread().getName()+"! (counter="+counter+")");
	}

}
