package com.pino.project.ocpairprogramming.java8.ocp.chapter3.collections;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommonCollectionsMethods {

	public static void main(String[] args) {
		System.out.println("[boolean add(E e); ..List..]");
		List<String> list = new ArrayList<>();
		//boolean add(E e); into List, as an override of the add() into Collection
		System.out.println(list.add("Sparrow"));//true
		System.out.println(list.add("Sparrow"));//true as it allows duplicates
		
		System.out.println("\n[boolean add(E e); ..Set..]");
		Set<String> set = new HashSet<>();
		//boolean add(E e); into Set, as an override of the add() into Collection
		System.out.println(set.add("Sparrow"));//true
		System.out.println(set.add("Sparrow"));//false since elements must be unique in set
		
		//boolean remove(Object object) tells us whether a match was removed
		System.out.println("\n[boolean remove(Object object) ..List..]");
		List<String> birds = new ArrayList<>();
		birds.add("hawk");// [hawk]
		birds.add("hawk");// [hawk, hawk]
		System.out.println(birds.remove("cardinal"));//false
		System.out.println(birds.remove("hawk"));//true
		System.out.println(birds);// [hawk]
		
		//boolean remove(int index) tells us whether an item at a matching index was removed.
		//NB: An index that does not exist will throw an exception.
		try { System.out.println(birds.remove(100)); }
		catch (IndexOutOfBoundsException e) {System.out.println("Caught and swallowed exception: "+e);}
		
		//isEmpty() and size()
		System.out.println("\nboolean isEmpty() and int size() ..List..]");
		System.out.println(birds.isEmpty());//false
		System.out.println(birds.size());//1
		System.out.println("Removing element from list birds at index 0: "+birds.remove(0));
		System.out.println(birds);// []
		System.out.println(birds.isEmpty());//true
		System.out.println(birds.size());//0
		birds.add("hawk");// [hawk]
		birds.add("hawk");// [hawk, hawk]  
		System.out.println(birds.isEmpty());//false
		System.out.println(birds.size());//2
		System.out.println(birds);// [hawk, hawk]
		
		//void clear(), discard all elements of the Collection
		System.out.println("\nvoid clear() ..List..]");
		birds.clear();
		System.out.println(birds.isEmpty());//true
		System.out.println(birds.size());//0
		System.out.println(birds);// []
		
		//boolean contains(Object object)
		birds.add("hawk");
		System.out.println(birds.contains("hawk"));//(*) true
		System.out.println(birds.contains("robin"));//(*) false
		//(*) it calls equals() on each element of the ArrayList to see if there are any matches.
		
	}

}
