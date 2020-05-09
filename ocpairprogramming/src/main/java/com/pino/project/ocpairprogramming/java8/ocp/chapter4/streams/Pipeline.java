package com.pino.project.ocpairprogramming.java8.ocp.chapter4.streams;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

/*
 * Here I show some examples of usage of the Stream API, specifically
 * of the pipeline with various intermediate and final operations
 */

public class Pipeline {

		public static void main(String[] args) {
			
			//only filter
			
			Stream.iterate(1, n-> n + 1).filter(s -> s < 5 && s > 2).forEach(n -> System.out.println(n)); //hangs

			Stream.iterate(1, n-> n + 1).filter(s -> s < 5).forEach(n -> System.out.println(n)); //hangs printing infinite numbers
			
			
			//filter + limit -> limit is needed with infinite steams
			//pay attention to order of filter - stream -> can give different results
			
			//basic case - filter before limit - OK
			Stream.iterate(1, n-> n + 1).filter(s -> s < 5 && s >= 3).limit(2).forEach(n -> System.out.println(n)); //OK
				
			//basic case - limit before filter - different result
			Stream.iterate(1, n-> n + 1).limit(2).filter(s -> s < 5 && s >= 3).forEach(n -> System.out.println(n)); //OK
			
			//basic case - hangs
			Stream.iterate(1, n-> n + 1).filter(s -> s < 1).limit(2).forEach(n -> System.out.println(n)); // condition never satisfied 
			//so always hangs

			
			
			//behaviour of sort
			
			Stream.iterate(1, n-> n + 1).filter(n -> n > 2).limit(2).sorted().forEach(n -> System.out.println(n)); // OK

			Stream.iterate(1, n-> n + 1).filter(n -> n > 2).sorted().limit(2).forEach(n -> System.out.println(n)); //hangs

						
			
			//behaviour of peek
			
			Stream.iterate(1, n-> n + 1).limit(2).peek(n -> System.out.println(n)).filter(n -> n > 1).count(); //prints 1, 2

			Stream.iterate(1, n-> n + 1).filter(n -> n > 1).peek(n -> System.out.println(n)).limit(2).count(); //prints 2, 3

			Stream.iterate(1, n-> n + 1).peek(n -> System.out.println(n)).limit(2).filter(n -> n > 2).count(); //prints 1, 2

			Stream.iterate(1, n-> n + 1).peek(n -> System.out.println(n)).filter(n -> n > 2).limit(2).count(); //prints 1, 2, 3, 4

			Stream.iterate(1, n-> n + 1).peek(n -> System.out.println(n)).filter(n -> n > 2).sorted().limit(2).count(); //hangs

			//pay attention to the ++ operator
			System.out.println(Stream.iterate(1, x -> ++x).limit(5).map(x -> ""+x).collect(Collectors.joining()));
			
			//bad usage of peek operation: here it alters the state of an object used in the pipeline
			List<Integer> numbers = new ArrayList<>();
			List<Character> letters = new ArrayList<>();
			numbers.add(1);
			letters.add('a');
			Stream<List<?>> bad = Stream.of(numbers, letters);
			bad.peek(l -> l.remove(0)).map(List::size).forEach(System.out::print); // 00
			
			
			
			//reverse sorting
			List<String> l5 = Arrays.asList("1", "5", "0");
			l5.stream().sorted((a, b) -> b.compareTo(a)).forEach(System.out::println);
			
			
			
			//from the book
			Stream.generate(()->"Elsa")
			.filter(n->n.length()==4)
			.sorted().limit(2)
			.forEach(System.out::println); 	//this hangs because to wait until everything to sort is present.

			Stream.generate(()->"Olaz Lazzisson")
			.filter(n->n.length()==4)
			.limit(2)
			.sorted()
			.forEach(System.out::println); //this hangs because limit() never see 2 elems

			Stream.generate(()->"Elsa")
			.filter(n->n.length()==4)
			.limit(2)
			.sorted()
			.forEach(System.out::println); //Elsa Elsa
		}
}
