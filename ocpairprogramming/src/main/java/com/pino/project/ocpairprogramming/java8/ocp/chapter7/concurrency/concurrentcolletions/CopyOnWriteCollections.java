package com.pino.project.ocpairprogramming.java8.ocp.chapter7.concurrency.concurrentcolletions;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Understanding CopyOnWrite Collections
____________________________________________________________________________________________________________________________
concurrent collection class    Interface 	    Ordered?   sorted (elem or key in natural order) ? 
                                                          
----------------------------------------------------------------------------------------------------------------------------
CopyOnWriteArrayList           List		         Yes         No
                                                           	
CopyOnWriteArraySet            Set		         No	        No
														                                                   						                              
----------------------------------------------------------------------------------------------------------------------------
 * :-) prevent from ConcurrentModificationException
 *
 * :-( can use a lot of memory. therefore IT HAS TO BE USED in multi-threading where reads are far more common than writes
 *NB: it is recommended that you assign these objects to interface references, such as SortedMap or NavigableSet
 *
 *NB2: The CopyOnWrite classes are similar to the immutable object pattern, as a new underlying structure is created (use a lot of memory) every time
       the collection is modified. Unlike the immutable object pattern, though, the reference to the object stays the same even while 
       the underlying data is changed. Therefore, this is not an immutable object pattern!
 * 
 */
public class CopyOnWriteCollections {

	public static void main(String[] args) {
		List<Integer> list = /* new ArrayList<>(Arrays.asList(4,3,52));*/ // an unchecked ConcurrentModificationException is thrown by iterator
				new CopyOnWriteArrayList<>(Arrays.asList(4,3,52));
		for(Integer item: list) {//the iterator established prior to the changes (add(9) won't see the changes, and iterate always over the original elements (size=3)
			System.out.print(item+" ");
			list.add(9);//the reference 'list' to the object doesn't change, but data is copied to a NEW Object (new CopyOnWriteArrayList<>)
		}
		System.out.println();
		System.out.println("list Size: "+list.size());
	
        //Scenario 1
		Set<Integer> set = /* new HashSet<>(list);*/ // an unchecked ConcurrentModificationException is thrown by iterator
				new CopyOnWriteArraySet<>(list);//OK, but remove list duplicates
		for(Integer item: set) {
		System.out.print(item+" ");
		set.add(9);
		}
		System.out.println();
		System.out.println("Set Size: "+set.size());
		
		//Scenario 2
        Set<Integer> set2 = Collections.unmodifiableSet(set);

	}

}
