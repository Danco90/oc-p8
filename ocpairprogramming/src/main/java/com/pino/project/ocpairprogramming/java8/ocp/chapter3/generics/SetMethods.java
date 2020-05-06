package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

public class SetMethods {

	public static void main(String[] args) {
		//the insertion order is random
		Set<Integer> set = new HashSet<>();
		System.out.println(set + ", set.add(66) : " + set.add(66));
		System.out.println(set + ", set.add(10) : " + set.add(10));
		System.out.println(set + ", set.add(66) , " + set.add(66));//false
		System.out.println(set + ", set.add(8) , " + set.add(8)); 
		System.out.print("["); 
		for (Integer integer: set) System.out.print(integer + ","); // 66,8,10,
		System.out.println("] the order of insertion is random"); 
		
		//TreeSet implements NavigableSet . The insertion order is in their natural order thanks to .equals()
		System.out.println(" TreeSet implements NavigableSet ");
		Set<Integer> set2 = new TreeSet<>();
		System.out.println(set2 + ", set2.add(66) : " + set2.add(66));
		System.out.println(set2 + ", set2.add(10) : " + set2.add(10));
		System.out.println(set2 + ", set2.add(66) , " + set2.add(66));//false
		System.out.println(set2 + ", set2.add(66) , " + set2.add(8)); 
		System.out.print("[");
		for (Integer integer: set2) System.out.print(integer + ","); // 8,10,66
		System.out.println("] with TreeSet elements are inserted in their natural order"); 
		System.out.println("that's because Number implements Comparable Interface ");
		System.out.println("and compareTo() methods is called among each couple during insertion \n ");
		
		System.out.println("NavigableSet");
		NavigableSet<Integer> navSet = new TreeSet<>();
		for (int i = 1; i <= 20; i++) navSet.add(i);
		System.out.println(navSet);
		System.out.println(" navSet.lower(10) : " + navSet.lower(10)); // 9
		System.out.println(" navSet.floor(10) : " + navSet.floor(10)); // 10
		System.out.println(" navSet.ceiling(20) : " + navSet.ceiling(20)); // 20
		System.out.println(" navSet.higher(20) : " + navSet.higher(20)); // null
		
	}

}
