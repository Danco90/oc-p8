package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.concurrentcolletions;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ConcurrentCollections {

	public static void main(String[] args) throws InterruptedException {
		Map<String, Object> foodData = new HashMap<String, Object>();
		foodData.put("penguin", 1);
		foodData.put("flamingo", 2);
		System.out.println(foodData + "<- HashMap");
		for(String key: foodData.keySet())//the iterator returned will throw a ConcurrentModificationException
//		foodData.remove(key);//because of the remove
		
		//Changing the Implementation.. 
		foodData = new ConcurrentHashMap<String, Object>();
		foodData.put("penguin", 1);
		foodData.put("flamingo", 2);
		System.out.println(foodData + "<- Concurrent HashMap. ");
		for(String key: foodData.keySet()) 
		{//..will solve the issue by updating the iterator returned by keySet()
			foodData.remove(key);
			System.out.println(foodData + ", remove(" + key + ")");
		}
		System.out.println(foodData );
		
		System.out.println("\n ___ Other concurrent collections ______");
		Map<String,Integer> map = new ConcurrentHashMap<>();
		map.put("zebra", 52);
		map.put("elephant", 10);
		System.out.println(map + " ConcurrentHashMap");
		System.out.println(map + ", map.get(\"elephant\") : " + map.get("elephant"));
		System.out.println(map);
		
		Queue<Integer> queue = new ConcurrentLinkedQueue<>();
		System.out.println("\n" + queue + " ConcurrentLinkedQueue");
		System.out.println(queue + ", queue.offer(31) : " + queue.offer(31));
		System.out.println(queue + ", queue.peek() : " + queue.peek());
		System.out.println(queue + ", queue.poll() : " + queue.poll());
		System.out.println(queue);
		
		Deque<Integer> deque = new ConcurrentLinkedDeque<>();
		System.out.println("\n" + deque + " ConcurrentLinkedDeque");
		System.out.println(deque + " deque.offer(10) : " + deque.offer(10));
		System.out.println(deque + " deque.push(4) (void)");
		deque.push(4);
		System.out.println(deque + " deque.peek() : " + deque.peek());
		System.out.println(deque + " deque.pop() : " + deque.pop());
		System.out.println(deque);
		
		//1 Understanding Blocking Queues
		//1.A EASY
		try {
			BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
			System.out.println("\n" + blockingQueue +  " LinkedBlockingQueue (1.A EASY)");
			System.out.println(blockingQueue + "<- blockingQueue.offer(39) : " + blockingQueue.offer(39));
			System.out.println(blockingQueue + "<- blockingQueue.offer(3, 4, TimeUnit.SECONDS) : " + blockingQueue.offer(3, 4, TimeUnit.SECONDS));
			System.out.println("<-" + blockingQueue + " blockingQueue.poll() : " + blockingQueue.poll());
			System.out.println(blockingQueue + " blockingQueue.poll(10, TimeUnit.MILLISECONDS)) : " + blockingQueue.poll(10, TimeUnit.MILLISECONDS));
			System.out.println(blockingQueue);
		} catch (InterruptedException e) { }
		//1.B MEDIUM
		try {
			BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
			System.out.println("\n" + blockingQueue +  " LinkedBlockingQueue (1.B HARD)");
			System.out.println(blockingQueue + "<- blockingQueue.offer(39) : " + blockingQueue.offer(39));
			System.out.println(blockingQueue + "<- blockingQueue.offer(3, 4, TimeUnit.SECONDS) : " + blockingQueue.offer(3, 4, TimeUnit.SECONDS));
			System.out.println(blockingQueue + "<- blockingQueue.offer(2, 4, TimeUnit.MILLISECONDS) : " + blockingQueue.offer(2, 4, TimeUnit.MILLISECONDS));
			
			System.out.println("<-" + blockingQueue + " blockingQueue.poll() : " + blockingQueue.poll());
			System.out.println(blockingQueue + " blockingQueue.poll(10, TimeUnit.SECONDS)) : " + blockingQueue.poll(10, TimeUnit.SECONDS));
			System.out.println(blockingQueue + " blockingQueue.poll(10, TimeUnit.MILLISECONDS)) : " + blockingQueue.poll(10, TimeUnit.MILLISECONDS));
			System.out.println(blockingQueue);
		} catch (InterruptedException e) { }
		
		//1.C HARD
		
		// define capacity of LinkedBlockingQueue 
        int capacityOfQueue = 4; 
  
        // create object of LinkedBlockingQueue 
        LinkedBlockingQueue<String> linkedQueue 
            = new LinkedBlockingQueue<String>(capacityOfQueue); 
  
        // Add element to LinkedBlockingQueue 
        linkedQueue.add("Ravi"); 
        linkedQueue.add("Suraj"); 
        linkedQueue.add("Harsh"); 
  
        // print elements of queue 
        System.out.println("Items in Queue are " + linkedQueue); 
  
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
		
		//Blocking Deque
		try {
			BlockingDeque<Integer> blockingDeque = new LinkedBlockingDeque<>();
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
		} catch (InterruptedException e) { }
	}

}
