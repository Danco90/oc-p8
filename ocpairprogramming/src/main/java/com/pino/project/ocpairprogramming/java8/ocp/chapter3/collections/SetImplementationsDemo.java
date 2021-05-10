package com.pino.project.ocpairprogramming.java8.ocp.chapter3.collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

/*
 * Set is used when you don't want duplicate entries
 */
public class SetImplementationsDemo {

	public static void main(String[] args) {
		
		//A. Comparing Set Implementations and working with methods
		//A1. HashSet stores its elements in a hash table. It uses hashCode() of the objects to retrieve them more efficiently
		//++benefit: adding elements and checking if they are in the set both have constant time O(1)
		//--tradeoff: lose order in which elements are inserted!
		System.out.println("HashSet : element stored randomly, but assigned to buckets according to their hashCode() returned by the implementation of the Set ");
		Set<Integer> set = new HashSet<>();
		boolean b1 = set.add(66);//true
		boolean b2 = set.add(10);//true
		boolean b3 = set.add(66);//false
		System.out.println("b3="+b3);
		boolean b4 = set.add(8);//true
		for (Integer integer: set) System.out.print(integer+ ",");//66,8,10, as order of insertion is lost
		//Retrieving element from an HashSet
		//hashCode() is used to know which bucket to look in so that java won't have to look through the whole set
		//Best case: hash codes are UNIQUE, and java has to call equals() on only one object
		//Worst case: all implementations return the same hasnCode(), and java has to call equals() on every element of the set anyway.
		System.out.println("Retrieving element from an HashSet through hashCode() and equals() called at least once and at worst O(n) linear time");
		set.contains(10);//It'll probably use equals() just once
		
		//A2. TreeSet implements NavigableSet: stores elements in a sorted tree structure.
		//++benefit : set is ALWAYS in sorted order
		//--tradeoff: adding and checking if an elem is present are both O(log n), which is still good.
		System.out.println("TreeSet : elements stored in their natural sorted ordered ");
		Set<Integer> tSet = new TreeSet<>();
		b1 = tSet.add(66);//true
		b2 = tSet.add(10);//true
		b3 = tSet.add(66);//false
		System.out.println("b3="+b3);
		b4 = tSet.add(8);//true
		for (Integer integer: tSet) System.out.print(integer+ ",");//8,10,66, as element were automatically stored in their natural sorted order
		//NB: Numbers implement the COmparable interface, used for sorting.
		//Indeed Integer is an object which has already overrided the equals() method
		
		//Additional methods from Navigable Interface
		System.out.println("\nAdditinal methods from Navigable Interface");
		/*
		 * Method 									Description
		 * E lower(E e)			Returns greatest element that is < e, or null if no such element
		   E floor(E e)			Returns greatest element that is <= e, or null if no such element
		   E ceiling(E e) 		Returns smallest element that is >= e, or null if no such element
		   E higher(E e) 		Returns smallest element that is > e, or null if no such element
		 */
		  NavigableSet<Integer> navSet = new TreeSet<>();
		  for (int i = 1; i <= 20; i++) navSet.add(i);
		  System.out.println(navSet);
		  System.out.println(navSet.lower(1));//null
		  System.out.println(navSet.lower(10));//9
		  System.out.println(navSet.floor(0));//null
		  System.out.println(navSet.floor(10));//10
		  System.out.println(navSet.floor(21));//20 TRICKY
		  System.out.println(navSet.ceiling(19));//19 TRICKY
		  System.out.println(navSet.ceiling(20));//20
		  System.out.println(navSet.ceiling(21));//null
		  System.out.println(navSet.higher(19));//20
		  System.out.println(navSet.higher(20));//null
		
	}

}
