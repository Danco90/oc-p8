package com.pino.project.ocpairprogramming.java8.ocp.chapter4.streams;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InfiniteStreams {

	public static void main(String[] args) {
		Stream<Double> randoms = Stream.generate(Math::random);
		Stream<Integer> oddNumbers = Stream.iterate(1, n -> n + 2);
		//randoms.forEach(System.out::println);//WIll print until you kill
		
		Stream<String> s = Stream.of("monkey", "gorilla", "bonobo");
		Stream<String> infinite = Stream.generate(() -> "chimp");
		s.findAny().ifPresent(System.out::println); // monkey
		infinite.findAny().ifPresent(System.out::println); // chimp
//		Predicate<String> pred =
//		infinite.noneMatch();
		
//		infinite.findFirst().ifPresent(System.out::println); // IllegalStateException
		oddNumbers.findFirst().ifPresent(System.out::println);//1
//		oddNumbers.findAny().ifPresent(System.out::println);//IllegalStateException
	
		
		List<String> list = Arrays.asList("monkey", "2", "chimp");
		Stream<String> infinite2 = Stream.generate(() -> "chimp");
		Predicate<String> pred = x -> Character.isLetter(x.charAt(0));
		System.out.println(list.stream().anyMatch(pred)); // true
		System.out.println(list.stream().allMatch(pred)); // false
		System.out.println(list.stream().noneMatch(pred)); // false
//		System.out.println(infinite2.anyMatch(pred)); // true
//		System.out.println(infinite2.allMatch(pred)); // false
		System.out.println(infinite2.noneMatch(pred)); // false
		
		 List<Integer> numbers = Stream.iterate(0, x -> x+3)
                 .filter(x -> x>10 && x<20).peek(System.out::println)
                 .collect(Collectors.toList());
		 numbers.forEach(System.out::println);
	}

}
