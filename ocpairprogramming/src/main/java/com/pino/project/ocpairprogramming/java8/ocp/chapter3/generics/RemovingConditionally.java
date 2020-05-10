package com.pino.project.ocpairprogramming.java8.ocp.chapter3.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class RemovingConditionally {

	public static void main(String[] args) {
		List<String> list = new ArrayList<>();
		list.add("Magician");
		list.add("Assistant");
		System.out.println(list); // [Magician, Assistant]
		
		// default boolean removeIf(Predicate<? super E> filter) {
		
		list.removeIf(s -> s.startsWith("A"));//the logic to run when that point 
		//in the code is reached : for each element if its first character is
		// 'A', then remove that element.
		System.out.println(list); // [Magician]
		
		//NB: it can't be replaced with method reference because startsWith("A")
		//takes a param 'A' which is not each singular element of the list.
		//s::startWith would apply for what's inside string s
		//but s is neither a specific instance at all!
		String str = "A";
		Predicate<String> mr = str::startsWith;
		list.add("Assistant");
		System.out.println(list); // [Magician,Assistant]
		list.removeIf(str::startsWith);// It won't remove the element 
		//since 'mr' it is not equal to 'str -> str.startsWith("A")'
		System.out.println(list); // [Magician, Assistant]
		list.add("A");
		System.out.println(list); // [Magician, Assistant, A]
		list.removeIf(mr);// would remove just 'A'
		System.out.println(list);
		
		//Updating all elements conditionally
		//void replaceAll(UnaryOperator<E> o)
		//UnaryOperator, which takes one parameter 
		//and returns a value of the same type
		List<Integer> list2 = Arrays.asList(1, 2, 3);
		System.out.println(list2); // [1, 2, 3]
		UnaryOperator<Integer> unaryLambda = x -> x*2;
		list2.replaceAll(unaryLambda);//its like map(x -> x*2)
		//How to do the long way with Stream.to(list2).map..?
		System.out.println(list2); // [2, 4, 6]
		

	}

}
