package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.concurrentcolletions;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/** Understanding Blocking Queues
 * 
 *  Table 7.10 BlockingQueue waiting methods
 * +---------------------------------+-------------------------------------------------------------------------------+
 * |offer(E e, long timeout,         |Adds IMMEDIATELY item to the queue, or waiting the specified time IF NO SPACE  |       
 * |      TimeUnit unit)             |AVAILABLE(*), returning false if time elapses before space is available         |
 * +---------------------------------+-------------------------------------------------------------------------------+
 * |poll(long timeout, TimeUnit unit)|Retrieves and removes IMMEDIATELY an item from the queue, waiting the specified|
 * |                                 |time IF NO ITEM AVAILABLE , returning null if the time elapses before at lest  |
 * |                                 |one item is available                                                          |
 * +---------------------------------+-------------------------------------------------------------------------------+
 * 
 * (*)NB: It works only for bounded (immutable) queue with fixed size
 * */
public class BlockingQueue {
	
	enum Difficulty {
		EASY,
		HARD
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		// -- Blocking Queue usecases -- 
		//Easy usecase: both waiting methods returns true
		testBlockingQueueWaitingMethods(Difficulty.EASY);
	
		//Hard-usecase-1  : Timeout elapses and there's no space to offer into the queue
		givenBoundedBlockingQueueIfStillFullWhenOfferingAfterTimeoutThenReturnsFalse();
		
		//Hard-Usecase-2 : Timeout ealpses and there's no item to poll from the queue
		givenBlockingQueueIfStillEmptyWhenPollingAfterTimeoutThenReturnsNull();
		
		//Hard usecase (combo 1+2): At least one waiting method returning null AND another returning false
//		testBlockingQueueWaitingMethods(Difficulty.HARD);
		
        //-- Blocking Deque usecases --
		testBlockingDequeWaitingMethods(Difficulty.EASY);
//		testBlockingDequeWaitingMethods(Difficulty.HARD);
	
	}
	
	private static void givenBlockingQueueIfStillEmptyWhenPollingAfterTimeoutThenReturnsNull() throws InterruptedException {
		// create an Unbounded BlockingQueue 
        LinkedBlockingQueue<String> linkedQueue = new LinkedBlockingQueue<String>(); 
//      LinkedBlockingQueue<String> linkedQueue = new LinkedBlockingQueue<String>(4);//It works also with Bounded 
        // print elements of queue 
        System.out.println("Items in Queue are " + linkedQueue); 
        // using poll(long timeout, TimeUnit unit) method 
        System.out.println("Attemping to remove item From head (waiting max 5 sec) : "
                           + linkedQueue.poll(5, TimeUnit.SECONDS)); 
        // print queue details 
        System.out.println("Now Queue Contains" + linkedQueue);
	}
	
	private static void givenBoundedBlockingQueueIfStillFullWhenOfferingAfterTimeoutThenReturnsFalse() throws InterruptedException {
		// define capacity of LinkedBlockingQueue 
        int capacityOfQueue = 4; 
  
        // create object of LinkedBlockingQueue 
        LinkedBlockingQueue<String> linkedQueue 
            = new LinkedBlockingQueue<String>(capacityOfQueue); 
  
        // Add element to LinkedBlockingQueue 
        linkedQueue.add("Ravi"); 
        linkedQueue.add("Suraj"); 
        linkedQueue.add("Harsh"); 
        linkedQueue.add("Pinavi"); 
  
        // print elements of queue 
        System.out.println("Items in Queue are " + linkedQueue); 
        
        // Try to poll elements from linkedQueue 
        // using poll(long timeout, TimeUnit unit) method 
        System.out.println("Adding item Daniash From head: "
                           + linkedQueue.offer("Daniash",5, TimeUnit.SECONDS));
	}

	private static void testBlockingQueueWaitingMethods(Difficulty difficulty) throws InterruptedException {
		 // create object of LinkedBlockingQueue 
        LinkedBlockingQueue<String> linkedQueue = null;
		switch(difficulty) {
		case EASY :
			// create unbounded BlockingQueue
	        linkedQueue = new LinkedBlockingQueue<String>();
			break;
		case HARD :
			// define capacity of LinkedBlockingQueue 
	        int capacityOfQueue = 4; 
	  
	        // create bounded BlockingQueue 
	        linkedQueue = new LinkedBlockingQueue<String>(capacityOfQueue); 
			break;
		default :
			// create unbounded BlockingQueue
	        linkedQueue = new LinkedBlockingQueue<String>();
			break;
		}
		
  
        // Add element to LinkedBlockingQueue 
        linkedQueue.add("Ravi"); 
        linkedQueue.add("Suraj"); 
        linkedQueue.add("Harsh"); 
        linkedQueue.add("Pinavi"); 
  
        // print elements of queue 
        System.out.println("Items in Queue are " + linkedQueue); 
        
        if(difficulty == Difficulty.HARD) {
	        // Try to poll elements from linkedQueue 
	        // using poll(long timeout, TimeUnit unit) method 
	        System.out.println("Adding item Daniash From head: "+ linkedQueue.offer("Daniash",5, TimeUnit.SECONDS)); 
	        System.out.println("Now Queue Contains" + linkedQueue); 
        }
        // Try to poll elements from linkedQueue 
        // using poll(long timeout, TimeUnit unit) method 
        System.out.println("Removing item From head: "
                           + linkedQueue.poll(5, TimeUnit.SECONDS)); 
  
        // print queue details 
        System.out.println("Now Queue Contains" + linkedQueue); 
  
        // using poll(long timeout, TimeUnit unit) method 
        System.out.println("Removing item From head: "
                           + linkedQueue.poll(5, TimeUnit.SECONDS)); 
  
        // print queue details 
        System.out.println("Now Queue Contains" + linkedQueue); 
  
        // using poll(long timeout, TimeUnit unit) method 
        System.out.println("Removing item From head: "
                           + linkedQueue.poll(5, TimeUnit.SECONDS)); 
  
        // print queue details 
        System.out.println("Now Queue Contains" + linkedQueue); 
  
        // using poll(long timeout, TimeUnit unit) method 
        System.out.println("Removing item From head: "
                           + linkedQueue.poll(5, TimeUnit.SECONDS)); 
        // print queue details 
        System.out.println("Now Queue Contains" + linkedQueue);
        
        if(difficulty == Difficulty.HARD) {
	        // using poll(long timeout, TimeUnit unit) method 
	        System.out.println("Attemping to remove item From head (waiting max 5 sec) : "
	                           + linkedQueue.poll(5, TimeUnit.SECONDS)); 
	        System.out.println("Now Queue Contains" + linkedQueue);
        }
	}
	
	private static void testBlockingDequeWaitingMethods(Difficulty difficulty) throws InterruptedException {
		BlockingDeque<Integer> blockingDeque = null;
		//Blocking Deque
		switch(difficulty) {
		case EASY :
			// create unbounded BlockingDeque
			blockingDeque = new LinkedBlockingDeque<>();
			break;
		case HARD :
			// define capacity of LinkedBlockingDeque
	        int capacityOfDeque = 4; 
	  
	        // create bounded BlockingDeque
	        blockingDeque = new LinkedBlockingDeque<>(capacityOfDeque); 
			break;
		default :
			// create unbounded BlockingDeque
			blockingDeque = new LinkedBlockingDeque<>();
			break;
		}
		
		System.out.println("\n" + blockingDeque +  " LinkedBlockingDeque");
		System.out.println(blockingDeque + "<- blockingDeque.offer(91) : " + blockingDeque.offer(91));//[91]<-
		System.out.println(blockingDeque + "<- blockingDeque.offerFirst(5, 2, TimeUnit.MINUTES) : " 
										 + blockingDeque.offerFirst(5, 2, TimeUnit.MINUTES));//5->[91], after 2 min [5,91]
		System.out.println(blockingDeque + "<- blockingDeque.offerLast(47, 100, TimeUnit.MICROSECONDS) : " 
										 + blockingDeque.offerLast(47, 100, TimeUnit.MICROSECONDS));//[5, 91]<-47, after 0.0001 sec [5,91,47]
		System.out.println(blockingDeque + "<- blockingDeque.offer(3, 4, TimeUnit.SECONDS) : " 
										 + blockingDeque.offer(3, 4, TimeUnit.SECONDS));//[5,91,47]<-3, after 4 sec [5,91,47,3]
		
		System.out.println(blockingDeque + "<- blockingDeque.poll() : " + blockingDeque.poll());//5<-[91,47,3]
		System.out.println(blockingDeque + "<- blockingDeque.poll(950, TimeUnit.MILLISECONDS)) : " 
											+ blockingDeque.poll(950, TimeUnit.MILLISECONDS));//<-[91,47,3], after 0.95 sec [47,3]
		System.out.println(blockingDeque + "<- blockingDeque.pollFirst(200, TimeUnit.NANOSECONDS)) : " 
											+ blockingDeque.pollFirst(200, TimeUnit.NANOSECONDS));//<-[47,3], after 0.0000001 sec [3]
		System.out.println(blockingDeque + "<- blockingDeque.pollLast(1, TimeUnit.SECONDS) : " 
											+ blockingDeque.pollLast(1, TimeUnit.SECONDS));//<-[3], after 1 sec return []
		if(difficulty == Difficulty.HARD) {
		        // using poll(long timeout, TimeUnit unit) method 
		        System.out.println("Attemping to remove item From head (waiting max 5 sec) : "
		                           + blockingDeque.pollFirst(5, TimeUnit.SECONDS)); 
		        System.out.println("Now Queue Contains" + blockingDeque);
		      
	    }
	
	}
	


}
