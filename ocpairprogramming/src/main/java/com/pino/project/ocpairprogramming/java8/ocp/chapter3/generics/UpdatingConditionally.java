package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class UpdatingConditionally {

	public static void main(String[] args) {
		Map<String, String> favorites = new HashMap<>();
		favorites.put("Jenny", "Bus Tour");
		favorites.put("Tom", null);
		System.out.println(favorites);
		favorites.putIfAbsent("Jenny", "Tram");//doesn't replace anything
		favorites.putIfAbsent("Tom", "Tram");
		System.out.println(favorites);
		
		//merge()
		BiFunction<String, String, String> mapper = (v1, v2) -> v1.length() > v2.length() ? v1 : v2 ;
		String jenny = favorites.merge("Jenny", "Skyride", mapper);//mapper funct gets called but doesn't change
		String tom = favorites.merge("Tom", "Skyride", mapper);//value replaced !
		System.out.println(favorites);
		
		favorites = new HashMap<>();
		favorites.put("Sam", null);
		System.out.println(favorites);
		favorites.merge("Pino", "Skyride", mapper);//mapper doesn't get called because 
		//of missing key and so a new value is added!
		System.out.println(favorites);
		favorites.merge("Sam", "Skyride", mapper);//mapper doesn't get called because 
		//given the same key, can't compare a value with another null
		
		//CASE C - mapping function returning null, the key is being removed along with its value
		BiFunction<String, String, String> mapper2 = (v1, v2) -> null;
		
		favorites = new HashMap<>();
		System.out.println(favorites);
		favorites.put("Jenny", "Bus Tour");
		favorites.put("Tom", null);
		System.out.println(favorites);
		favorites.merge("Jenny", "Skyride", mapper2);//the key, as well as its value, is being removed
		System.out.println(favorites);
		favorites.merge("Sam", "Skyride", mapper2);
		System.out.println(favorites);
		
	}

}
