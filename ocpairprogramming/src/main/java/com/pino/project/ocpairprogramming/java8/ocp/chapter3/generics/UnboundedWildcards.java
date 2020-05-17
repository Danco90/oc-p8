package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics.UpperboundedWildcards.Bird;

public class UnboundedWildcards {
	
	static class Sparrow extends Bird { }
	static class Bird { }
	
	public static void printList(List<Object> list) {
		for (Object x: list) System.out.print(x);
	}
	
	public static void printListSubtype(List<Integer> list) {
		for (Integer x: list) System.out.print(x);
	}
	
	public static void printListAnyType(List<?> list) {
		//for (? x: list) System.out.print(x);
	}
	
	public static void main(String[] args) {
		/* CASE1  */
		List<String> keywords = new ArrayList<>();
		keywords.add("java");
//		printList(keywords);//COMPILATION ERROR . Generic type can't use either subtype or supertype.
		//because List<String> CANNOT BE assigned to List<Object>
		/* CASE2  */
		List<Integer> numbers = new ArrayList<>();
		numbers.add(new Integer(42));
//		List<Object> objects = numbers; //COMPILATION ERROR
		//cannot convert from List<Integer> to List<Object>
//		objects.add("forty two");// compiles !
//		System.out.println(numbers.get(1));// even this on compiles !
		
		/* CASE3  */
		List<Object> objects2 = new ArrayList<>();
		objects2.add("forty two");
		objects2.add(42);
		//numbers = objects2;////COMPILATION ERROR because
		//cannot even convert from List<Object> to List<Integer>
		
		//Array Vs ArrayList
		//List<Object> objects3 = new ArrayList<String>();//COMPILATION ERROR
		Object[] o = new String[0];//wheres this DOES COMPILE correctly!
		
		List<Number> numbers2 = new ArrayList<>();
		numbers2.add(null);
		//printListSubtype(numbers2);//ERROR. A generic type can't even use a supertype
	
		printListAnyType(numbers2);//Compiles because is anytype
		
		//Immutable object with Unbounded Wildcard
		List<?> birds = new ArrayList<Bird>();
		
//		birds.add(new Bird());//Compilation Error because it can't add
		//Bird to a possible List<Sparrow> or a List of some subclass
		
//		birds.add(new Sparrow());//Compilation Error always because
		//we can't add a Sparrow to a List<Bird> !
		
		//From Java’s point of view, both scenarios 
		//(List<Bird> and List<Sparrow>) are equally possible 
		//so neither is allowed.
	}

}
