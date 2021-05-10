package com.pino.project.ocpairprogramming.java8.ocp.chapter3.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class ListImplementationsDemo {

	public static void main(String[] args) {
		//A. New implementations
		//A1. ArrayList implements List. It's a replacement of Vector
		List<String> list = new ArrayList<>();
		boolean b1 = list.add("SD");//[SD] , for mutable list it always returns true
		List<String> immulist = Arrays.asList("ONLY");
		try {
			boolean b2 = immulist.add("SD");//It throws java.lang.UnsupportedOperationException
			b2 = immulist.add("SD");
			b2 = immulist.add("SD");
			System.out.println(immulist);
		} catch (UnsupportedOperationException e) { System.out.println("Caught exception : "+e+" and swallowed"); }
		list.add(0,"NY");//[NY, SD]
		list.set(1,"FL");//[NY, FL]
		list.remove("NY");//[FL]
		list.remove(0);//[]
		list.add("OH");//[OH]
		list.add("CO");//[OH,CO]
		list.add("NJ");//[OH,CO,NJ]
		String state = list.get(0);// OH
		int index = list.indexOf("NJ");// 2
		
		//A2. LinkedList implements List, Queue. 
		//benefits: you can access,add, and remove from beginning or end in constant time O(1)
//		            It's a good choice when using it as Queue
		//tradeoff: dealing with arbitrary index takes linear time O(n)
		
		//B. Old implementations
		//B1. Vector implements List , replaced by ArrayList. It's slower because it is thread-safe
		
		//B2. Stack extends Vector: data structure where you add or remove elements from the top of the stack.
		//(*) if you need a stack, use an ArrayDeque.
		
		//Looping through a List
		System.out.println("Looping through a List");
		System.out.println("Enhanced for-loop");
		for( String s: list) 
			System.out.println(s);
		//before java 5
		System.out.println("Raw type Iterator (before java 5). It requires casting");
		Iterator iter = list.iterator();//Iterator raw type WARNING 
		while(iter.hasNext())//checks if there's a next value. it says if next() will execute without throwing an exception
		{   String s = (String) iter.next();//It requires casting. It moves the Iterator to the next element. It throws an exception if there's no such next element available
			System.out.println(s);
		}
		//Hybrid way : Iterator with generics
		System.out.println("Hybrid way : Iterator with generics");
		Iterator<String> it = list.iterator();
		while(it.hasNext()) 
		{   String s = it.next();
			System.out.println(s);
		}
	
	}

}
