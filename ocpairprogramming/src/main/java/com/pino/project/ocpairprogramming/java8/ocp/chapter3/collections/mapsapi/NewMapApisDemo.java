package com.pino.project.ocpairprogramming.java8.ocp.chapter3.collections.mapsapi;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class NewMapApisDemo {

	public static void main(String[] args) {
		//put
		
		//putIfAbsent
		
		// merge
		
		//computeIfPresent and computeIfAbsent which respectively call the BiFunction 
		//whether the requested key is found or not
		
		//default V compute(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)
		Map<String, Integer> counts = new HashMap<>();
		counts.put("Jenny", 1);// {Jenny=1}
		System.out.println("compute()");//
		BiFunction<String, Integer, Integer> mapper = (k,v) -> v+1;
		Integer jenny = counts.compute("Jenny", mapper);// {Jenny=2}
//		Integer sam = counts.compute("Sam", mapper);// NullPointerException
		System.out.println(counts);// {Jenny=2}
		System.out.println(jenny);// 2
//		System.out.println(sam);//
		
		//default V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction)just like with merge(), the return value is the result of what changed in the map or null if does not apply 
		counts = new HashMap<>();
		counts.put("Jenny", 1);// {Jenny=1}
		System.out.println("\ncomputeIfPresent()");
//		mapper = (k,v) -> v+1;
		jenny = counts.computeIfPresent("Jenny", mapper);// {Jenny=2}
		Integer sam = counts.computeIfPresent("Sam", mapper);// null
		System.out.println(counts);// {Jenny=2}
		System.out.println(jenny);// 2
		System.out.println(sam);// null
		
		// default V computeIfAbsent(K key,Function<? super K, ? extends V> mappingFunction) 
		// which calls Function and runs only when the key isn't present or its related value is null
		counts = new HashMap<>();
		counts.put("Jenny", 15);// {Jenny=15}
		counts.put("Tom", null);// {Tom=null}
		
		System.out.println("\ncomputeIfAbsent()");
		Function<String, Integer> funcMapper = (k) -> 1;
		jenny = counts.computeIfAbsent("Jenny", funcMapper);// {Jenny=15}
		sam = counts.computeIfAbsent("Sam", funcMapper);// {Sam=1} ok because key is not present
		Integer tom = counts.computeIfAbsent("Tom", funcMapper);// {Tom=1} ok because key is present but value is null
		System.out.println(counts);// {Tom=1, Jenny=15, Sam=1} order of key is given by their hashCode()
		System.out.println(jenny);// 15
		System.out.println(sam);// 1
		System.out.println(tom);// 1
		

	}

}
