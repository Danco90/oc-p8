package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.concurrentcolletions;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingDequeDemo {

	public static void main(String[] args) {
		//'main' thread (still independent from pool-1-thread-1)
		System.out.println(Thread.currentThread().getName() + " begin");
		
        BlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<>(6);
        ExecutorService service = null;
		try {
			//2 threads
			service = Executors.newFixedThreadPool(2);	
	        LinkedBlockingDequeDemo x = new LinkedBlockingDequeDemo();
	        // 2 producer tasks and one consumer task
	        service.execute(x.new LinkedDQProducer(blockingDeque));// thread assignment  1 to 1 
//	        service.execute(x.new LinkedDQProducer(blockingDeque));
	        service.execute(x.new LinkedDQConsumer(blockingDeque));
	        service.execute(x.new LinkedDQProducer(blockingDeque));
	        service.execute(x.new LinkedDQConsumerB(blockingDeque));
	        
	      //'main' thread
			System.out.println(Thread.currentThread().getName() + " end");
			
		}finally {
			if(service != null) service.shutdown();
		}
    }
	
	// Producer
	class LinkedDQProducer implements Runnable{
	    BlockingDeque<Integer> blockingDeque;
	    LinkedDQProducer(BlockingDeque<Integer> blockingDeque){
	        this.blockingDeque = blockingDeque;
	    }
	    @Override
	    public void run() {
	        for(int i = 0; i < 5; i++){            
//	            blockingDeque.addFirst(i);
	        		try {
						blockingDeque.offerFirst(i, 100, TimeUnit.MILLISECONDS);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            System.out.println(Thread.currentThread().getName()+" executed task producer : Added to queue-" + i);            
	        }
	    }
	}
	
	//Consumer
	class LinkedDQConsumer implements Runnable{
	    BlockingDeque<Integer> blockingDeque;
	    LinkedDQConsumer(BlockingDeque<Integer> blockingDeque){
	      this.blockingDeque = blockingDeque;
	    }
	    @Override
	    public void run() {
	        for(int i = 0; i < 8; i++){
	            try {
	                  System.out.println(Thread.currentThread().getName()+" executed task consumer :retrieved- " 
//	                		  + blockingDeque.takeLast()
	                		  + blockingDeque.pollFirst(1000, TimeUnit.MILLISECONDS));
	            } catch (InterruptedException e) {
	            
	                e.printStackTrace();
	            }
	          }
	    }
	}
	
	//Consumer B
	class LinkedDQConsumerB implements Runnable{
	    BlockingDeque<Integer> blockingDeque;
	    LinkedDQConsumerB(BlockingDeque<Integer> blockingDeque){
	      this.blockingDeque = blockingDeque;
	    }
	    @Override
	    public void run() {
	        for(int i = 0; i < 10; i++){
	            try {
	                  System.out.println(Thread.currentThread().getName()+" executed task consumerB :retrieved- " 
//		                		  + blockingDeque.takeLast()
	                		  + blockingDeque.pollFirst(1000, TimeUnit.MICROSECONDS));
	            } catch (InterruptedException e) {
	            
	                e.printStackTrace();
	            }
	          }
	    }
	}

}
