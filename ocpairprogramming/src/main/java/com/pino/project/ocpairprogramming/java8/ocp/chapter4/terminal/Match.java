package com.pino.project.ocpairprogramming.java8.ocp.chapter4.terminal;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Match {
	public static void main(String[] args) {
		System.out.println("ITERATE MATCH 1:");
		Predicate<? super Integer> predicate2 = s-> s >3;
//		Stream<Integer> stream3 = Stream.iterate(1, (s)->/*++s*/s++); //.iterate() does not work properly with post increment. it hangs because the predicate condition is never met
//		stream3.forEach(System.out::println); //ddd dddddd
//		boolean b23 = stream3.anyMatch(predicate2); //true
//		boolean b23 = stream3.allMatch(predicate2); //false
//		boolean b23 = stream3.noneMatch(predicate2); //false
//		System.out.println(b23);
		
		/*
	     * first param stopOnPredicateMatches
	     * second param shortCircuitResult
	    ANY(true, true),   //if(true) match. --> true
        ALL(false, false),  // if(false) match. --> false
        NONE(true, false);  //if(true) match. --> false
	    */
		
		System.out.println("ITERATE MATCH 2:");
		Predicate<? super String> predicate2e = s-> s.isEmpty();
		Stream<String> stream3e = Stream.iterate("ddd", (s)->s+s); 
		//stream3e.forEach(System.out::println); //ddd dddddd ....
		//boolean b23e = stream3e.anyMatch(predicate2e); //infinite 
		boolean b23e = stream3e.allMatch(predicate2e); //false  
		//boolean b23e = stream3e.noneMatch(predicate2e); //infinite &
		System.out.println(b23e);
		
		System.out.println("ITERATE MATCH 2b:");
		Predicate<? super String> predicate2b = s-> !s.isEmpty();
		Stream<String> stream3b = Stream.iterate("ddd", (s)->s+s); 
		//stream3b.forEach(System.out::println); //ddd dddddd ....
//		boolean b3b = stream3b.anyMatch(predicate2b); //true 
//		boolean b3b = stream3b.allMatch(predicate2b); //infinite - OutOfMemoryError
		boolean b3b = stream3b.noneMatch(predicate2b); //false 
		System.out.println(b3b);
		
		
		System.out.println("ITERATE MATCH 3:");
		// No Infinite Stream. if you use generate is an infinite stream
		Predicate<? super String> predicateI = s -> s.startsWith("g"); 
		Stream<String> streamI = Stream.iterate("", (s)->"growl! "); 
//		streamI/*.limit(2)*/.forEach(System.out::println); // "<empty>" growl! ....
//		boolean bI = streamI.anyMatch(predicateI); //true
//		boolean bI = streamI.allMatch(predicateI);  //false
		boolean bI = streamI.noneMatch(predicateI);  //false because it founds out NONE(true, false); at the second element
	    System.out.println(bI);
	    
	  
	    System.out.println("ITERATE MATCH 4:");
		Predicate<? super String> predicate4e = s-> s.isEmpty();
		Stream<String> stream4e = Stream.iterate("", (s)->s+s);
		//stream4e.forEach(System.out::println); // "<empty>"  "<empty>" 
//		boolean b24e = stream4e.anyMatch(predicate4e); //true  ||
//		boolean b24e = stream4e.allMatch(predicate4e); //infinite && - OutOfMemoryError
		boolean b24e = stream4e.noneMatch(predicate4e); //false
		System.out.println(b24e);
		
		
		System.out.println("GENERATE MATCH 1:");
		//Infinite Stream
		Predicate<? super String> predicate = s -> s.startsWith("g");
		Stream<String> stream1 = Stream.generate(()->"growl! ").limit(2); //try the same with iterate
		//stream1.forEach(System.out::println); //growl! growl!
		boolean b1 = stream1.anyMatch(predicate); //true 
		//boolean b1 = stream1.allMatch(predicate); //infinite stream. It needs to keep going until the end of the stream
	    //boolean b1 = stream1.noneMatch(predicate); //false
		System.out.println(b1);
		
		/*
		 * anyMatch/nonematch: The stream has no way to know that a match won’t show up later. 
		 * all match: it is safe to return false as soon as one element passes through the stream that doesn’t match.
		 */
		System.out.println("GENERATE MATCH 2:");
		Stream<String> s= Stream.generate(()->"meow").limit(2);
//		s.forEach(System.out::println); //meow meow
//		boolean match = s.anyMatch(String::isEmpty); //FALSE
//		boolean match = s.anyMatch(String::length); //DOESN'T COMPILE type is String
//		boolean match = s.noneMatch(String::isEmpty); //TRUE
		boolean match = s.allMatch(String::isEmpty); ////FALSE
		System.out.println(match);
		
		
		//Infinite Stream
		System.out.println("GENERATE MATCH 3:");
		Predicate<? super String> predicate3 = s2-> s2.length() >3;
		Stream<String> stream44 = Stream.generate(()->"ddd").limit(2);
//		stream44.forEach(System.out::println); //ddd ddd
//		boolean b5 = stream44.anyMatch(predicate3); //false
//		boolean b5 = stream44.allMatch(predicate3); //false
		boolean b5 = stream44.noneMatch(predicate3); //true 
		System.out.println(b5);
		
		System.out.println("GENERATE MATCH 4:");
		Predicate<? super String> predicate4 = s2-> s2.length() >3;
		Stream<String> stream444 = Stream.generate(()->"dddd").limit(2);
//		stream444.forEach(System.out::println); //dddd dddd
//		boolean b34 = stream444.anyMatch(predicate4); //true
//		boolean b34 = stream444.allMatch(predicate4); //true
		boolean b34 = stream444.noneMatch(predicate4); //false
		System.out.println(b34);
		
		List<String> list = Arrays.asList("monkey","0","chimp");
		Predicate<String> pred = x-> Character.isLetter(x.charAt(0));
		list.stream().anyMatch(pred); //--> true
		list.stream().allMatch(pred); //--> false
		list.stream().noneMatch(pred); //--> false
	}
}
