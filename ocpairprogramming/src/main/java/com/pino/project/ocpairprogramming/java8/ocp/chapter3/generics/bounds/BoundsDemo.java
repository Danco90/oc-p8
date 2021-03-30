package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics.bounds;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author matteodaniele
 * Table 3.2 Types of bounds
 * ----------------------------+---------------+-----------------------------------------------------------------
 * Type of bound 				Syntax			Example
 * Unbounded wildcard 			 ?				List<?> l =new ArrayList<String>();
 * Wildcard with an upper bound  ? extends type  List<? extends Exception> l =new ArrayList<RuntimeException>();
 * Wildcard with a lower bound 	 ? super type	List<? super Exception> l =new ArrayList<Object>();
 */
public class BoundsDemo {

	public static void main(String[] args) {
		//TYPES of Generics 
		//Example: List<Number> which means ONLY Number and no subclass or superclass.
		List<String> keywords = new ArrayList<>();
		keywords.add("java");
//		printList(keywords);//(*) COMPILATION ERROR because List<String> cannot be assigned to List<Object> although String extends Object!
		
		List<Integer> numbers = new ArrayList<>();
		numbers.add(new Integer(42));
		numbers.add(33);//OK thanks to autoboxing int into Integer
//		List<Object> objects = numbers;//(*) DOES NOT COMPILE since we cannot convert from List<Object> to List<Number>
	    //(*) Due to type Erasure at runtime the ArrayList does not know what is allowed in it!
		
		//Arrays vs. ArrayList when storing the wrong Objects
		Integer[] numbers2 = { new Integer(42)};
		Object[] objects2 = numbers2; //LEGAL since the reference is a superclass, but the object is Integer[] and allows ONLY Integer or subclass at runtime
		objects2[0] = "forty two";//OK but throws ArrayStoreException as with arrays java knows the types (Integer[]) that is allowed in there.
		
		//SUMMARY: due to type erasure, which aims at backward compatibility (not breaking old existing code)
		//java uses the compiler to prevent wrong assignment with collections
		
		// 1. Bounded parameter type (without any question marks) is a generic type which specifies a bound for the generic
		//Examples: <T extends Number>, <T super Exception> and so on.
		
		// 2. A wildcard generic type (?) is an UNKNOWN generic type
		// Types of bounds for wildcard generic type
		
		// 2.A. Unbounded Wildcards ? :  List<?> that means whatever type, such as new ArrayList<String>(); And it is not just List<Object>
		List<String> keywords2 = new ArrayList<>();
		keywords2.add("java");
		printUnboundedList(keywords2);//Legal as long as it's a list of "Everything"
		
		// 2.B. Upper-bounded <? extends Number>, for example a RuntimeException
		
		// 2.C. Lower-bounded <? super Exception>, such as Throwable or Object

	}
	
	public static void printUnboundedList(List<?> list) {
		for(Object x: list) System.out.println(x);
	}
	
	public static void printList(List<Object> list) {
		for(Object x: list) System.out.println(x);
	}

}
