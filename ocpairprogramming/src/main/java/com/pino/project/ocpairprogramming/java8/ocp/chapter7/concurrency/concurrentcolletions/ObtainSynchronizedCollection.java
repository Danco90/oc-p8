package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.concurrentcolletions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* ObtainSynchronizedCollection
* 
* Table 7.12 Synchronized collections methods
* 
*   Method Name
_________________________________________________
	synchronizedCollection(Collection<T> c)
	synchronizedList(List<T> list)
	synchronizedMap(Map<K,V> m)
	synchronizedNavigableMap(NavigableMap<K,V> m)
	synchronizedNavigableSet(NavigableSet<T> s)
	synchronizedSet(Set<T> s)
	synchronizedSortedMap(SortedMap<K,V> m)
	synchronizedSortedSet(SortedSet<T> s)
                                                         
--------------------------------------------------
*  :-) Guarantee mutual access
 *
 * :-( It doesn't work with iterator, which must be enclosed in a synchronized block. 
 *     Unlike concurrent collection, the synch. collection throws an exception, if they it is modified within an iterator by a single thread
 * 
 * NB: It's better to used only when we realize we should have DECLARED the collection concurrent and for such reason we did not. (no needed previously)
*/
public class ObtainSynchronizedCollection {

	public static void main(String[] args) {
		List<Integer> list = Collections.synchronizedList( //Used when suddently it's needed to make a collection concurrent which wasn't needed at the beginning, whether for multithreading or not
			                  new ArrayList<>(Arrays.asList(4,3,52)));
		synchronized(list) {//the block surrounding the iterator is MANDATORY
			for(int data: list)
			System.out.print(data+" ");
	    }// in order TO PREVENT FROM ConcurrentModificationException
			
		//Synchronized Collection Example 2 : Throwing ConcurrentModificationException
		Map<String, Object> foodData = new HashMap<String, Object>();
		foodData.put("penguin", 1);
		foodData.put("flamingo", 2);
		Map<String,Object> synchronizedFoodData = Collections.synchronizedMap(foodData);
		System.out.println("");
		for(String key: synchronizedFoodData.keySet())//Throw Exception
		    synchronizedFoodData.remove(key);

	}

}
