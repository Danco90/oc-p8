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

/**
 * Table 7. 9 Concurrent collection classes
 * +----------------------+----------------------------+---------------------------+
   |  			          |Java Collections Framework  |Elements					  |
   |Class Name            |Interface                   |Ordered? Sorted? Blocking? |
   +----------------------+----------------------------+---------------------------+
   |	ConcurrentHashMap     |ConcurrentMap               |No       No       No       |
   |ConcurrentLinkedDeque |Deque                       |Yes      No       No       |		 
   |ConcurrentLinkedQueue |Queue                       |Yes      No       No       |
   |ConcurrentSkipListMap |ConcurrentMap               |Yes      Yes      No       |
   |						  |SortedMap                   |                           |
   |						  |NavigableMap                |                           |
   |ConcurrentSkipListSet |SortedSet                   |Yes      Yes      No       |                 
   |                      |NavigableSet                |                           |           
   |CopyOnWriteArrayList  |List                        |Yes      No       No       |
   |CopyOnWriteArraySet   |Set                         |No       No       No       |
   |LinkedBlockingDeque   |BlockingQueue               |Yes      No       Yes      |
   |                      |BlockingDeque               |                           |
   |LinkedBlockingQueue   |BlockingQueue               |Yes      No       Yes      |
   +----------------------+----------------------------+---------------------------+
 * @author matteodaniele
 *
 */
public class ConcurrentCollections {

	public static void main(String[] args) throws InterruptedException {
		Map<String, Object> foodData = new HashMap<String, Object>();
		foodData.put("penguin", 1);
		foodData.put("flamingo", 2);
		System.out.println(foodData + "<- HashMap");
		for(String key: foodData.keySet()/**/)//the iterator returned by keySet() will throw a ConcurrentModificationException
//		foodData.remove(key);//because not properly updated after the first element is removed 
		//(**) Memory Consistency Error - occurs because two threads have inconsistent views of what should be the same data (set size and content)
		
		//Changing the Implementation.. 
		foodData = new ConcurrentHashMap<String, Object>(); //now all access (read/write) to the class is consistent!
		foodData.put("penguin", 1);
		foodData.put("flamingo", 2);
		System.out.println(foodData + "<- Concurrent HashMap. ");
		for(String key: foodData.keySet())
		{//..will solve the issue by updating the iterator, which is returned by keySet(), as soon as an object is removed from the Map
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
		/* Table 7.10 BlockingQueue waiting methods
		 * +---------------------------------+-------------------------------------------------------------------------------+
		 * |offer(E e, long timeout,         |Adds IMMEDIATELY item to the queue, or waiting the specified time IF NO SPACE  |       
		 * |      TimeUnit unit)             |AVAILABLE(*),returning false if time elapses before space is available            |
		 * +---------------------------------+-------------------------------------------------------------------------------+
		 * |poll(long timeout, TimeUnit unit)|Retrieves and removes IMMEDIATELY an item from the queue, waiting the specified|
         * |                                 |time IF NO ITEM AVAILABLE , returning null if the time elapses before at lest  |
		 * |                                 |one item is available                                                          |
		 * +---------------------------------+-------------------------------------------------------------------------------+
		 * 
		 * (*)NB: It works only for bounded (immutable) queue with fixed size
		 * */
		//EASY EXAMPLE 1.A 
		try {
			BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
			System.out.println("\n" + blockingQueue +  " LinkedBlockingQueue (1.A EASY)");
			System.out.println(blockingQueue + "<- blockingQueue.offer(39) : " + blockingQueue.offer(39));
			System.out.println(blockingQueue + "<- blockingQueue.offer(3, 4, TimeUnit.SECONDS) : " + blockingQueue.offer(3, 4, TimeUnit.SECONDS));
			System.out.println("<-" + blockingQueue + " blockingQueue.poll() : " + blockingQueue.poll());
			System.out.println(blockingQueue + " blockingQueue.poll(10, TimeUnit.MILLISECONDS)) : " + blockingQueue.poll(10, TimeUnit.MILLISECONDS));
			System.out.println(blockingQueue);
		} catch (InterruptedException e) { }
		
		//MEDIUM EXAMPLE 1.B 
		try {
			BlockingQueue<Integer> blockingQueue = new LinkedBlockingQueue<>();
			System.out.println("\n" + blockingQueue +  " LinkedBlockingQueue (1.B MEDIUM)");
			System.out.println(blockingQueue + "<- blockingQueue.offer(39) : " + blockingQueue.offer(39));
			System.out.println(blockingQueue + "<- blockingQueue.offer(3, 4, TimeUnit.SECONDS) : " + blockingQueue.offer(3, 4, TimeUnit.SECONDS));
			System.out.println(blockingQueue + "<- blockingQueue.offer(2, 4, TimeUnit.MILLISECONDS) : " + blockingQueue.offer(2, 4, TimeUnit.MILLISECONDS));
			
			System.out.println("<-" + blockingQueue + " blockingQueue.poll() : " + blockingQueue.poll());
			System.out.println(blockingQueue + " blockingQueue.poll(10, TimeUnit.SECONDS)) : " + blockingQueue.poll(10, TimeUnit.SECONDS));
			System.out.println(blockingQueue + " blockingQueue.poll(10, TimeUnit.MILLISECONDS)) : " + blockingQueue.poll(10, TimeUnit.MILLISECONDS));
			System.out.println(blockingQueue);
		} catch (InterruptedException e) { }
		
		
	}

}
