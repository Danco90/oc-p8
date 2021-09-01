package com.pino.project.ocpairprogramming.java8.ocp.chapter3.collections;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapImplementationsDemo {

	public static void main(String[] args) {
		// MAP : identify values by a key
		/*
		 * Map methods 						Description
		 * void clear()						Removes all keys and values from the map.
		 * boolean isEmpty()					Returns whether the map is empty.
		 * int size()						Returns the number of entries (key/value pairs) in the map.
		 * V get(Object key)					Returns the value mapped by key or null if none is mapped.
		 * V put(K key, V value)				Adds or replaces key/value pair. Returns previous value or null.
		 * V remove(Object key)				Removes and returns value mapped to key. Returns null if none.
		 * boolean containsKey(Object key)   Returns whether key is in map.
		 * boolean containsValue(Object)     Returns value is in map.
		 * Set<K> keySet()					Returns set of all keys.
		 * Collection<V> values()    		Returns Collection of all values.
		 */
		
		//Comparing Map Implementations
		//1. HashMap stores the keys in a hash table and uses the hashCode()
		// of the keys to retrieve their values more efficiently
		//++benefit: adding elements and retrieving the element by key both have O(1) constant time 
		//--tradeoff: order of insertion is lost
		System.out.println("HashMap ::");
		Map<String, String> map = new HashMap<>();
		map.put("koala", "bamboo");
		map.put("lion", "meat");
		map.put("giraffe", "leaf");
		String food = map.get("koala"); // bamboo
		for (String key: map.keySet()) System.out.print(key + ","); // koala,giraffe,lion, NOT SORTED order, or the order of insertion
		//The order is determined by hashCode() of the key
		for (String value: map.values()) System.out.print(value + ","); // bamboo,meat,leaf, the order correspond to the order of the keys
//		System.out.println(map.contains("lion"));//COMPILATION ERROR as it is a method of Collection
		System.out.println(map.containsKey("lion"));//true
		System.out.println(map.containsValue("lion"));//false
		System.out.println(map.size()); // 3
		
		//2. LinkedHashMap: it is like HashMap, but keeps the order of insertion
		//++benefit: order of insertion is maintained
		//--tradeoff: slower than HashMap
		System.out.println("\nLinkedHashMap ::");
		Map<String, String> linkHMap = new LinkedHashMap<>();
		linkHMap.put("koala", "bamboo");
		linkHMap.put("lion", "meat");
		linkHMap.put("giraffe", "leaf");
		for (String key: linkHMap.keySet()) System.out.print(key + ",");// koala, lion, giraffe with the order of insertion
		
		//3. TreeMap stores the keys in a sorted tree structure.
		//++benefit: keys are always in sorted order
		//--tradeoff: adding and checking if a key is present are both O(log n) good
		System.out.println("\nTreeMap ::");
		Map<String, String> treeMap = new TreeMap<>();
		treeMap.put("koala", "bamboo");
		treeMap.put("lion", "meat");
		treeMap.put("giraffe", "leaf");
		for (String key: treeMap.keySet()) System.out.print(key + ",");//giraffe,koala,lion in SORTED order
		
		//4. Hashtable is like Vector* in that it is really old and thread-safe
		Map<Integer,String> hashtable = new Hashtable<>();
		//*ArrayList is to Vector as HashMap is to Hashtable
		
	}

}
