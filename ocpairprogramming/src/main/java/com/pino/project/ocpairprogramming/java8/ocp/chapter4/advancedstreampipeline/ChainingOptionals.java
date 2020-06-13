package com.pino.project.ocpairprogramming.java8.ocp.chapter4.advancedstreampipeline;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ChainingOptionals {

	private static void threeDigit(Optional<Integer> optional) {
		if (optional.isPresent()) { // outer if
			Integer num = optional.get();
			String string = "" + num;
			if (string.length() == 3) // inner if
			System.out.println(string);
		} 
	}
	
	private static void threeDigitAdvanced(Optional<Integer> optional) {
		optional.map(n -> ""+ n)//if mets an empty Optional, it passes it through without calling the Function<T, R> 
			.filter(s -> s.length() == 3)//if not met the condition, it returns an emptyOptional
			.ifPresent(System.out::println);//if it sees an empty Optional, it does not call the Consumer parameter
	}
	
	private static Optional<Integer> calculator(String s){
		return Optional.of(Integer.parseInt(s));
	}
	
	public static void main(String[] args) {
		
		//DOUBT : what is the difference between Optional[] and Optional.empty ?
		
		IntStream ints = IntStream.of();
		OptionalDouble optionalDouble = ints.average();//It will return an optional effectively empty, but not exactly Optional.empy
		System.out.println(optionalDouble);
	
		//Empty optional
		Optional<Integer> optional = Optional.empty();
		threeDigit(optional);//it stops at outer if, returning false as it's not present
		threeDigitAdvanced(optional);//it sees an empty Optional and has both map() and filter() pass it through!
		
		//Non empty Optional.of(4)
		optional = Optional.of(4);
		threeDigit(optional);//returns false for the inner if
		threeDigitAdvanced(optional);//it does not call the  Consumer parameter
		
		//Non empty Optional.of(123)
		optional = Optional.of(123);
		threeDigit(optional);//returns true for both if
		threeDigitAdvanced(optional);//maps number 123 to the string "123", the filter then returns the same Optional not empty and ifPresent() now does call the Consumer
	
		Optional<String> optional2 = Optional.of("10");
		Optional<Integer> result = optional2.map(String::length);
		
//		result = optional2.map(ChainingOptionals::calculator);//Does not copile because it adds another level of Optional
		//Solution 1 - BAD
		Optional<Optional<Integer>> result2 = optional2.map(ChainingOptionals::calculator);
		
		//Solution 2 - using flatMap() to flatten to one level of Optional
		result = optional2.flatMap(ChainingOptionals::calculator);
		System.out.println(result);
		
	
	}

}
