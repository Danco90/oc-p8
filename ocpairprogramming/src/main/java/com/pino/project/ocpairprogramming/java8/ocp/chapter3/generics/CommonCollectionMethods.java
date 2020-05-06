package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommonCollectionMethods {

	public static void main(String[] args) {
		
		//
		Collection<Object> collection = new ArrayList<>();
		//boolean add(E element)
		System.out.println("\nCollection<Object> " + collection);
		System.out.println(collection + ", collection.add(\"Sparrow\") : " + collection.add("Sparrow"));//true
		System.out.println(collection + ", collection.add(\"Sparrow\") : " + collection.add("Sparrow"));//true because it can contains duplicates
		
		Set<String> set = new HashSet<>();
		System.out.println("\nHashSet<String> " + set);
		System.out.println(set + ", set.add(\"Sparrow\") : " + set.add("Sparrow")); // true
		System.out.println(set + ", set.add(\"Sparrow\") : " + set.add("Sparrow") + ". Elements must be unique!"); // false
	
		
		List<String> birds = new ArrayList<>();
		birds.add("hawk"); // [hawk]
		birds.add("hawk"); // [hawk, hawk]
		System.out.println("\nArrayList<String> " + birds);// [hawk, hawk]
		System.out.println(birds + ", birds.remove(\"cardinal\") : " + birds.remove("cardinal")); // prints false
		System.out.println(birds + ", birds.remove(\"hawk\") : " + birds.remove("hawk")); // prints true
		System.out.println(birds ); // [hawk]
		System.out.println(birds + ", birds.remove(100) : would throw an ArrayOutOfBoundException"); // prints true
		System.out.println(birds + ", birds.remove(0) : " + birds.remove(0)); // prints true
		System.out.println(birds); // []
		System.out.println(birds +", birds.isEmpty() : " + birds.isEmpty()); // true
		System.out.println(birds +", birds.size() : " + birds.size()); // 0
		
		birds.add("hawk"); // [hawk]
		birds.add("hawk"); // [hawk, hawk]
		System.out.println(birds + ", birds.isEmpty() : " + birds.isEmpty()); // false
		System.out.println(birds + ", birds.size() : " + birds.size()); // 2
		System.out.println(birds + ", birds.clear()  ");
		birds.clear(); // []
		System.out.println(birds + ", birds.isEmpty() : " + birds.isEmpty()); // true
		System.out.println(birds + ", birds.size() : " + birds.size()); // 0
		
		System.out.println(birds + ", birds.add(\"hawk\") : " +birds.add("hawk")); // [hawk]
		System.out.println(birds + ", birds.contains(\"hawk\") : " + birds.contains("hawk")); // true
		System.out.println(birds + ", birds.contains(\"robin\") : " + birds.contains("robin")); // false
		
	}

}
