package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.concurrentcolletions;

import java.util.NavigableSet;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * Understanding SkipList Collections
____________________________________________________________________________________________________________________________
concurrent collection		    non concur. counterpart 	  Java Collections Framework  Ordered?   sorted (elem or key in natural order) ? 
                                                           Interface 
----------------------------------------------------------------------------------------------------------------------------
ConcurrentSkipListSet           TreeSet		              SortedSet                    Yes         Yes
                                                           NavigableSet
	
ConcurrentSkipListMap           TreeMap		               ConcurrentMap               Yes	       Yes
														   SortedMap                                              
   						                                   NavigableMap
----------------------------------------------------------------------------------------------------------------------------
 *NB: it is recommended that you assign these objects to interface references, such as SortedMap or NavigableSet
 * 
 */
public class SkipListCollections {

	public static void main(String[] args) {
		NavigableSet<Integer> set = new ConcurrentSkipListSet<>();
		set.add(2);
		set.add(3);
		set.add(1);
		System.out.println("set : "+ set);
		for(Integer i : set) {
			set.remove(i);
		}
		System.out.println("set : "+ set);
		
		SortedMap<Integer,String> map = new ConcurrentSkipListMap<>();
		map.put(1, "one");
		map.put(2, "two");
		map.put(0, "zero");
		System.out.println("map : "+ map);
		for(Integer key : map.keySet()) {
			map.remove(key);
		}
		System.out.println("set : "+ set);
		

	}

}
